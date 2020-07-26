package xyz.n7mn.dev.whereisplugin.event.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.n7mn.dev.whereisplugin.event.Interface.WhereisEventInterface;

public class DeleteCompleteWhereLocationEvent extends Event implements Cancellable, WhereisEventInterface {

    private static final HandlerList handlerList = new HandlerList();

    private boolean cancel = false;
    private Player player;
    private String Name;
    private String Message;
    private boolean errFlag;

    private final String eventName = "DeleteCompleteWhereLocationEvent";

    public DeleteCompleteWhereLocationEvent (String Message, String LocationName, Player ExePlayer, boolean ErrorFlag){
        this.Message = Message;
        this.Name = LocationName;
        this.player = ExePlayer;
        this.errFlag = ErrorFlag;
    }

    @Override
    public String getEventName() {
        return eventName;
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
    public Player getExePlayer() {
        return player;
    }

    @Override
    public String getLocationName() {
        return Name;
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
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public boolean isError(){
        return errFlag;
    }
}
