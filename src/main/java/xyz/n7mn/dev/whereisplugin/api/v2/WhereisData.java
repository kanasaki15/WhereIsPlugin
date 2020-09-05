package xyz.n7mn.dev.whereisplugin.api.v2;

import org.bukkit.World;

import java.util.UUID;

public class WhereisData {

    private int ID;
    private UUID CreateUser;
    private String Name;
    private World World;
    private int StartX;
    private int EndX;
    private int StartZ;
    private int EndZ;
    private boolean Active;

    public WhereisData(int id, UUID createUser, String name, World world, int startX, int endX, int startZ, int endZ, boolean active) {
        this.ID = id;
        this.CreateUser = createUser;
        this.Name = name;
        this.World = world;
        this.StartX = startX;
        this.EndX = endX;
        this.StartZ = startZ;
        this.EndZ = endZ;
        this.Active = active;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public UUID getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(UUID createUser) {
        CreateUser = createUser;
    }

    public String getName(){
        return Name;
    }

    public void setName(String name){
        this.Name = name;
    }

    public World getWorld() {
        return World;
    }

    public void setWorld(org.bukkit.World world) {
        World = world;
    }

    public int getStartX() {
        return StartX;
    }

    public void setStartX(int startX) {
        StartX = startX;
    }

    public int getEndX() {
        return EndX;
    }

    public void setEndX(int endX) {
        EndX = endX;
    }

    public int getStartZ() {
        return StartZ;
    }

    public void setStartZ(int startZ) {
        StartZ = startZ;
    }

    public int getEndZ() {
        return EndZ;
    }

    public void setEndZ(int endZ) {
        EndZ = endZ;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }
}
