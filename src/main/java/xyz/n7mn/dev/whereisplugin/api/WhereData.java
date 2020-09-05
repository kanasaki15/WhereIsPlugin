package xyz.n7mn.dev.whereisplugin.api;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

@Deprecated
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
    @Deprecated
    WhereData(){

    }
    @Deprecated
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
    @Deprecated
    public int getID() {
        return ID;
    }
    @Deprecated
    public void setID(int ID) {
        this.ID = ID;
    }
    @Deprecated
    public String getLocationName() {
        return LocationName;
    }
    @Deprecated
    public void setLocationName(String locationName) {
        LocationName = locationName;
    }
    @Deprecated
    public String getWorldName(){
        return WorldName;
    }
    @Deprecated
    public void setWorldName(String worldName){
        this.WorldName = worldName;
    }
    @Deprecated
    public UUID getUUID() {
        return UUID;
    }
    @Deprecated
    public void setUUID(UUID UUID) {
        this.UUID = UUID;
    }
    @Deprecated
    public int getStartX() {
        return StartX;
    }
    @Deprecated
    public void setStartX(int startX) {
        StartX = startX;
    }
    @Deprecated
    public int getEndX() {
        return EndX;
    }
    @Deprecated
    public void setEndX(int endX) {
        EndX = endX;
    }
    @Deprecated
    public int getStartZ() {
        return StartZ;
    }
    @Deprecated
    public void setStartZ(int startZ) {
        StartZ = startZ;
    }
    @Deprecated
    public int getEndZ() {
        return EndZ;
    }
    @Deprecated
    public void setEndZ(int endZ) {
        EndZ = endZ;
    }
    @Deprecated
    public boolean isActive() {
        return Active;
    }
    @Deprecated
    public void setActive(boolean active) {
        Active = active;
    }
}