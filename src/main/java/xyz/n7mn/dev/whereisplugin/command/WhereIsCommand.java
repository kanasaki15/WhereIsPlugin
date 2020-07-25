package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.whereisplugin.resource.WhereIsCommandUsage;
import xyz.n7mn.dev.whereisplugin.resource.WhereIsLnResource;
import xyz.n7mn.dev.whereisplugin.data.Data;

import java.util.Collection;
import java.util.UUID;

public class WhereIsCommand implements CommandExecutor {

    WhereIsLnResource lnMsg = new WhereIsLnResource("ja");
    Plugin plugin;

    public WhereIsCommand (Plugin p) {
        plugin = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1){
            if (args[0].equals("help")){
                sender.sendMessage(ChatColor.YELLOW + "--- WhereIsPlugin Help ---");
                sender.sendMessage(ChatColor.YELLOW + "/where -- "+lnMsg.commandWhere);
                sender.sendMessage(ChatColor.YELLOW + "/where help -- "+lnMsg.commandHelp);
                sender.sendMessage(ChatColor.YELLOW + "/where add ["+lnMsg.x1+"] ["+lnMsg.x2+"] ["+lnMsg.z1+"] ["+lnMsg.z2+"] [Name] -- "+lnMsg.commandWhereAdd);
                sender.sendMessage(ChatColor.YELLOW + "/where del [Name] -- "+lnMsg.commandWhereDel);
                sender.sendMessage(ChatColor.YELLOW + "/where Update [OldName] [NewName] -- "+lnMsg.commandWhereUpdate);
                sender.sendMessage(ChatColor.YELLOW + "/where system -- "+lnMsg.commandSystem);
                sender.sendMessage(ChatColor.YELLOW + "/where admin -- "+lnMsg.commandAdmin);
                return true;
            }

            if (args[0].equals("system")){
                if (sender instanceof Player){
                    Player player = (Player)sender;
                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null){
                        if (!player.hasPermission("whereis.sys")){
                            player.sendMessage(ChatColor.RED + lnMsg.PermError);
                            return true;
                        }
                    }
                    if (!player.isOp()) {
                        player.sendMessage(ChatColor.RED + lnMsg.PermError);
                        return true;
                    }
                }
                sender.sendMessage(ChatColor.YELLOW + "---- WhereIsPlugin SystemInfo ----");
                sender.sendMessage(ChatColor.YELLOW + "Ver : " + plugin.getDescription().getVersion());
                sender.sendMessage(ChatColor.YELLOW + "Use DataSystem : " + new Data(plugin).getMode());
                sender.sendMessage(ChatColor.YELLOW + "Use SpigotAPI Version" + plugin.getDescription().getAPIVersion());
                if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null){
                    sender.sendMessage(ChatColor.YELLOW + "Use LuckPerm Mode : True");
                }else{
                    sender.sendMessage(ChatColor.YELLOW + "Use LuckPerm Mode : False");
                }

