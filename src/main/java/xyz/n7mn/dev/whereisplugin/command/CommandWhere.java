package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.api.WhereData;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;
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

        if (player == null && args.length != 4){
            return false;
        }

        StringBuffer sb = new StringBuffer();

        List<int[]> tempList = new ArrayList<>();
        List<WhereData> data;

        if (player != null){
            data = WhereIsAPI.getWhereListByLocation(player.getLocation());
        } else {
            data = WhereIsAPI.getWhereListByLocation(new Location(Bukkit.getWorld(args[1]),Integer.parseInt(args[2]), 0, Integer.parseInt(args[3])));
        }


        for (int i = 0; i < data.size(); i++){

            int[] temp = {data.get(i).getStartX(), data.get(i).getEndX(), data.get(i).getStartZ(), data.get(i).getEndZ()};
            tempList.add(temp);
            sb.append(data.get(i).getLocationName());
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
