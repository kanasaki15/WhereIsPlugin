package xyz.n7mn.dev.whereisplugin.dataSystem;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;

import java.util.List;
import java.util.UUID;

public class JSON implements DataSystemInterface {

    final private String version = "1.1";
    final private String systemName = "File (JSON)";

    private String FilePass;

    private boolean isConnect = false;

    public JSON(WhereIsPlugin plugin){

    }

    @Override
    public List<DataResult> getAllList() {
        return null;
    }

    @Override
    public List<DataResult> getListBySearch(String word) {
        return null;
    }

    @Override
    public DataResult getLocationName(int ID) {
        return null;
    }

    @Override
    public List<DataResult> getLocationName(int X, int Z) {
        return null;
    }

    @Override
    public List<DataResult> getLocationName(Location loc) {
        return null;
    }

    @Override
    public List<DataResult> getLocationName(Player player) {
        return null;
    }

    @Override
    public DataSystemResult addList(DataResult newData) {
        return null;
    }

    @Override
    public DataSystemResult updateData(int ID, String NewName, UUID uuid) {
        return null;
    }

    @Override
    public DataSystemResult updateData(String OldName, String NewName, UUID uuid) {
        return null;
    }

    @Override
    public DataSystemResult deleteData(int ID, UUID uuid) {
        return null;
    }

    @Override
    public DataSystemResult deleteData(String Name, UUID uuid) {
        return null;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getDataSystemName() {
        return systemName;
    }

    @Override
    public boolean isConnect() {
        return isConnect;
    }
}
