package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataResult;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystem;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

import java.util.ArrayList;
import java.util.List;

public class CommandTab implements TabExecutor {

    WhereIsPlugin plugin;

    public CommandTab(WhereIsPlugin p){
        plugin = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> list = new ArrayList<>();
        Player player = null;
        if (sender instanceof Player){
            player = (Player)sender;
        }

        if (args.length == 1){
            list.add("help");
            list.add("add");
            list.add("del");
            list.add("update");
            list.add("system");
            list.add("admin");
        }

        if (args.length > 1 && args[0].equals("add")){
            if (args.length <= 3){
                if (player != null){
                    list.add(""+player.getLocation().getBlockX());
                }else{
                    if (args.length == 2){
                        list.add(new MessageList().getStartXMessage());
                    }else{
                        list.add(new MessageList().getEndXMessage());
                    }
                }
            }
            if (args.length > 3 && args.length <= 5){
                if (player != null){
                    list.add(""+player.getLocation().getBlockZ());
                }else{
                    if (args.length == 4){
                        list.add(new MessageList().getStartZMessage());
                    }else{
                        list.add(new MessageList().getEndZMessage());
                    }
                }
            }

            if (args.length == 6){
                list.add("Name");
            }
        }

        if (args.length == 2 && args[0].equals("del")){
            DataSystem system;
            if (player != null){
                system = new DataSystem(plugin,player);
            }else{
                system = new DataSystem(plugin);
            }
            List<DataResult> dataList = system.getDataAllList();
            for (int i = 0; i < dataList.size(); i++){
                list.add(dataList.get(i).LocationName);
            }
        }

        if (args.length == 2 && args[0].equals("update")){
            DataSystem system;
            if (player != null){
                system = new DataSystem(plugin,player);
            }else{
                system = new DataSystem(plugin);
            }
            List<DataResult> dataList = system.getDataAllList();
            for (int i = 0; i < dataList.size(); i++){
                list.add(dataList.get(i).LocationName);
            }
        }

        if (args.length == 3 && args[0].equals("update")){
            DataSystem system;
            if (player != null){
                system = new DataSystem(plugin,player);
            }else{
                system = new DataSystem(plugin);
            }
            List<DataResult> dataList = system.getDataAllList();
            for (int i = 0; i < dataList.size(); i++){
                list.add(dataList.get(i).LocationName);
            }
        }

        if (args.length == 2 && args[0].equals("admin")){
            list.add("list");
            list.add("del");
        }

        if (args.length == 3 && args[0].equals("admin") && args[1].equals("list")){
            list.add("pageCount");
        }

        if (args.length == 3 && args[0].equals("admin") && args[1].equals("del")){
            list.add("ID");
        }

        return list;
    }
}
