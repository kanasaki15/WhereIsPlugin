package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;

public class CommandMain implements CommandExecutor {

    WhereIsPlugin plugin;

    public CommandMain(WhereIsPlugin p){
        plugin = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if (args.length >= 0){

            Player p = null;
            if (sender instanceof Player){
                p = (Player)sender;
            }

            // /where
            if (args.length == 2 && !args[0].equals("del") && !args[0].equals("admin")){
                return new CommandWhere(plugin, args, p).run();
            }

            if (args.length == 0){
                return new CommandWhere(plugin, args, p).run();
            }

            // /where help
            if (args.length > 0 && args[0].equals("help")){
                return new CommandHelp(p).run();
            }

            // /where add
            if (args.length > 0 && args[0].equals("add")){
                return new CommandAdd(plugin, args, p).run();
            }

            // /where update
            if (args.length == 3 && args[0].equals("update")){
                return new CommandUpdate(plugin, args, p).run();
            }

            // /where del
            if (args.length == 2 && args[0].equals("del")){
                return new CommandDelete(plugin, args, p).run();
            }

            // /where system
            if (args.length > 0 && args[0].equals("system")){
                return new CommandSystem(p).run();
            }

            // /where admin
            if (args.length == 2 && args[0].equals("admin")){
                return new CommandAdmin(plugin, args, p).run();
            }

            if (args.length == 3 && args[0].equals("admin")){
                return new CommandAdmin(plugin, args, p).run();
            }
        }

        return false;
    }
}
