package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.api.DynmapNotFoundException;
import xyz.n7mn.dev.whereisplugin.api.WhereData;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;
import xyz.n7mn.dev.whereisplugin.api.WhereIsDataDynmap;
import xyz.n7mn.dev.whereisplugin.event.WhereisCompleteCommandEvent;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

class CommandUpdate {

    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;
    MessageList messageList = new MessageList();
    WhereIsData WhereIsAPI;

    public CommandUpdate(WhereIsPlugin p, String[] args, Player player, WhereIsData api){
        this.plugin = p;
        this.args = args;
        this.player = player;
        this.WhereIsAPI = api;
    }

    public boolean run(){

        if (args.length == 3){
            int id = -1;
            if (player != null){
                id = WhereIsAPI.getWhereDataID(args[1], player.getUniqueId());
            } else {
                id = WhereIsAPI.getWhereDataID(args[1], null);
            }

            boolean b = false;
            if (id != -1){
                WhereData data = WhereIsAPI.getWhereData(id);
                b = WhereIsAPI.updateWhereData(id, args[2], data.getUUID(), data.getWorldName(), data.getStartX(), data.getEndX(), data.getStartZ(), data.getEndZ(), data.isActive());
            }

            String msg;
            if (!b){
                msg = ChatColor.RED + WhereIsAPI.getErrorMessage();
            }else{
                try {
                    if (new WhereIsDataDynmap().isDataExists(id)){
                        new WhereIsDataDynmap().updateMarker(id);
                    }
                } catch (DynmapNotFoundException e) {

                }

                msg = ChatColor.YELLOW + messageList.getUpdateSuccessMessage(args[1], args[2]);
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

        if (args.length == 5){
            // TODO: 範囲指定し直し
        }

        return true;
    }
}