                return true;
            }
        }else{
            if (args.length == 0){
                if (sender instanceof Player){
                    Player player = (Player)sender;

                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null){
                        if (!player.hasPermission("whereis.check")){
                            player.sendMessage(ChatColor.RED + lnMsg.PermError);
                            return true;
                        }
                    }

                    Data[] dataList = new Data(plugin).getDataList(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
                    StringBuffer sb = new StringBuffer();

                    for (int i = 0; i < dataList.length; i++){
                        sb.append(dataList[i].Name);
                        if ((i + 1) < dataList.length){
                            sb.append(",");
                        }
                    }
                    if (dataList.length == 0){
                        sb.append(lnMsg.NoName);
                    }
                    player.sendMessage(lnMsg.Here1+sb.toString()+lnMsg.Here2);
                    return true;
                }else{
                    Data[] list = new Data(plugin).getDataAllList();
                    for (int i = 0; i < list.length; i++){

                        String CreateUserName = null;
                        Object[] onlineList = plugin.getServer().getOnlinePlayers().toArray();

                        for (int x = 0; x < onlineList.length; x++){
                            Player p = (Player) onlineList[x];
                            if (p.getUniqueId().toString().equals(list[i].uuid.toString())){
                                CreateUserName = p.getDisplayName();
                                break;
                            }
                        }
                        if (CreateUserName == null){
                            OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(list[i].uuid);
                            if (offlinePlayer != null){
                                CreateUserName = offlinePlayer.getName();
                            }else{
                                CreateUserName = "Unknown";
                            }
                        }

                        sender.sendMessage(lnMsg.x1+": "+list[i].startX+" "+lnMsg.z1+": "+list[i].startZ + " - " + lnMsg.z1+": "+list[i].startZ+" "+lnMsg.z2 + ": "+list[i].endZ+" : " + list[i].Name + " (By "+CreateUserName+")");
                    }
                    return true;
                }
            }else{
                Player player = null;
                UUID PlayerUUID = null;
                if (sender instanceof Player){
                    player = (Player)sender;
                    PlayerUUID = player.getUniqueId();
                }

                command.setUsage(WhereIsCommandUsage.Msg(args[0]));
                if (args[0].equals("add") && args.length == 6){

                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null && player != null){
                        if (!player.hasPermission("whereis.add")){
                            player.sendMessage(ChatColor.RED + lnMsg.PermError);
                            return true;
                        }
                    }

                    if (new Data(plugin).setName(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), args[5], PlayerUUID)){
                        String msg = lnMsg.AddSuccess.replaceAll("\\{startx\\}",args[1]).replaceAll("\\{endx\\}",args[2]).replaceAll("\\{startz\\}",args[3]).replaceAll("\\{endz\\}",args[4]).replaceAll("\\{name\\}",args[5]);
                        sender.sendMessage(ChatColor.YELLOW + msg);
                    }else{
                        sender.sendMessage(ChatColor.RED + lnMsg.AddError);
                    }

                    return true;
                }

                if (args[0].equals("update") && args.length == 3){
                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null && player != null){
                        if (!player.hasPermission("whereis.update")){
                            player.sendMessage(ChatColor.RED + lnMsg.PermError);
                            return true;
                        }
                    }

                    if (new Data(plugin).UpdateName(args[1], args[2], PlayerUUID)){
                        sender.sendMessage(ChatColor.YELLOW + lnMsg.UpdateSuccess.replaceAll("\\{oldname\\}",args[1]).replaceAll("\\{newname\\}",args[2]));
                    }else{
                        sender.sendMessage(ChatColor.RED + lnMsg.UpdateError);
                    }

                    return true;
                }

                if (args[0].equals("del") && args.length == 2){
                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null && player != null){
                        if (!player.hasPermission("whereis.del")){
                            player.sendMessage(ChatColor.RED + lnMsg.PermError);
                            return true;
                        }
                    }

                    if (new Data(plugin).DelName(args[1], PlayerUUID)){
                        sender.sendMessage(ChatColor.YELLOW + lnMsg.DelSuccess.replaceAll("\\{name\\}",args[1]));
                    }else{
                        sender.sendMessage(ChatColor.RED + lnMsg.DelError);
                    }

                    return true;
                }

                if (args[0].equals("admin")){

                    if (player != null && !player.isOp()){
                        sender.sendMessage(ChatColor.RED + lnMsg.PermError);
                        return true;
                    }

                    if (player != null && plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null){
                        if (!player.hasPermission("whereis.admin")){
                            sender.sendMessage(ChatColor.RED + lnMsg.PermError);
                        }
                    }

                    if (args.length == 2){
                        if (args[1].equals("list")){
                            Data[] list = new Data(plugin).getDataAllList();
                            int pagePer = 5;
                            if (player == null){ pagePer = list.length; }
                            int maxPage = (list.length / pagePer);
                            if (maxPage < 1){ maxPage = 1; }

                            if (maxPage == 1){
                                sender.sendMessage(ChatColor.YELLOW + "---- WhereIsPlugin Admin ItemList ----");
                            }else{
                                sender.sendMessage(ChatColor.YELLOW + "---- WhereIsPlugin Admin ItemList Page 1 / "+maxPage+" ----");
                            }

                            int minPage = list.length - pagePer;
                            if (minPage < 1){ minPage = 1; }
                            for (int i = list.length; i >= minPage; i--){

                                sender.sendMessage(ChatColor.YELLOW + "ID : "+list[i - 1].ID);
                                sender.sendMessage(ChatColor.YELLOW + "Name : "+list[i - 1].Name);
                                Player crePlayer = plugin.getServer().getPlayer(list[i - 1].uuid);
                                if (crePlayer == null){
                                    crePlayer = plugin.getServer().getOfflinePlayer(list[i - 1].uuid).getPlayer();
                                }
                                if (crePlayer == null){
                                    sender.sendMessage(ChatColor.YELLOW + "CreateUser (UUID): "+list[i - 1].uuid);
                                }else{
                                    sender.sendMessage(ChatColor.YELLOW + "CreateUser : "+crePlayer.getName());
                                }
                                sender.sendMessage(ChatColor.YELLOW + "Active : " + list[i - 1].Active);
                                sender.sendMessage(ChatColor.YELLOW + lnMsg.x1 + " : " + list[i - 1].startX+" , "+lnMsg.z1 + " : " + list[i - 1].startZ+" , "+lnMsg.x2 + " : " + list[i - 1].endX+" , "+lnMsg.z2 + " : " + list[i - 1].endZ);

                                sender.sendMessage(ChatColor.YELLOW + "------");
                            }
                            return true;
                        }
                    }

                    if (args.length == 3){
                        if (args[1].equals("list")){
                            Data[] list = new Data(plugin).getDataAllList();
                            int pagePer = 5;
                            if (player == null){ pagePer = list.length; }
                            int maxPage = (list.length / pagePer);
                            if (maxPage < 1){ maxPage = 1; }
                            sender.sendMessage(ChatColor.YELLOW + "---- WhereIsPlugin Admin ItemList Page "+args[2]+" / "+maxPage+" ----");

                            int minPage = list.length - (pagePer * Integer.parseInt(args[2]));
                            int MaxPage = minPage + 5;
                            if (minPage < 1){ minPage = 1; }
                            if (maxPage >= list.length){ MaxPage = list.length; }

                            for (int i = minPage; i <= MaxPage; i++){

                                sender.sendMessage(ChatColor.YELLOW + "ID : "+list[i - 1].ID);
                                sender.sendMessage(ChatColor.YELLOW + "Name : "+list[i - 1].Name);
                                Player crePlayer = plugin.getServer().getPlayer(list[i - 1].uuid);
                                if (crePlayer == null){
                                    crePlayer = plugin.getServer().getOfflinePlayer(list[i - 1].uuid).getPlayer();
                                }
                                if (crePlayer == null){
                                    sender.sendMessage(ChatColor.YELLOW + "CreateUser (UUID): "+list[i - 1].uuid);
                                }else{
                                    sender.sendMessage(ChatColor.YELLOW + "CreateUser : "+crePlayer.getName());
                                }
                                sender.sendMessage(ChatColor.YELLOW + "Active : " + list[i - 1].Active);
                                sender.sendMessage(ChatColor.YELLOW + lnMsg.x1 + " : " + list[i - 1].startX+" , "+lnMsg.z1 + " : " + list[i - 1].startZ+" , "+lnMsg.x2 + " : " + list[i - 1].endX+" , "+lnMsg.z2 + " : " + list[i - 1].endZ);

                                sender.sendMessage(ChatColor.YELLOW + "------");
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
