package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystem;
import xyz.n7mn.dev.whereisplugin.dataSystem.Result.DataResult;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

import java.util.List;


public class CommandList {
    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;

    public CommandList(WhereIsPlugin p, Player player) {
        this.plugin = p;
        this.player = player;
    }

    public boolean run(){

        DataSystem dataSystem;
        CommandSender sender = plugin.getServer().getConsoleSender();
        if (player != null){
            dataSystem = new DataSystem(plugin, player);
            sender = player;
        } else {
            sender.sendMessage(ChatColor.RED + "Use /where admin list");
            return true;
        }

        List<DataResult> list = dataSystem.getDataAllList();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).Active && list.get(i).UUID.toString().equals(player.getUniqueId().toString())){
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
            sender.sendMessage(ChatColor.YELLOW + "Name: "+list.get(i).LocationName+" Active: "+list.get(i).Active);
            sender.sendMessage(ChatColor.YELLOW + "StartX: "+list.get(i).StartX+" EndX: "+list.get(i).EndX+" StartZ: "+list.get(i).StartZ+" EndZ: "+list.get(i).EndZ);
        }


        return true;
    }
}