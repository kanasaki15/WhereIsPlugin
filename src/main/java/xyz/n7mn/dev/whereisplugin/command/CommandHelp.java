package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

import java.util.ArrayList;
import java.util.List;


class CommandHelp {
    private Player player;
    private ConsoleCommandSender sender = Bukkit.getConsoleSender();

    private MessageList lnMsg = new MessageList();

    public CommandHelp(Player player) {

        this.player = player;
    }

    public boolean run(){

        List<String> msgList = new ArrayList<>();

        msgList.add("--- WhereIsPlugin Help ---");

        boolean isCheck = true;
        boolean isAdd = true;
        boolean isDel = true;
        boolean isUpdate = true;
        boolean isSystem = false;
        boolean isAdmin = false;
        boolean isList = false;
        boolean isPlayer = (player != null);

        if (isPlayer){
            isList = true;

            if (player.isOp()){
                isAdmin = true;
                isSystem = true;
            }

            if (Bukkit.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                if (!player.hasPermission("whereis.check")){
                    isCheck = false;
                }

                if (!player.hasPermission("whereis.add")){
                    isAdd = false;
                }

                if (!player.hasPermission("whereis.del")){
                    isDel = false;
                }

                if (!player.hasPermission("whereis.update")){
                    isUpdate = false;
                }

                if (player.hasPermission("whereis.system")){
                    isAdd = true;
                }

                if (player.hasPermission("whereis.admin")){
                    isAdmin = true;
                }

                if (!player.hasPermission("whereis.list")){
                    isList = false;
                }
            }
        }

        if (isCheck){
            if (isPlayer){
                msgList.add("/where  -- "+lnMsg.getCommandWhereMessage());
            } else {
                msgList.add("/where [worldname] [X] [Z]  -- "+lnMsg.getCommandWhereMessage());
            }
        }
        msgList.add("/where help -- "+lnMsg.getCommandHelpMessage());

        if (isAdd){
            if (isPlayer){
                msgList.add("/where add ["+lnMsg.getStartXMessage()+"] ["+lnMsg.getEndXMessage()+"] ["+lnMsg.getStartZMessage()+"] ["+lnMsg.getEndZMessage()+"] ["+lnMsg.getNameMessage()+"]  -- "+lnMsg.getCommandWhereAddMessage());
            } else {
                msgList.add("/where add [worldname] ["+lnMsg.getStartXMessage()+"] ["+lnMsg.getEndXMessage()+"] ["+lnMsg.getStartZMessage()+"] ["+lnMsg.getEndZMessage()+"] ["+lnMsg.getNameMessage()+"]  -- "+lnMsg.getCommandWhereAddMessage());
            }
        }

        if (isDel){
            msgList.add("/where del ["+lnMsg.getNameMessage()+"]  -- "+lnMsg.getCommandWhereDelMessage());
        }

        if (isUpdate){
            msgList.add("/where update ["+lnMsg.getOldNameMessage()+"] ["+lnMsg.getNewNameMessage()+"]  -- "+lnMsg.getCommandWhereUpdateMessage());
        }

        if (isSystem){
            msgList.add("/where system  -- "+lnMsg.getCommandSystemMessage());
        }

        if (isAdmin){
            msgList.add("/where admin [list|del] -- "+lnMsg.getCommandAdminMessage());
            msgList.add("/where import [mysql|json] -- "+lnMsg.getCommandImportMessage());
        }

        if (isList){
            msgList.add("/where list -- " + lnMsg.getCommandListMessage());
        }

        for (int i = 0; i < msgList.size(); i++){
            if (isPlayer){
                player.sendMessage(ChatColor.YELLOW + msgList.get(i));
            } else {
                sender.sendMessage(ChatColor.YELLOW + msgList.get(i));
            }
        }

        return true;
    }
}
