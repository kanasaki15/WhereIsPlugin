package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;

public class WhereisAdd implements CommandExecutor {

    private final Plugin plugin;
    private final Connection con;

    public WhereisAdd(Plugin plugin, Connection con){
        this.plugin = plugin;
        this.con = con;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 2){

        }

        return true;
    }
}
