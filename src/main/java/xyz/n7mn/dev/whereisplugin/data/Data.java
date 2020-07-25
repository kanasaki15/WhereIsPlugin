package xyz.n7mn.dev.whereisplugin.data;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Data {
    public String Name;
    public int startX;
    public int endX;
    public int startZ;
    public int endZ;
    private DataSQL sql = null;
    private DataJSON json = null;

    public Data(Plugin p){
        if (p.getConfig().getString("mysqlServer").length() > 0){
            sql = new DataSQL(p);
        }else{
            json = new DataJSON(p);
        }
    }

    public Data(){

    }

    public Data[] getDataList(int x , int z){
        if (sql != null && sql.NewConnect()){
            return sql.GetList(x , z);
        }
        if (json != null && json.NewConnect()){
            return json.GetList(x , z);
        }

        return null;
    }

    public Data[] getDataList(Player player) {
        return getDataList(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
    }

    public boolean setName(int startX, int endX, int startZ, int endZ, String name){

        if (startX == endX && startZ == endZ){
            return false;
        }

        if (sql != null && sql.NewConnect()){
            return sql.SetName(startX, endX, startZ, endZ, name);
        }

        if (json != null && json.NewConnect()){
            return json.SetName(startX, endX, startZ, endZ, name);
        }

        return false;
    }

    public boolean UpdateName(String OldName , String NewName){

        if (sql != null && sql.NewConnect()){
            return sql.UpdateName(OldName, NewName);
        }

        if (json != null && json.NewConnect()){
            return json.UpdateName(OldName, NewName);
        }
        return false;
    }

    public boolean DelName(String Name){
        if (sql != null && sql.NewConnect()){
            return sql.DelName(Name);
        }

        if (json != null && json.NewConnect()){
            return json.DelName(Name);
        }
        return false;
    }
}
