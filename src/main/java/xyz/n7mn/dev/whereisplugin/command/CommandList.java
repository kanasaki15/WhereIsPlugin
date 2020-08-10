package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.api.WhereData;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystem;
import xyz.n7mn.dev.whereisplugin.dataSystem.Result.DataResult;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

import java.util.ArrayList;
import java.util.List;


public class CommandList {
    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;

    private WhereIsData WhereIsAPI;

    @Deprecated
    public CommandList(WhereIsPlugin p, Player player) {
        this.plugin = p;
        this.player = player;
        this.WhereIsAPI = new WhereIsData();
    }

    public CommandList(WhereIsPlugin p, Player player, WhereIsData api) {
        this.plugin = p;
        this.player = player;
        this.WhereIsAPI = api;
    }

    public boolean run(){

        CommandSender sender = plugin.getServer().getConsoleSender();
        if (player != null){
            sender = player;
        } else {
            sender.sendMessage(ChatColor.RED + "Use /where admin list");
            return true;
        }


        List<WhereData> list = new ArrayList<>();
        if (player != null){
            list = WhereIsAPI.getDataListByUser(player.getUniqueId());
        } else {
            list = WhereIsAPI.getDataListByALL();
        }

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).isActive() && list.get(i).getUUID().equals(player.getUniqueId())){
                continue;
            }

            list.remove(i);
        }


        sender.sendMessage(ChatColor.YELLOW + "--- "+new MessageList().getListMessage() +" ---");

        //int maxCount = list.size();
        //if (maxCount > 5){ maxCount = 5; }

        for (int i = list.size() - 1; i > ((list.size() - 1) - 5); i--){
            if (i < 0) {
                break;
            }
            sender.sendMessage(ChatColor.YELLOW + "Name: "+list.get(i).getLocationName()+" Active: "+list.get(i).isActive());
            sender.sendMessage(ChatColor.YELLOW + "StartX: "+list.get(i).getStartX()+" EndX: "+list.get(i).getEndX()+" StartZ: "+list.get(i).getStartZ()+" EndZ: "+list.get(i).getEndZ());
        }


        return true;
    }
}