package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.api.DynmapNotFoundException;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;
import xyz.n7mn.dev.whereisplugin.api.WhereIsDataDynmap;
import xyz.n7mn.dev.whereisplugin.event.WhereisCompleteCommandEvent;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

public class CommandMarker {
    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;
    private final MessageList messageList = new MessageList();

    private WhereIsData WhereIsAPI;

    private WhereIsDataDynmap WhereIsDynmapAPI;
    private CommandSender sender = Bukkit.getConsoleSender();

    public CommandMarker(WhereIsPlugin p, String[] args, Player player, WhereIsData api) {
        this.plugin = p;
        this.args = args;
        this.player = player;
        this.WhereIsAPI = api;

        try {
            WhereIsDynmapAPI = new WhereIsDataDynmap();
        } catch (DynmapNotFoundException e) {
            WhereIsDynmapAPI = null;
        }

        if (player != null){
            sender = player;
        }
    }

    public boolean run(){

        if (WhereIsDynmapAPI == null){
            sender.sendMessage(ChatColor.RED + messageList.getDynmapNotFound());
            return true;
        }

        try {
            if (args.length == 3){
                if (args[1].equals("show")){
                    int id = -1;
                    if (player != null){
                        id = WhereIsAPI.getWhereDataID(args[2], player.getUniqueId());
                    } else {
                        id = WhereIsAPI.getWhereDataID(args[2], player.getUniqueId());
                    }

                    if (id != -1 && WhereIsDynmapAPI.addMarker(id)){
                        WhereisCompleteCommandEvent event = new WhereisCompleteCommandEvent(sender, ChatColor.GREEN + messageList.getDynmapAddSuccess(), false);
                        plugin.getServer().getPluginManager().callEvent(event);
                        if (!event.isCancelled()){
                            sender.sendMessage(event.getMessage());
                        }
                    } else {
                        WhereisCompleteCommandEvent event = new WhereisCompleteCommandEvent(sender, ChatColor.RED + messageList.getDynmapAddError(), true);
                        plugin.getServer().getPluginManager().callEvent(event);
                        if (!event.isCancelled()){
                            sender.sendMessage(event.getMessage());
                        }
                    }

                    return true;
                }

                if (args[1].equals("hide")) {
                    int id = -1;
                    if (player != null){
                        id = WhereIsAPI.getWhereDataID(args[2], player.getUniqueId());
                    } else {
                        id = WhereIsAPI.getWhereDataID(args[2], player.getUniqueId());
                    }

                    if (id != -1 && WhereIsDynmapAPI.delMarker(id)){
                        WhereisCompleteCommandEvent event = new WhereisCompleteCommandEvent(sender, ChatColor.GREEN + messageList.getDynmapDeleteSuccess(), false);
                        plugin.getServer().getPluginManager().callEvent(event);
                        if (!event.isCancelled()){
                            sender.sendMessage(event.getMessage());
                        }
                    } else {
                        WhereisCompleteCommandEvent event = new WhereisCompleteCommandEvent(sender, ChatColor.RED + messageList.getDynmapDeleteError(), true);
                        plugin.getServer().getPluginManager().callEvent(event);
                        if (!event.isCancelled()){
                            sender.sendMessage(event.getMessage());
                        }
                    }

                    return true;
                }
            }
        } catch (DynmapNotFoundException e) {
            // e.printStackTrace();
            WhereisCompleteCommandEvent event = new WhereisCompleteCommandEvent(sender, ChatColor.RED + "Plugin Error : " + e.getMessage(), true);
            plugin.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()){
                sender.sendMessage(event.getMessage());
            }
        }

        return true;
    }
}
