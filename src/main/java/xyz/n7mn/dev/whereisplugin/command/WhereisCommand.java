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

        if (label.equals("where") && args.length == 0){
            if (player != null) {
                if (new WhereisConfigAPI().isUseLuckPerms()) {
                    Plugin luckPerms = Bukkit.getPluginManager().getPlugin("LuckPerms");
                    if (luckPerms != null) {
                        if (!player.hasPermission("whereis")) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c実行する権限がありません。"));
                            return true;
                        }
                    }
                }

                List<WhereisData> list = new WhereisDataAPI(plugin, con).getDataList();
                if (list == null){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"ここは 名称未設定 です。"));
                    return true;
                }

                StringBuffer sb = new StringBuffer();

                int count = 0;
                int count2 = 0;
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
                            ((player.getLocation().getBlockX() <= data.getStartX() && player.getLocation().getBlockX() >= data.getEndX()) || (player.getLocation().getBlockX() >= data.getStartX() && player.getLocation().getBlockX() <= data.getEndX())) &&
                            ((player.getLocation().getBlockZ() <= data.getStartZ() && player.getLocation().getBlockZ() >= data.getEndZ()) || (player.getLocation().getBlockZ() >= data.getStartZ() && player.getLocation().getBlockZ() <= data.getEndZ()))
                    ){
                        sb.append(data.getName());
                        if (count + 1 < max){
                            sb.append(",");
                        }
                        count2++;
                    }

                    count++;
                }


                final String whereName;
                if (sb.toString().endsWith(",")){
                    whereName = sb.toString().substring(0, sb.toString().length() - 1);
                } else {
                    whereName = sb.toString();
                }

                if (count2 != 0){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"ここは " + whereName + " です。"));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"ここは 名称未設定 です。"));
                }
                return true;
            } else {
                sender.sendMessage(ChatColor.GREEN + "----- WhereIsPlugin Ver "+plugin.getDescription().getVersion() + "-----");
                sender.sendMessage(ChatColor.GREEN + "/where add <x> <z> <x> <z> <Name> -- 追加");
                sender.sendMessage(ChatColor.GREEN + "/where update <OldName> <NewName> -- 更新");
                sender.sendMessage(ChatColor.GREEN + "/where update <Name> <x> <z> <x> <z> -- 範囲更新");
                sender.sendMessage(ChatColor.GREEN + "/where delete <Name> -- 削除");
                sender.sendMessage(ChatColor.GREEN + "/where list -- リスト");
                // System.out.println(plugin.getServer().getVersion().toLowerCase());
                if (plugin.getServer().getVersion().toLowerCase().matches(".*paper.*")){
                    sender.sendMessage(ChatColor.GREEN + "RunSystem : Paper");
                } else if (plugin.getServer().getVersion().toLowerCase().matches(".*spigot.*")) {
                    sender.sendMessage(ChatColor.GREEN + "RunSystem : Spigot");
                } else {
                    sender.sendMessage(ChatColor.GREEN + "RunSystem : UnknownBukkit (動作対象外)");
                }
            }
        }

        if (args.length > 0 && args[0].equals("axeget")){
            return new WhereisAxeGet().onCommand(sender, command, label, args);
        }

        if (args.length > 0 && args[0].equals("axeclear")){
            return new WhereisAxeClear(plugin).onCommand(sender, command, label, args);
        }

        if (args.length > 0 && args[0].equals("add")){
            return new WhereisAdd(plugin, con).onCommand(sender, command, label, args);
        }

        if (args.length > 0 && args[0].equals("update")){
            return new WhereisUpdate(plugin, con).onCommand(sender, command, label, args);
        }

        if (args.length > 0 && args[0].equals("delete")){
            return new WhereisDelete(plugin, con).onCommand(sender, command, label, args);
        }



        return true;
    }
}
