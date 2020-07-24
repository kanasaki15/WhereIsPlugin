package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
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
                }
            }
            if (args.length == 6){

            }
        }
        return true;
    }
}
