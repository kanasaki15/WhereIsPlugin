package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;

public class WhereisUpdate extends CommandInterface {

    private Plugin plugin;
    private Connection con;

    public WhereisUpdate(Plugin plugin, Connection con){
        this.plugin = plugin;
        this.con = con;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
}
