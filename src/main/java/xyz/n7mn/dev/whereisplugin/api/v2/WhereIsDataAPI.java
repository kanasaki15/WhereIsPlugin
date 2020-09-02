package xyz.n7mn.dev.whereisplugin.api.v2;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

public class WhereIsDataAPI {

    private Plugin plugin;
    private Connection con;

    public WhereIsDataAPI(){
        plugin = null;
        con = new ConnectSystem().getMySQLConnect((String) ConfigAPI.getConfig("MySQLServer").getValue(), (String) ConfigAPI.getConfig("MySQLUsername").getValue(), (String) ConfigAPI.getConfig("MySQLPassword").getValue(), (String) ConfigAPI.getConfig("MySQLDatabase").getValue(), (String) ConfigAPI.getConfig("").getValue());
    }

    public WhereIsDataAPI(Plugin plugin){
        this.plugin = plugin;
        con = new ConnectSystem().getMySQLConnect((String) ConfigAPI.getConfig("MySQLServer").getValue(), (String) ConfigAPI.getConfig("MySQLUsername").getValue(), (String) ConfigAPI.getConfig("MySQLPassword").getValue(), (String) ConfigAPI.getConfig("MySQLDatabase").getValue(), (String) ConfigAPI.getConfig("").getValue());
    }

    public WhereisData getData(int id){
        if (plugin == null){
            throw new NullPointerException();
        }

        try {
            boolean isJson = false;
            if (new ConnectSystem().isMySQLConnect(con)){
                isJson = true;
            }

            if (!isJson){
                PreparedStatement statement = con.prepareStatement("SELECT * FROM WhereisList WHERE WhereID = ?");
                statement.setInt(1, id);
                ResultSet set = statement.executeQuery();
                if (set.next()){
                    WhereisData data = new WhereisData(
                            set.getInt("ID"),
                            set.getString("WhereName"),
                            Bukkit.getServer().getWorld(set.getString("WorldName")),
                            set.getInt("StartX"),
                            set.getInt("StartZ"),
                            set.getInt("EndX"),
                            set.getInt("EndZ"),
                            UUID.fromString(set.getString("CreateUser")),
                            set.getBoolean("Active")
                    );

                    return data;
                }
            }

            return new WhereisData();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<WhereisData> getDataList(){
        if (plugin == null){
            throw new NullPointerException();
        }



        return null;
    }
}
