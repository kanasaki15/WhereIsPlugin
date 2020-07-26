package xyz.n7mn.dev.whereisplugin.event.ServerCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.n7mn.dev.whereisplugin.event.Interface.WhereisEventInterface;

public class CreateCompleteWhereLocationServerCommandEvent extends Event implements Cancellable, WhereisEventInterface {

    private static final HandlerList handlerList = new HandlerList();

    private boolean cancel = false;
    private String Name;
    private int StartX;
    private int EndX;
    private int StartZ;
    private int EndZ;
    private String Message;
    private boolean errFlag;

    public CreateCompleteWhereLocationServerCommandEvent(String Message, String LocationName, int StartX, int EndX, int StartZ, int EndZ, boolean ErrorFlag){
        this.Message = Message;
        this.Name = LocationName;
        this.StartX = StartX;
        this.EndX = EndX;
        this.StartZ = StartZ;
        this.EndZ = EndZ;
        this.errFlag = ErrorFlag;
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
    public String getEventName() {
        return this.getClass().getName();
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
    public String getLocationName() {
        return Name;
    }

    @Override
    public int getStartX() {
        return StartX;
    }

    @Override
    public int getEndX() {
        return EndX;
    }

    @Override
    public int getStartZ() {
        return StartZ;
    }

    @Override
    public int getEndZ() {
        return EndZ;
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
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
