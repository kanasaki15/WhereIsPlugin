package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.whereisplugin.resource.WhereIsCommandUsage;
import xyz.n7mn.dev.whereisplugin.resource.WhereIsLnResource;
import xyz.n7mn.dev.whereisplugin.data.Data;

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
                        sender.sendMessage(lnMsg.x1+": "+list[i].startX+" "+lnMsg.z1+": "+list[i].startZ + " - " + lnMsg.z1+": "+list[i].startZ+" "+lnMsg.z2 + ": "+list[i].endZ+" : " + list[i].Name);
                    }
                    return true;
                }
            }else{
                command.setUsage(WhereIsCommandUsage.Msg(args[0]));
                if (args[0].equals("add") && args.length == 6){

                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null && sender instanceof Player){
                        Player player = (Player)sender;
                        if (!player.hasPermission("whereis.add")){
                            player.sendMessage(ChatColor.RED + lnMsg.PermError);
                            return true;
                        }
                    }
                    if (new Data(plugin).setName(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), args[5])){
                        String msg = lnMsg.AddSuccess.replaceAll("\\{startx\\}",args[1]).replaceAll("\\{endx\\}",args[2]).replaceAll("\\{startz\\}",args[3]).replaceAll("\\{endz\\}",args[4]).replaceAll("\\{endz\\}",args[5]);
                        sender.sendMessage(ChatColor.YELLOW + msg);
                    }else{
                        sender.sendMessage(ChatColor.RED + lnMsg.AddError);
                    }

                    return true;
                }

                if (args[0].equals("update") && args.length == 3){
                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null && sender instanceof Player){
                        Player player = (Player)sender;
                        if (!player.hasPermission("whereis.update")){
                            player.sendMessage(ChatColor.RED + lnMsg.PermError);
                            return true;
                        }
                    }

                    if (new Data(plugin).UpdateName(args[1], args[2])){
                        sender.sendMessage(ChatColor.YELLOW + lnMsg.UpdateSuccess.replaceAll("\\{oldname\\}",args[1]).replaceAll("\\{newname\\}",args[2]));
                    }else{
                        sender.sendMessage(ChatColor.RED + lnMsg.UpdateError);
                    }

                    return true;
                }

                if (args[0].equals("del") && args.length == 2){
                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null && sender instanceof Player){
                        Player player = (Player)sender;
                        if (!player.hasPermission("whereis.del")){
                            player.sendMessage(ChatColor.RED + lnMsg.PermError);
                            return true;
                        }
                    }

                    if (new Data(plugin).DelName(args[1])){
                        sender.sendMessage(ChatColor.YELLOW + lnMsg.DelSuccess.replaceAll("\\{name\\}",args[1]));
                    }else{
                        sender.sendMessage(ChatColor.RED + lnMsg.DelError);
                    }

                    return true;
                }
            }
        }
        return false;
    }
}
