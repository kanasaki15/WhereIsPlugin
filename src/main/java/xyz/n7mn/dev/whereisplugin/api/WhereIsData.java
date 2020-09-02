package xyz.n7mn.dev.whereisplugin.api;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.*;
import java.util.*;

@Deprecated
public class WhereIsData {
    private final Plugin plugin = Bukkit.getPluginManager().getPlugin("WhereIsPlugin");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String version = "1.2";

    private boolean isMySQL = false;

    private String MySQLServer;
    private String MySQLUsername;
    private String MySQLPassword;
    private String MySQLDatabase;
    private String MySQLOption;

    private Connection con = null;
    private String ErrorMessage = null;

    public WhereIsData(){
        this.MySQLServer   = plugin.getConfig().getString("mysqlServer");
        this.MySQLUsername = plugin.getConfig().getString("mysqlUser");
        this.MySQLPassword = plugin.getConfig().getString("mysqlPassWord");
        this.MySQLDatabase = plugin.getConfig().getString("mysqlDatabase");
        this.MySQLOption   = "?allowPublicKeyRetrieval=true&useSSL=false";

        MySQLConnect();
        if (isMySQL){
            MySQLInit();
        } else {
            FileInit();
        }
    }

    public WhereIsData(String MySQLServerName, String Username, String Password, String Database, String ConnectOption){
        this.MySQLServer = MySQLServerName;
        this.MySQLUsername = Username;
        this.MySQLPassword = Password;
        this.MySQLDatabase = Database;
        this.MySQLOption = ConnectOption;

        MySQLConnect();
        if (isMySQL){
            MySQLInit();
        }else{
            FileInit();
        }
    }

    @Deprecated
    public List<WhereData> getDataListByALL(){
        return Collections.EMPTY_LIST;
    }

    @Deprecated
    public List<WhereData> getDataListByUser(UUID uuid){
        return Collections.EMPTY_LIST;
    }

    @Deprecated
    public List<WhereData> getWhereListByLocation(Location loc){
        return Collections.EMPTY_LIST;
    }

    @Deprecated
    public WhereData getWhereData(int id){
        return new WhereData();
    }

    @Deprecated
    public WhereData getWhereData(String name){
        if (isMySQL && con != null){
            try {
                PreparedStatement statement = con.prepareStatement("SELECT * FROM WhereList WHERE Name = ?;");
                statement.setString(1, name);
                ResultSet set = statement.executeQuery();
                if (set.next()){
                    if (set.getString("CreateUser") != null && set.getString("CreateUser").length() != 0){
                        return new WhereData(set.getInt("ID"), set.getString("Name"), UUID.fromString(set.getString("CreateUser")), set.getString("WorldName"), set.getInt("startX"), set.getInt("endX"), set.getInt("startZ"), set.getInt("endZ"), set.getBoolean("Active"));
                    } else {
                        return new WhereData(set.getInt("ID"), set.getString("Name"), null, set.getString("WorldName"), set.getInt("startX"), set.getInt("endX"), set.getInt("startZ"), set.getInt("endZ"), set.getBoolean("Active"));
                    }

                }
                return new WhereData();
            } catch (SQLException throwable) {
                ErrorMessage = throwable.getMessage();
                MySQLConnectClose();
                isMySQL = false;
                return new WhereData();
            }
        } else {
            List<WhereData> templist = gson.fromJson(new FileSystem().Read(), new TypeToken<Collection<WhereData>>(){}.getType());

            for (WhereData where : templist){
                if (where.getLocationName().equals(name)){
                    return where;
                }
            }
            return new WhereData();
        }
    }

    @Deprecated
    public int getWhereDataID(String name){
        return -1;
    }

    @Deprecated
    public int getWhereDataID(String name, UUID uuid){
        return -1;
    }

    @Deprecated
    public boolean addWhereData(WhereData data){
        return false;
    }

    @Deprecated
    public boolean addWhereData(String name, UUID CreateUser, String WorldName, int StartX, int EndX, int StartZ, int EndZ, boolean Active){
        return addWhereData(new WhereData(0, name, CreateUser, WorldName, StartX, EndX, StartZ, EndZ, Active));
    }

    @Deprecated
    public boolean updateWhereData(WhereData data){
        return false;
    }

    @Deprecated
    public boolean updateWhereData(int id, String name, UUID CreateUser, String WorldName, int StartX, int EndX, int StartZ, int EndZ, boolean Active){
        return updateWhereData(new WhereData(id, name, CreateUser, WorldName, StartX, EndX, StartZ, EndZ, Active));
    }

    @Deprecated
    public boolean deleteWhereData(int id){
        WhereData whereData = this.getWhereData(id);
        whereData.setActive(false);

        return updateWhereData(whereData);
    }

    @Deprecated
    public boolean deleteWhereData(String name){
        WhereData whereData = this.getWhereData(name);
        whereData.setActive(false);
        return updateWhereData(whereData);
    }

    @Deprecated
    public boolean deleteWhereData(String name, UUID uuid){
        WhereData whereData = this.getWhereData(getWhereDataID(name, uuid));
        whereData.setActive(false);
        return updateWhereData(whereData);
    }

    @Deprecated
    public String getUseSystem(){
        return "Unknown";
    }

    @Deprecated
    public String getVersion(){
        return "1.3-final";
    }

    @Deprecated
    public void Close(){
        if (isMySQL){
            MySQLConnectClose();
        }
    }

    @Deprecated
    public String getErrorMessage(){

        return "";
    }

    @Deprecated
    private void MySQLConnect(){
        isMySQL = false;
    }

    @Deprecated
    private void MySQLInit(){

    }

    @Deprecated
    private void MySQLConnectClose(){
        con = null;
    }

    @Deprecated
    private void MySQLAutoImport(){

    }

    @Deprecated
    private void FileInit(){

    }

    @Deprecated
    private int NewID(){
        return -1;
    }

}