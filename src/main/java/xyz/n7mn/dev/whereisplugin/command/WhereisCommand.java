package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.whereisplugin.api.v2.WhereisConfigAPI;
import xyz.n7mn.dev.whereisplugin.api.v2.WhereisData;
import xyz.n7mn.dev.whereisplugin.api.v2.WhereisDataAPI;

import java.sql.Connection;
import java.util.List;

public class WhereisCommand implements CommandExecutor {

    private final Plugin plugin;
    private final Connection con;

    public WhereisCommand(Plugin plugin, Connection con){
        this.plugin = plugin;
        this.con = con;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final Player player;
        if (sender instanceof Player){
            player = (Player) sender;
        } else {
            player = null;
        }


        if (player != null){

            if (new WhereisConfigAPI().isUseLuckPerms()){
                Plugin luckPerms = Bukkit.getPluginManager().getPlugin("LuckPerms");
                if (luckPerms != null){
                    if (!player.hasPermission("whereis")){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"実行する権限がありません。"));
                        return true;
                    }
                }
            }

            if (label.equals("where") && args.length == 0){
                List<WhereisData> list = new WhereisDataAPI(plugin, con).getDataList();
                if (list == null){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"ここは 名称未設定 です。"));
                    return true;
                }

                StringBuffer sb = new StringBuffer();

                int count = 0;
                int max = list.size();
                for (WhereisData data : list){
                    if (!data.isActive()){
                        count++;
                        continue;
                    }

                    if (!data.getWorld().getUID().equals(player.getLocation().getWorld().getUID())){
                        count++;
                        continue;
                    }

                    if (
                            (data.getStartX() <= player.getLocation().getBlockX() && player.getLocation().getBlockX() <= data.getEndX()) || (data.getStartX() >= player.getLocation().getBlockX() && player.getLocation().getBlockX() >= data.getEndX()) &&
                            (data.getStartZ() <= player.getLocation().getBlockZ() && player.getLocation().getBlockZ() <= data.getEndZ()) || (data.getStartZ() >= player.getLocation().getBlockZ() && player.getLocation().getBlockZ() >= data.getEndZ())
                    ){
                        sb.append(data.getName());
                        if (count < max){
                            sb.append(",");
                        }
                    }

                    count++;
                }

                if (count != 0){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"ここは " + sb.toString() + " です。"));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"ここは 名称未設定 です。"));
                }
                return true;
            }

            if (label.equals("add") && args.length == 2){
                return new WhereisAdd(plugin, con).onCommand(sender, command, label, args);
            }

            if (label.equals("add") && args.length == 6){
                return new WhereisAdd(plugin, con).onCommand(sender, command, label, args);
            }


        } else {
            if (args.length == 0){
                sender.sendMessage(ChatColor.GREEN + "----- WhereIsPlugin Ver "+plugin.getDescription().getVersion() + "-----");
                sender.sendMessage(ChatColor.GREEN + "");
            }
        }

        return true;
    }
}
