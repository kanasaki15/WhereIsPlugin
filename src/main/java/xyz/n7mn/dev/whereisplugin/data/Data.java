package xyz.n7mn.dev.whereisplugin.data;

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
        if (sql != null){
            return sql.GetList(x , z);
        }

        return null;
    }
}
