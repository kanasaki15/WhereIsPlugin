package xyz.n7mn.dev.whereisplugin.api;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class WhereData {
    private int ID;
    @SerializedName("Name")
    private String LocationName;
    private UUID UUID;
    private String WorldName;
    private int StartX;
    private int EndX;
    private int StartZ;
    private int EndZ;
    private boolean Active;

    WhereData(){

    }

    WhereData(int ID, String LocationName, UUID uuid, String WorldName, int StartX, int EndX, int StartZ, int EndZ, boolean Active){
        this.ID = ID;
        this.LocationName = LocationName;
        this.WorldName = WorldName;
        this.UUID = uuid;
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
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public String getWorldName(){
        return WorldName;
    }

    public void setWorldName(String worldName){
        this.WorldName = worldName;
    }

    public UUID getUUID() {
        return UUID;
    }

    public void setUUID(UUID UUID) {
        this.UUID = UUID;
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
