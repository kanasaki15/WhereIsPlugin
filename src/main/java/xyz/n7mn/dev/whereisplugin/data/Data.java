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
        p.reloadConfig();

        if (p.getConfig().getString("mysqlServer").length() > 0){
            sql = new DataSQL(p);
        }
        json = new DataJSON(p);
    }

    public Data(){

    }

    public Data[] getDataList(int x , int z){
        Data[] temp = null;
        if (sql != null && sql.NewConnect()){
            temp = sql.GetList(x , z);
        }
        if (json != null && json.NewConnect()){
            temp = json.GetList(x , z);
        }

        if (temp == null){
            temp = new Data[0];
        }

        return temp;
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

    public Data[] getDataAllList(){
        Data[] temp = null;
        if (sql != null && sql.NewConnect()){
            temp = sql.GetListAll();
        }

        if (json != null && json.NewConnect()){
            temp = json.GetListAll();
        }

        if (temp == null){
            temp = new Data[0];
        }

        return temp;
    }

    public String getMode(){
        if (sql != null && sql.NewConnect()){
            return "MySQL";
        }
        if (json != null && json.NewConnect()){
            return "File (JSON)";
        }

        return "Unknown";
    }
}
