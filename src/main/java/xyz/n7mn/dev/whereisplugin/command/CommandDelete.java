package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystem;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystemResult;
import xyz.n7mn.dev.whereisplugin.event.Player.DeleteCompleteWhereLocationEvent;
import xyz.n7mn.dev.whereisplugin.event.ServerCommand.DeleteCompleteWhereLocationServerCommandEvent;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

public class CommandDelete {

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

        if (player != null){
            plugin.getServer().getPluginManager().callEvent(new DeleteCompleteWhereLocationEvent(msg, args[1], player, systemResult.isError()));
        }else{
            plugin.getServer().getPluginManager().callEvent(new DeleteCompleteWhereLocationServerCommandEvent(msg, args[1], systemResult.isError()));
        }


        return true;
    }
}
