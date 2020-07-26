package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataResult;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystem;
import xyz.n7mn.dev.whereisplugin.event.Player.CheckWhereLocationEvent;
import xyz.n7mn.dev.whereisplugin.event.ServerCommand.CheckWhereLocationServerCommandEvent;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

import java.util.List;

public class CommandWhere {

    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;

    public CommandWhere(WhereIsPlugin p, String[] args, Player player) {
        this.plugin = p;
        this.args = args;
        this.player = player;
    }

    public boolean run(){

        if (args.length != 0 && player != null){
            return false;
        }

        if (player == null && args.length != 2){
            return false;
        }

        List<DataResult> data;
        if (player != null){
            data = new DataSystem(plugin, player).getData(player);
        } else {
            data = new DataSystem(plugin).getData(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.size(); i++){
            sb.append(data.get(i).LocationName);
            if (i + 1 < data.size()){
                sb.append(",");
            }
        }

        String msg = new MessageList().getHereMessage(sb.toString());
        if (player != null){
            plugin.getServer().getPluginManager().callEvent(new CheckWhereLocationEvent(msg, sb.toString(), player, player.getLocation().getBlockX(),player.getLocation().getBlockZ()));
        } else {
            plugin.getServer().getPluginManager().callEvent(new CheckWhereLocationServerCommandEvent(msg, sb.toString(), Integer.parseInt(args[0]), Integer.parseInt(args[1])));
        }

        return true;
    }
}
