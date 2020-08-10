package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;
import xyz.n7mn.dev.whereisplugin.dataSystem.Result.DataResult;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystem;
import xyz.n7mn.dev.whereisplugin.event.WhereisCompleteCommandEvent;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

import java.util.ArrayList;
import java.util.List;

class CommandWhere {

    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;
    private WhereIsData WhereIsAPI;

    @Deprecated
    public CommandWhere(WhereIsPlugin p, String[] args, Player player) {
        this.plugin = p;
        this.args = args;
        this.player = player;
        this.WhereIsAPI = new WhereIsData();
    }

    public CommandWhere(WhereIsPlugin p, String[] args, Player player, WhereIsData api) {
        this.plugin = p;
        this.args = args;
        this.player = player;
        this.WhereIsAPI = api;
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

        List<int[]> tempList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++){

            int[] temp = {data.get(i).StartX, data.get(i).EndX, data.get(i).StartZ, data.get(i).EndZ};
            tempList.add(temp);
            sb.append(data.get(i).LocationName);
            if (i + 1 < data.size()){
                sb.append(",");
            }
        }

        String msg = new MessageList().getHereMessage(sb.toString());

        CommandSender sender = plugin.getServer().getConsoleSender();
        if (player != null){
            sender = player;
        }

        for (int i = 0; i < tempList.size(); i++){
            WhereisCompleteCommandEvent event = new WhereisCompleteCommandEvent(sender, msg, tempList.get(i)[0], tempList.get(i)[1], tempList.get(i)[2], tempList.get(i)[3], false);
            plugin.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()){
                sender.sendMessage(msg);
            }
        }

        return true;
    }
}
