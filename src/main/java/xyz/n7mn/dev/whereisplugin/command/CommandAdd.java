package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;
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

        CommandSender sender = plugin.getServer().getConsoleSender();
        if (player != null){
            sender = player;
        }

        if (args.length != 6 && args.length != 7){
            sender.sendMessage(ChatColor.RED + new MessageList().getCommandSyntaxError());
            return true;
        }

        boolean b;
        if (player != null){
            b = WhereIsAPI.addWhereData(args[5], player.getUniqueId(), player.getLocation().getWorld().getName(), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), true);
        } else {
            b = WhereIsAPI.addWhereData(args[6], null, args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), true);
        }

        String msg = ChatColor.YELLOW + new MessageList().getAddSuccessMessage(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]),args[5]);
        if (!b){
            msg = ChatColor.RED + WhereIsAPI.getErrorMessage();
        }

        WhereisCompleteCommandEvent event = new WhereisCompleteCommandEvent(sender, msg, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), !b);
        plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()){
            sender.sendMessage(msg);
        }

        return true;
    }
}
