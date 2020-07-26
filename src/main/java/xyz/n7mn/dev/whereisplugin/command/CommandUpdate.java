package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystem;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystemResult;
import xyz.n7mn.dev.whereisplugin.event.Player.UpdateCompleteWhereLocationEvent;
import xyz.n7mn.dev.whereisplugin.event.ServerCommand.UpdateCompleteWhereLocationServerCommandEvent;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

public class CommandUpdate {

    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;
    DataSystem system;
    MessageList messageList = new MessageList();

    public CommandUpdate(WhereIsPlugin p, String[] args, Player player){
        this.plugin = p;
        this.args = args;
        this.player = player;
    }

    public boolean run(){

        if (player != null){
            system = new DataSystem(plugin, player);
        }else{
            system = new DataSystem(plugin);
        }

        DataSystemResult result = system.updateData(args[1], args[2]);

        String msg;
        if (result.isError()){
            msg = ChatColor.RED + result.getErrorMessage();
        }else{
            msg = ChatColor.YELLOW + messageList.getUpdateSuccessMessage(args[1], args[2]);
        }

        if (player != null){
            plugin.getServer().getPluginManager().callEvent(new UpdateCompleteWhereLocationEvent(msg, player, args[1], args[2], result.isError()));
        }else{
            plugin.getServer().getPluginManager().callEvent(new UpdateCompleteWhereLocationServerCommandEvent(msg, args[1], args[2], result.isError()));
        }

        return true;
    }
}
