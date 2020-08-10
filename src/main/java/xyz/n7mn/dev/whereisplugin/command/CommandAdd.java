package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystem;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystemResult;
import xyz.n7mn.dev.whereisplugin.event.WhereisCompleteCommandEvent;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

class CommandAdd {

    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;
    private final MessageList messageList = new MessageList();
    private WhereIsData WhereIsAPI;

    @Deprecated
    public CommandAdd(WhereIsPlugin p, String[] args, Player player) {
        this.plugin = p;
        this.args = args;
        this.player = player;
        this.WhereIsAPI = new WhereIsData();
    }

    public CommandAdd(WhereIsPlugin p, String[] args, Player player, WhereIsData api) {
        this.plugin = p;
        this.args = args;
        this.player = player;
        this.WhereIsAPI = api;
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


        CommandSender sender = plugin.getServer().getConsoleSender();

        if (player != null){
            sender = player;
        }

        WhereisCompleteCommandEvent event = new WhereisCompleteCommandEvent(sender, msg, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), systemResult.isError());
        plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()){
            sender.sendMessage(msg);
        }

        return true;
    }
}
