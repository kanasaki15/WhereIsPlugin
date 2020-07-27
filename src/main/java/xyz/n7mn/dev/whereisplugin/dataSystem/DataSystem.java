package xyz.n7mn.dev.whereisplugin.dataSystem;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.Result.DataResult;
import xyz.n7mn.dev.whereisplugin.function.DataSystems;

import java.util.List;
import java.util.UUID;

public class DataSystem {

    private DataSystemInterface data;
    private Player player;

    public DataSystem(WhereIsPlugin plugin, Player exePlayer){
        if (DataSystems.getUseDataSystem().equals("MySQL")){
            this.data = new MySQL(plugin);
            this.player = exePlayer;
        }

        if (DataSystems.getUseDataSystem().equals("File (JSON)")){
            this.data = new JSON(plugin);
            this.player = exePlayer;
        }
    }

    public DataSystem(WhereIsPlugin plugin){
        if (DataSystems.getUseDataSystem().equals("MySQL")){
            this.data = new MySQL(plugin);
        }

        if (DataSystems.getUseDataSystem().equals("File (JSON)")){
            this.data = new JSON(plugin);
        }
    }

    public List<DataResult> getData(int x, int z){
        return data.getLocationName(x, z);
    }

    public List<DataResult> getData(Player player){
        return data.getLocationName(player);
    }

    public List<DataResult> getData(Location loc){
        return data.getLocationName(loc);
    }

    public List<DataResult> getDataAllList(){
        return data.getAllList();
    }

    public DataResult[] getDataAllListByArray(){
        List<DataResult> data = this.getDataAllList();

        DataResult[] result = new DataResult[data.size()];
        for (int i = 0; i < result.length; i++){
            result[i] = data.get(i);
        }

        return result;
    }

    public DataSystemResult addData(String LocationName, int StartX, int EndX, int StartZ, int EndZ){
        DataResult newData = new DataResult();
        newData.ID = getNewID();
        newData.LocationName = LocationName;
        newData.StartX = StartX;
        newData.EndX = EndX;
        newData.StartZ = StartZ;
        newData.EndZ = EndZ;
        if (player != null){
            newData.UUID = player.getUniqueId();
        }else{
            newData.UUID = null;
        }

        return data.addList(newData);
    }

    public DataSystemResult updateData(int ID, String newName){

        UUID uuid = null;
        if (player != null){
            uuid = player.getUniqueId();
        }

        return data.updateData(ID, newName, uuid);
    }

    public DataSystemResult updateData(String oldName, String newName){

        UUID uuid = null;
        if (player != null){
            uuid = player.getUniqueId();
        }

        return data.updateData(oldName, newName, uuid);
    }

    public DataSystemResult delData(int ID){

        UUID uuid = null;
        if (player != null){
            uuid = player.getUniqueId();
        }

        return data.deleteData(ID, uuid);
    }

    public DataSystemResult delData(String LocationName){

        UUID uuid = null;
        if (player != null){
            uuid = player.getUniqueId();
        }

        return data.deleteData(LocationName, uuid);
    }

    private int getNewID(){
        List<DataResult> list = data.getAllList();

        return list.size() + 1;
    }
}
