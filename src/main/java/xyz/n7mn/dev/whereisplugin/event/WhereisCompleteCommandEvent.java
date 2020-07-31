package xyz.n7mn.dev.whereisplugin.event;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;

public class WhereisCompleteCommandEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();

    private boolean cancel = false;
    private CommandSender commandSender;
    private String Message;

    private int StartX = Integer.MIN_VALUE;
    private int StartZ = Integer.MIN_VALUE;
    private int EndX = Integer.MIN_VALUE;
    private int EndZ = Integer.MIN_VALUE;
    private int X = Integer.MIN_VALUE;
    private int Z = Integer.MIN_VALUE;

    private boolean isErrorFlag = false;

    public WhereisCompleteCommandEvent(CommandSender sender, String Message, int StartX, int EndX, int StartZ, int EndZ, boolean isError){
        this.commandSender = sender;
        this.Message = Message;
        this.StartX = StartX;
        this.EndX = EndX;
        this.StartZ = StartZ;
        this.EndZ = EndZ;
        this.isErrorFlag = isError;
    }

    public WhereisCompleteCommandEvent(CommandSender sender, String Message, int X, int Z, boolean isError){
        this.commandSender = sender;
        this.Message = Message;
        this.X = X;
        this.Z = Z;
        this.isErrorFlag = isError;
    }

    public WhereisCompleteCommandEvent(CommandSender sender, String Message, boolean isError){
        this.commandSender = sender;
        this.Message = Message;
        this.isErrorFlag = isError;
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

    public List<Integer> getCoordinateByList(){

        List<Integer> list = new ArrayList<>();

        if (StartX != Integer.MIN_VALUE && EndX != Integer.MIN_VALUE && StartZ != Integer.MIN_VALUE && EndZ != Integer.MIN_VALUE){
            list.add(StartX);
            list.add(EndX);
            list.add(StartZ);
            list.add(EndZ);

            return list;
        }

        if (X != Integer.MIN_VALUE && Z != Integer.MIN_VALUE){
            list.add(X);
            list.add(Z);

            return list;
        }

        throw new NullPointerException("Set Parameter Error");
    }

    public int[] getCoordinateByArray(){

        if (StartX != Integer.MIN_VALUE && EndX != Integer.MIN_VALUE && StartZ != Integer.MIN_VALUE && EndZ != Integer.MIN_VALUE){
            return new int[]{StartX, EndX, StartZ, EndZ};
        }

        if (X != Integer.MIN_VALUE && Z != Integer.MIN_VALUE){
            return new int[]{X, Z};
        }

        throw new NullPointerException("Set Parameter Error");
    }

    public int getStartX() {

        if (StartX == Integer.MIN_VALUE){
            throw new NullPointerException("Not Set Parameter");
        }

        return StartX;
    }

    public int getEndX() {

        if (EndX == Integer.MIN_VALUE){
            throw new NullPointerException("Not Set Parameter");
        }

        return EndX;
    }

    public int getStartZ() {

        if (StartZ == Integer.MIN_VALUE){
            throw new NullPointerException("Not Set Parameter");
        }

        return StartZ;
    }

    public int getEndZ() {

        if (EndZ == Integer.MIN_VALUE){
            throw new NullPointerException("Not Set Parameter");
        }

        return EndZ;
    }

    public int getX() {

        if (X == Integer.MIN_VALUE){
            throw new NullPointerException("Not Set Parameter");
        }

        return X;
    }

    public int getZ() {

        if (Z == Integer.MIN_VALUE){
            throw new NullPointerException("Not Set Parameter");
        }

        return Z;
    }

    public boolean isErrorFlag() {
        return isErrorFlag;
    }
}
