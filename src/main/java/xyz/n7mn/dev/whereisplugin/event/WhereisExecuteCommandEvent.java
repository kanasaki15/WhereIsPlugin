package xyz.n7mn.dev.whereisplugin.event;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WhereisExecuteCommandEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();

    private boolean cancel = false;
    private CommandSender commandSender;
    private String Message;
    private String Mode;

    public WhereisExecuteCommandEvent(String Mode, CommandSender sender){
        this.commandSender = sender;
        this.Mode = Mode;
    }

    @Override
    public String getEventName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
    public void setCancelled(boolean cancel, String ReasonMessage) {
        this.cancel = cancel;
        this.Message = ReasonMessage;
    }

    public boolean isPlayer(){
        if (commandSender instanceof Player){
            return true;
        } else {
            return false;
        }
    }

    public CommandSender getSender(){
        return commandSender;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
