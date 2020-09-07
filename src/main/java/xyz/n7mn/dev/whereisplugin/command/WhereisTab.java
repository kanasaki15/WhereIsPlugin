package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class WhereisTab implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args.length == 1){
            return true;
        }

        if (args.length == 2 && (args[0].equals("add") || args[0].equals("update") || args[0].equals("delete"))){
            return true;
        }

        if (args.length == 3 && args[0].equals("update")){
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length == 1){
            list.add("axeget");
            list.add("axeclear");
            list.add("add");
            list.add("update");
            list.add("delete");
        }

        if (args.length == 2 && (args[0].equals("add") || args[0].equals("update") || args[0].equals("delete"))){
            list.add("Name");
        }

        if (args.length == 3 && args[0].equals("update")){
            list.add("NewName");
        }

        return list;
    }
}
