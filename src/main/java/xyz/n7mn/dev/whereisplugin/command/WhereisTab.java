package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.whereisplugin.data.Data;

import java.util.ArrayList;
import java.util.List;

public class WhereisTab implements TabExecutor {

    public Plugin plugin;

    public WhereisTab(Plugin p){
        plugin = p;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        //plugin.getLogger().info(""+args.length);
        List<String> list = new ArrayList<String>();

        if (args.length == 1 && sender instanceof Player){
            list.add("add");
            list.add("del");
            list.add("update");
            list.add("help");
            list.add("system");
            list.add("admin");

            return list;
        }else if (args.length == 1){
            list.add("update");
            list.add("help");
            list.add("system");
            list.add("admin");

            return list;
        }

        if (args.length == 2 && args[0].equals("admin")){
            list.add("list");
            list.add("del");

            return list;
        }

        if (args.length == 3 && args[0].equals("admin")){
            if (args[1].equals("list")){
                Data[] allList = new Data(plugin).getDataAllList();
                int pageMax = (allList.length / 5);
                if (pageMax < 1){ pageMax = 1; }
                for (int i = 1; i <= pageMax; i++){
                    list.add(""+i);
                }
            }else if (args[1].equals("del")){
                Data[] allList = new Data(plugin).getDataAllList();
                for (int i = 0; i < allList.length; i++){
                    if (allList[i].Active){
                        list.add(""+allList[i].ID);
                    }
                }
            }
            return list;
        }

        if ((args.length == 2 || args.length == 3) && sender instanceof Player){
            Player player = (Player)sender;
            if (args[0].startsWith("add")){
                list.add(""+player.getLocation().getBlockX());
            }else if (args[0].startsWith("update") || (args[0].startsWith("del") && args.length == 2)){
                Data[] dataList = new Data(plugin).getDataList(player);
                for(int i = 0; i < dataList.length; i++){
                    list.add(dataList[i].Name);
                }
            }

            return list;
        }

        if ((args.length == 4 || args.length == 5) && args[0].startsWith("add") && sender instanceof Player){
            Player player = (Player)sender;
            list.add(""+player.getLocation().getBlockZ());
        }

        if ((args.length == 6) && args[0].startsWith("add") && sender instanceof Player){
            Player player = (Player)sender;
            list.add("NewLocationName");
        }

        return list;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
}
