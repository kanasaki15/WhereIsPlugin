package xyz.n7mn.dev.whereisplugin.api.v2;

import org.bukkit.World;

import java.util.UUID;

public class WhereisData {
    private int id;
    private String name;
    private World world;
    private int startX;
    private int startZ;
    private int endX;
    private int endZ;
    private UUID createUser;
    private boolean active;

    public WhereisData(){

    }

    public WhereisData(int id, String name, World world, int startX, int startZ, int endX, int endZ, UUID createUser, boolean active){
        this.id = id;
        this.name = name;
        this.world = world;
        this.startX = startX;
        this.startZ = startZ;
        this.endX = endX;
        this.endZ = endZ;
        this.createUser = createUser;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartZ() {
        return startZ;
    }

    public void setStartZ(int startZ) {
        this.startZ = startZ;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndZ() {
        return endZ;
    }

    public void setEndZ(int endZ) {
        this.endZ = endZ;
    }

    public UUID getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UUID createUser) {
        this.createUser = createUser;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
