package xyz.n7mn.dev.whereisplugin.dataSystem;

import java.util.UUID;

public class DataResult {
    public int ID;
    public String LocationName;
    public UUID UUID;
    public int StartX;
    public int EndX;
    public int StartZ;
    public int EndZ;
    public boolean Active;

    public DataResult(int id, String locationname, UUID uuid,int startX, int endX, int startZ, int endZ) {
        ID = id;
        LocationName = locationname;
        UUID = uuid;
        StartX = startX;
        EndX = endX;
        StartZ = startZ;
        EndZ = endZ;
        Active = true;
    }

    public DataResult(int id, String locationname, UUID uuid,int startX, int endX, int startZ, int endZ, boolean active){
        ID = id;
        LocationName = locationname;
        UUID = uuid;
        StartX = startX;
        EndX = endX;
        StartZ = startZ;
        EndZ = endZ;
        Active = active;
    }

    public DataResult(){

    }
}
