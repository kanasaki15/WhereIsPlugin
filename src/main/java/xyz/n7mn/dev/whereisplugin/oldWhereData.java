package xyz.n7mn.dev.whereisplugin;

import java.util.UUID;

public class oldWhereData {
    private int ID;
    private String Name;
    private UUID UUID;
    private String WorldName;
    private int StartX;
    private int EndX;
    private int StartZ;
    private int EndZ;
    private boolean Active;

    public oldWhereData(int id, String name, UUID uuid, String worldName, int startX, int endX, int startZ, int endZ, boolean active){
        this.ID = id;
        this.Name = name;
        this.UUID = uuid;
        this.WorldName = worldName;
        this.StartX = startX;
        this.EndX = endX;
        this.StartZ = startZ;
        this.EndZ = endZ;
        this.Active = active;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public UUID getUUID() {
        return UUID;
    }

    public String getWorldName() {
        return WorldName;
    }

    public int getStartX() {
        return StartX;
    }

    public int getEndX() {
        return EndX;
    }

    public int getStartZ() {
        return StartZ;
    }

    public int getEndZ() {
        return EndZ;
    }

    public boolean isActive() {
        return Active;
    }
}
