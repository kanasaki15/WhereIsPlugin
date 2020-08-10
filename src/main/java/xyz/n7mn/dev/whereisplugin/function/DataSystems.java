package xyz.n7mn.dev.whereisplugin.function;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.JSON;
import xyz.n7mn.dev.whereisplugin.dataSystem.MySQL;

@Deprecated
public class DataSystems {
    private DataSystems(){

    }

    public static String getUseDataSystem(){
        WhereIsPlugin plugin = (WhereIsPlugin) Bukkit.getPluginManager().getPlugin("WhereIsPlugin");

        MySQL mysql = new MySQL(plugin);
        JSON json = new JSON(plugin);
        if (mysql.isConnect()){
            return mysql.getDataSystemName();
        }

        if (json.isConnect()){
            return json.getDataSystemName();
        }

        return "Unknown";
    }
}
