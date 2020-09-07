package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;

public abstract class CommandInterface implements CommandExecutor {

    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);
}
