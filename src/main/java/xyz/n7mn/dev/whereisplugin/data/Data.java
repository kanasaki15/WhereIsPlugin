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

        return null;
    }

    public Data[] getDataList(Player player) {
        return getDataList(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
    }

    public String setName(int startX, int endX, int startZ, int endZ, String name){
        if (sql != null && sql.NewConnect()){
            if (sql.SetName(startX, endX, startZ, endZ, name)){
                return "Success";
            }else{
                return "Error";
            }
        }else{
            return "implemented";
        }
    }
}
