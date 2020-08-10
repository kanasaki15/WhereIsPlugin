package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.api.WhereData;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;
import xyz.n7mn.dev.whereisplugin.event.WhereisCompleteCommandEvent;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

import java.util.List;


class CommandAdmin {
    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;
    private WhereIsData WhereIsAPI;

    @Deprecated
    public CommandAdmin(WhereIsPlugin p, String[] args, Player player) {
        this.plugin = p;
        this.args = args;
        this.player = player;
        this.WhereIsAPI = new WhereIsData();
    }

    public CommandAdmin(WhereIsPlugin p, String[] args, Player player, WhereIsData api) {
        this.plugin = p;
        this.args = args;
        this.player = player;
        this.WhereIsAPI = api;
    }

    public boolean run(){

        if ((args.length == 2 || args.length == 3) && args[1].equals("list")){

            CommandSender sender = null;
            List<WhereData> list = WhereIsAPI.getDataListByALL();

            int pageCount = 5;

            if (player == null){
                pageCount = list.size();
                sender = plugin.getServer().getConsoleSender();
            }else{
                sender = (CommandSender)player;
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

                if (list.get(i - 1).getUUID() != null){
                    UserName = plugin.getServer().getPlayer(list.get(i - 1).getUUID()).getName();
                }

                if (list.get(i - 1).getUUID() != null && UserName.equals("Unknown")){
                    UserName = plugin.getServer().getOfflinePlayer(list.get(i - 1).getUUID()).getName();
                }

                if (list.get(i - 1).getUUID() != null && UserName.equals("Unknown")) {
                    UserName = "Unknown (UUID : " + list.get(i - 1).getUUID() + ")";
                }

                if (list.get(i - 1).getUUID() == null){
                    UserName = "Console";
                }

                sender.sendMessage(ChatColor.YELLOW + "ID: "+list.get(i - 1).getID()+" Name: "+list.get(i - 1).getLocationName()+" CreateUser: "+UserName + " Active: "+list.get(i - 1).isActive());
                sender.sendMessage(ChatColor.YELLOW + "StartX: "+list.get(i - 1).getStartX()+" EndX: "+list.get(i - 1).getEndX()+" StartZ: "+list.get(i - 1).getStartZ()+" EndZ: "+list.get(i - 1).getEndZ());
                sender.sendMessage(ChatColor.YELLOW + "----");
            }
        }

        if (args.length == 3 && args[1].equals("del")){

            WhereData whereData = WhereIsAPI.getWhereData(Integer.parseInt(args[2]));
            boolean b = WhereIsAPI.deleteWhereData(Integer.parseInt(args[2]));
            String msg = ChatColor.YELLOW + new MessageList().getDelSuccess(whereData.getLocationName());
            if (!b){
                msg = ChatColor.RED + WhereIsAPI.getErrorMessage();
            }

            CommandSender sender = plugin.getServer().getConsoleSender();
            if (player != null){
                sender = player;
            }

            WhereisCompleteCommandEvent event = new WhereisCompleteCommandEvent(sender, msg, !b);
            plugin.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()){
                sender.sendMessage(msg);
            }

        }

        return true;
    }
}
