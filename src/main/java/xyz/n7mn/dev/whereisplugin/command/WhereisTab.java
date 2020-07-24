package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class WhereisTab implements TabExecutor {

    public Plugin plugin;

    public WhereisTab(Plugin p){
        plugin = p;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        plugin.getLogger().info(""+args.length);
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
}
