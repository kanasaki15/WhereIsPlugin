package xyz.n7mn.dev.whereisplugin.dataSystem.Result;

import java.util.UUID;

public class JsonResult {
    private int ID;
    private String Name;
    private UUID uuid;
    private int StartX;
    private int EndX;
    private int StartZ;
    private int EndZ;
    private boolean Active;

    public JsonResult (int ID, String Name, UUID uuid, int StartX, int EndX, int StartZ, int EndZ, boolean Active){
        this.ID = ID;
        this.Name = Name;
        this.uuid = uuid;
        this.StartX = StartX;
        this.EndX = EndX;
        this.StartZ = StartZ;
        this.EndZ = EndZ;
        this.Active = Active;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLocationName() {
        return Name;
    }

    public void setLocationName(String locationName) {
        Name = locationName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public java.util.UUID getUuid() {
        return uuid;
    }

    public void setUuid(java.util.UUID uuid) {
        this.uuid = uuid;
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
