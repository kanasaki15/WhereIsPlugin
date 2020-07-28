package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.function.MessageList;


class CommandHelp {
    private Player player;
    private ConsoleCommandSender sender = Bukkit.getConsoleSender();

    private MessageList lnMsg = new MessageList();

    public CommandHelp(Player player) {

        this.player = player;
    }

    public boolean run(){

        String[] msg = new String[8];

        msg[0] = ChatColor.YELLOW + "--- WhereIsPlugin Help ---";
        if (player != null){
            msg[1] = ChatColor.YELLOW + "/where  -- "+lnMsg.getCommandWhereMessage();
        }else{
            msg[1] = ChatColor.YELLOW + "/where [X] [Z]  -- "+lnMsg.getCommandWhereMessage();
        }
        msg[2] = ChatColor.YELLOW + "/where help -- "+lnMsg.getCommandHelpMessage();
        msg[3] = ChatColor.YELLOW + "/where add ["+lnMsg.getStartXMessage()+"] ["+lnMsg.getEndXMessage()+"] ["+lnMsg.getStartZMessage()+"] ["+lnMsg.getEndZMessage()+"] ["+lnMsg.getNameMessage()+"]  -- "+lnMsg.getCommandWhereAddMessage();
        msg[4] = ChatColor.YELLOW + "/where del ["+lnMsg.getNameMessage()+"]  -- "+lnMsg.getCommandWhereDelMessage();
        msg[5] = ChatColor.YELLOW + "/where update ["+lnMsg.getOldNameMessage()+"] ["+lnMsg.getNewNameMessage()+"]  -- "+lnMsg.getCommandWhereUpdateMessage();
        msg[6] = ChatColor.YELLOW + "/where system  -- "+lnMsg.getCommandSystemMessage();
        msg[7] = ChatColor.YELLOW + "/where admin [list|del] -- "+lnMsg.getCommandAdminMessage();

        if (player != null){
            player.sendMessage(msg);
        } else {
            sender.sendMessage(msg);
        }

        return true;
    }
}
