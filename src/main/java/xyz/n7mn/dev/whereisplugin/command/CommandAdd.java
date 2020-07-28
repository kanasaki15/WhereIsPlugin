package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystem;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystemResult;
import xyz.n7mn.dev.whereisplugin.event.Player.CreateCompleteWhereLocationEvent;
import xyz.n7mn.dev.whereisplugin.event.ServerCommand.CreateCompleteWhereLocationServerCommandEvent;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

class CommandAdd {

    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;

    public CommandAdd(WhereIsPlugin p, String[] args, Player player) {
        this.plugin = p;
        this.args = args;
        this.player = player;
    }

    public boolean run(){

        if (args.length != 6){
            return false;
        }

        DataSystemResult systemResult;
        if (player != null){
            systemResult = new DataSystem(plugin, player).addData(args[5], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        } else {
            systemResult = new DataSystem(plugin).addData(args[5], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        }

        String msg = ChatColor.YELLOW + new MessageList().getAddSuccessMessage(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]),args[5]);
        if (systemResult.isError()){
            msg = ChatColor.RED + systemResult.getErrorMessage();
        }

        if (player != null){
            plugin.getServer().getPluginManager().callEvent(new CreateCompleteWhereLocationEvent(msg, args[5], player, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), systemResult.isError()));
        }else{
            plugin.getServer().getPluginManager().callEvent(new CreateCompleteWhereLocationServerCommandEvent(msg, args[5], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), systemResult.isError()));
        }


        return true;
    }
}
