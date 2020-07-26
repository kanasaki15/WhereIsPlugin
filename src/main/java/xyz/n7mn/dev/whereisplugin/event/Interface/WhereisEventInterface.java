package xyz.n7mn.dev.whereisplugin.event.Interface;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public interface WhereisEventInterface extends Cancellable {

    boolean isCancelled();
    void setCancelled(boolean cancel);
    String getEventName();
    Player getExePlayer();
    String getLocationName();
    int getStartX();
    int getEndX();
    int getStartZ();
    int getEndZ();
    String getMessage();
    void setMessage(String Message);
    HandlerList getHandlers();

}
