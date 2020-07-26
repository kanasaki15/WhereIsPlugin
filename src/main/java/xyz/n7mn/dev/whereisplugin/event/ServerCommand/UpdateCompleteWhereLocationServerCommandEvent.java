package xyz.n7mn.dev.whereisplugin.event.ServerCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.n7mn.dev.whereisplugin.event.Interface.WhereisEventInterface;

public class UpdateCompleteWhereLocationServerCommandEvent extends Event implements Cancellable, WhereisEventInterface {

    private static final HandlerList handlerList = new HandlerList();

    private boolean cancel = false;
    private String OldName;
    private String NewName;

    private String Message;
    private boolean errFlag;

    private final String eventName = "UpdateCompleteWhereLocationEvent";

    public UpdateCompleteWhereLocationServerCommandEvent(String Message, String oldName, String newName , boolean ErrorFlag){
        this.Message = Message;
        this.OldName = oldName;
        this.NewName = newName;
        this.errFlag = ErrorFlag;
    }

    @Override
    @Deprecated
    public Player getExePlayer() {
        return null;
    }

    public CommandSender getCommandSender(){
        return Bukkit.getConsoleSender();
    }

    @Override
    @Deprecated
    public String getLocationName() {
        return OldName;
    }

    public String getOldLocationName(){
        return OldName;
    }

    public String getNewLocationName(){
        return NewName;
    }

    @Override
    @Deprecated
    public int getStartX() {
        return 0;
    }

    @Override
    @Deprecated
    public int getEndX() {
        return 0;
    }

    @Override
    @Deprecated
    public int getStartZ() {
        return 0;
    }

    @Override
    @Deprecated
    public int getEndZ() {
        return 0;
    }

    @Override
    public String getMessage() {
        return Message;
    }

    @Override
    public void setMessage(String Message) {
        this.Message = Message;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    public boolean isErrFlag() {
        return errFlag;
    }

}