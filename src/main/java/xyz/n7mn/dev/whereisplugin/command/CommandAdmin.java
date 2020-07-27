package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.Result.DataResult;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystem;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystemResult;
import xyz.n7mn.dev.whereisplugin.event.Player.DeleteCompleteWhereLocationEvent;
import xyz.n7mn.dev.whereisplugin.event.ServerCommand.DeleteCompleteWhereLocationServerCommandEvent;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

import java.util.List;


public class CommandAdmin {
    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;
    private DataSystem system;

    public CommandAdmin(WhereIsPlugin p, String[] args, Player player) {
        this.plugin = p;
        this.args = args;
        this.player = player;

        if (player != null){
            system = new DataSystem(p, player);
        }else{
            system = new DataSystem(p);
        }

    }

    public boolean run(){

        if ((args.length == 2 || args.length == 3) && args[1].equals("list")){

            CommandSender sender = null;
            List<DataResult> list = system.getDataAllList();

            int pageCount = 5;

            if (player == null){
                pageCount = list.size();
                sender = plugin.getServer().getConsoleSender();
            }else{
                sender = (CommandSender) player;
            }
            int pageMaxCount = list.size() / pageCount;
            if (pageMaxCount < 1){ pageMaxCount = 1; }

            int pageNowCount = 0;
            if (args.length == 3){
                pageNowCount = Integer.parseInt(args[2]) - 1;
            }
            if (pageNowCount < 0 || player == null) { pageNowCount = 0;}

            String page = (pageNowCount + 1)+" / "+pageMaxCount;

            if (player == null){ page = ""; }


            sender.sendMessage(ChatColor.YELLOW + "---- WhereIsPlugin Admin Menu "+page+" ----");

            for (int i = (list.size() - (pageNowCount * pageCount)); i > (list.size() - (pageNowCount * pageCount)) - pageCount; i--){

                if (i - 1 < 0){ break; }

                String UserName = "Unknown";

                if (list.get(i - 1).UUID != null && plugin.getServer().getPlayer(list.get(i - 1).UUID) != null){
                    UserName = plugin.getServer().getPlayer(list.get(i - 1).UUID).getName();
                }

                if (list.get(i - 1).UUID != null && plugin.getServer().getPlayer(list.get(i - 1).UUID) == null){
                    UserName = plugin.getServer().getOfflinePlayer(list.get(i - 1).UUID).getName();
                }

                if (list.get(i - 1).UUID != null && plugin.getServer().getOfflinePlayer(list.get(i - 1).UUID) == null) {
                    UserName = "Unknown (UUID : " + list.get(i - 1).UUID + ")";
                }

                sender.sendMessage(ChatColor.YELLOW + "ID: "+list.get(i - 1).ID+" Name: "+list.get(i - 1).LocationName+" CreateUser: "+UserName + " Active: "+list.get(i - 1).Active);
                sender.sendMessage(ChatColor.YELLOW + "StartX: "+list.get(i - 1).StartX+" EndX: "+list.get(i - 1).EndX+" StartZ: "+list.get(i - 1).StartZ+" EndZ: "+list.get(i - 1).EndZ);
                sender.sendMessage(ChatColor.YELLOW + "----");
            }
        }

        if (args.length == 3 && args[1].equals("del")){

            List<DataResult> list = system.getDataAllList();
            for (int i = 0; i < list.size(); i++){

                if (list.get(i).ID == Integer.parseInt(args[2])){
                    DataSystemResult result = new DataSystem(plugin).delData(list.get(i).ID);
                    String msg = ChatColor.YELLOW + new MessageList().getDelSuccess(list.get(i).LocationName);
                    if (result.isError()){
                        msg = ChatColor.RED + result.getErrorMessage();
                    }

                    if (player != null){
                        plugin.getServer().getPluginManager().callEvent(new DeleteCompleteWhereLocationEvent(msg, list.get(i).LocationName, player, result.isError()));
                    }else{
                        plugin.getServer().getPluginManager().callEvent(new DeleteCompleteWhereLocationServerCommandEvent(msg, list.get(i).LocationName, result.isError()));
                    }

                    return true;
                }
            }
        }

        return true;
    }
}
