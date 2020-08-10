package xyz.n7mn.dev.whereisplugin.dataSystem;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.dataSystem.Result.DataResult;

import java.util.List;
import java.util.UUID;

@Deprecated
public interface DataSystemInterface {

    List<DataResult> getAllList();
    List<DataResult> getListBySearch(String word);
    List<DataResult> getLocationName(int X, int Z);
    List<DataResult> getLocationName(Location loc);
    List<DataResult> getLocationName(Player player);
    DataResult getLocationName(int ID);
    DataSystemResult addList(DataResult newData);
    DataSystemResult updateData(int ID, String NewName, UUID uuid);
    DataSystemResult updateData(String OldName, String NewName, UUID uuid);
    DataSystemResult deleteData(int ID, UUID uuid);
    DataSystemResult deleteData(String Name, UUID uuid);
    String getVersion();
    String getDataSystemName();
    boolean isConnect();
}
