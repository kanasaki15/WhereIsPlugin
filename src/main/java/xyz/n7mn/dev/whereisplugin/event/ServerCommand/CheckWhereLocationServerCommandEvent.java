package xyz.n7mn.dev.whereisplugin.event.ServerCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import xyz.n7mn.dev.whereisplugin.event.Interface.WhereisEventInterface;

public class CheckWhereLocationServerCommandEvent extends Event implements Cancellable, WhereisEventInterface {

    private static final HandlerList handlerList = new HandlerList();

    private boolean cancel = false;
    private String Name;
    private int StartX;
    private int StartZ;
    private String Message;

    public CheckWhereLocationServerCommandEvent(String Message, String LocationName, int X, int Z){
        this.Message = Message;
        this.Name = LocationName;
        this.StartX = X;
        this.StartZ = Z;
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
    @Deprecated
    public int getStartX() {
        return StartX;
    }

    @Override
    @Deprecated
    public int getEndX() {
        return getStartX();
    }

    @Override
    @Deprecated
    public int getStartZ() {
        return StartZ;
    }

    @Override
    @Deprecated
    public int getEndZ() {
        return getStartZ();
    }

    public int getX(){ return StartX; }
    public int getZ(){ return StartZ; }

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
