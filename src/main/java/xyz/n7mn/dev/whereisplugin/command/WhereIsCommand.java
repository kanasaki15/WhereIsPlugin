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
        }else{
            if (args.length == 0){
                if (sender instanceof Player){
                    Player player = (Player)sender;
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
                }
            }else{
                //command.setUsage(WhereIsCommandUsage.Msg(args[0]));
                if (args[0].equals("add") && args.length == 6){
                    String s = new Data(plugin).setName(Integer.getInteger(args[1]), Integer.getInteger(args[2]), Integer.getInteger(args[3]), Integer.getInteger(args[4]), args[5]);
                    plugin.getLogger().info("Debug : "+s);

                    return true;
                }
            }
        }
        return false;
    }
}
