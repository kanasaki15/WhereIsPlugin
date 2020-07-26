package xyz.n7mn.dev.whereisplugin.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import xyz.n7mn.dev.whereisplugin.event.Player.CheckWhereLocationEvent;
import xyz.n7mn.dev.whereisplugin.event.Player.CreateCompleteWhereLocationEvent;
import xyz.n7mn.dev.whereisplugin.event.Player.DeleteCompleteWhereLocationEvent;
import xyz.n7mn.dev.whereisplugin.event.Player.UpdateCompleteWhereLocationEvent;
import xyz.n7mn.dev.whereisplugin.event.ServerCommand.CheckWhereLocationServerCommandEvent;
import xyz.n7mn.dev.whereisplugin.event.ServerCommand.CreateCompleteWhereLocationServerCommandEvent;
import xyz.n7mn.dev.whereisplugin.event.ServerCommand.DeleteCompleteWhereLocationServerCommandEvent;

public class WhereisEventListener implements Listener {

    // プレーヤー側
    @EventHandler (priority = EventPriority.MONITOR)
    public void CheckWhereLocationEvent (CheckWhereLocationEvent e){
        System.out.println(e.isCancelled());
        if (!e.isCancelled()){
            e.getExePlayer().sendMessage(e.getMessage());
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void CreateCompleteWhereLocationEvent (CreateCompleteWhereLocationEvent e){
        System.out.println(e.isCancelled());
        if (!e.isCancelled()){
            e.getExePlayer().sendMessage(e.getMessage());
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void UpdateCompleteWhereLocationEvent (UpdateCompleteWhereLocationEvent e){
        System.out.println(e.isCancelled());
        if (!e.isCancelled()){
            e.getExePlayer().sendMessage(e.getMessage());
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void DeleteCompleteWhereLocationEvent (DeleteCompleteWhereLocationEvent e){
        System.out.println(e.isCancelled());
        if (!e.isCancelled()){
            e.getExePlayer().sendMessage(e.getMessage());
        }
    }

    // サーバーコマンド側
    @EventHandler (priority = EventPriority.MONITOR)
    public void CheckWhereLocationEvent (CheckWhereLocationServerCommandEvent e){
        System.out.println(e.isCancelled());
        if (!e.isCancelled()){
            e.getCommandSender().sendMessage(e.getMessage());
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void CreateCompleteWhereLocationEvent (CreateCompleteWhereLocationServerCommandEvent e){
        System.out.println(e.isCancelled());
        if (!e.isCancelled()){
            e.getCommandSender().sendMessage(e.getMessage());
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void DeleteCompleteWhereLocationEvent (DeleteCompleteWhereLocationServerCommandEvent e){
        System.out.println(e.isCancelled());
        if (!e.isCancelled()){
            e.getCommandSender().sendMessage(e.getMessage());
        }
    }
}
