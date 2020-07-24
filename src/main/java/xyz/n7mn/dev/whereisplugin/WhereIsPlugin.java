package xyz.n7mn.dev.whereisplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.n7mn.dev.whereisplugin.data.Data;

public final class WhereIsPlugin extends JavaPlugin {

    WhereIsLnResource lnMsg;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        lnMsg = new WhereIsLnResource("ja");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!label.startsWith("where")){
            return false;
        }
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
                    Data[] dataList = new Data(this).getDataList(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
                    StringBuffer sb = new StringBuffer();

                    for (int i = 0; i < dataList.length; i++){
                        sb.append(dataList[i].Name);
                        if ((i + 1) < dataList.length){
                            sb.append(",");
                        }
                    }

                    player.sendMessage(lnMsg.Here1+sb.toString()+lnMsg.Here2);
                }
            }
        }
        return true;
    }
}
