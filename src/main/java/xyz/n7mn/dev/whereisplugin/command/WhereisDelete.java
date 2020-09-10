package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.whereisplugin.api.v2.WhereisData;
import xyz.n7mn.dev.whereisplugin.api.v2.WhereisDataAPI;

import java.sql.Connection;
import java.util.List;

public class WhereisDelete extends CommandInterface {

    private Plugin plugin;
    private Connection con;

    public WhereisDelete(Plugin plugin, Connection con){
        this.plugin = plugin;
        this.con = con;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1){

            return true;
        }

        if (args.length == 2 && sender instanceof Player){
            List<WhereisData> list = new WhereisDataAPI(plugin, con).getDataList();

            for (WhereisData data : list){
                if (data.getName().startsWith(args[1])){
                    data.setActive(false);
                    if (new WhereisDataAPI(plugin, con).updateData(data)){
                        sender.sendMessage(ChatColor.GREEN + "「"+args[1]+"」を削除しました。");
                    } else {
                        sender.sendMessage(ChatColor.RED + "削除失敗しました。");
                    }
                }
            }
        }

        return true;
    }
}
