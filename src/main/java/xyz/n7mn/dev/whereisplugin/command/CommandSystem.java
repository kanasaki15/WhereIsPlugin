package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

class CommandSystem {

    private Player player;
    private ConsoleCommandSender sender = Bukkit.getConsoleSender();
    private Plugin plugin = Bukkit.getPluginManager().getPlugin("WhereIsPlugin");
    private WhereIsData WhereIsAPI;

    private MessageList lnMsg = new MessageList();

    public CommandSystem(Player player, WhereIsData api){
        this.player = player;
        this.WhereIsAPI = api;
    }

    public boolean run(){

        String[] msg = new String[5];

        msg[0] = ChatColor.YELLOW + "---- WhereIsPlugin SystemInfo ----";
        msg[1] = ChatColor.YELLOW + "Ver : " + plugin.getDescription().getVersion();
        msg[2] = ChatColor.YELLOW + "Use DataSystem : " + WhereIsAPI.getUseSystem() + "(Ver " + WhereIsAPI.getVersion() + ")";
        msg[3] = ChatColor.YELLOW + "Use SpigotAPI Version : " + plugin.getDescription().getAPIVersion();
        if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null){
            msg[4] = ChatColor.YELLOW + "Use LuckPerm Mode : True";
        }else{
            msg[4] = ChatColor.YELLOW + "Use LuckPerm Mode : False";
        }

        if (player != null){
            player.sendMessage(msg);
        } else {
            sender.sendMessage(msg);
        }

        return true;
    }
}
