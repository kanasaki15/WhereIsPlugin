package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystem;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystemResult;
import xyz.n7mn.dev.whereisplugin.event.WhereisCompleteCommandEvent;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

class CommandDelete {

    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;

    public CommandDelete(WhereIsPlugin p, String[] args, Player player) {
        this.plugin = p;
        this.args = args;
        this.player = player;
    }

    public boolean run(){

        DataSystemResult systemResult;
        if (player != null){
            systemResult = new DataSystem(plugin, player).delData(args[1]);
        } else {
            systemResult = new DataSystem(plugin).delData(args[1]);
        }

        String msg = ChatColor.YELLOW + new MessageList().getDelSuccess(args[1]);
        if (systemResult.isError()){
            msg = ChatColor.RED + systemResult.getErrorMessage();
        }

        CommandSender sender = plugin.getServer().getConsoleSender();
        if (player != null){
            sender = player;
        }

        plugin.getServer().getPluginManager().callEvent(new WhereisCompleteCommandEvent(sender, msg, systemResult.isError()));


        return true;
    }
}
