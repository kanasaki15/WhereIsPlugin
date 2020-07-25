package xyz.n7mn.dev.whereisplugin.data.Result;

public class JSONData {
    int ID;
    String Name;
    int StartX;
    int EndX;
    int StartZ;
    int EndZ;
    boolean Active;

    public JSONData(int ID, String Name, int StartX, int EndX, int StartZ, int EndZ, boolean Active){
        this.ID = ID;
        this.Name = Name;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
