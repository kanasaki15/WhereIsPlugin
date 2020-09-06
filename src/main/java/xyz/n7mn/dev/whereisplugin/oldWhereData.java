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
