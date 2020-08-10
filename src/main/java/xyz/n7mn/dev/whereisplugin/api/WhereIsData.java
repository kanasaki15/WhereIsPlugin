package xyz.n7mn.dev.whereisplugin.api;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class WhereIsData {
    private final Plugin plugin = Bukkit.getPluginManager().getPlugin("WhereIsPlugin");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

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
        }
    }

    public List<WhereData> getDataListByALL(){
        List<WhereData> list = null;
        if (isMySQL && con != null){
            list = new ArrayList<>();
            try {
                ResultSet set = con.prepareStatement("SELECT * FROM WhereList;").executeQuery();
                boolean b = set.next();

                while(b){
                    list.add(new WhereData(set.getInt("ID"), set.getString("Name"), UUID.fromString(set.getString("CreateUser")), set.getInt("startX"), set.getInt("endX"), set.getInt("startZ"), set.getInt("endZ"), set.getBoolean("Active")));
                    b = set.next();
                }
            } catch (SQLException e) {
                ErrorMessage = e.getMessage();
                MySQLConnectClose();
                isMySQL = false;
                return new ArrayList<>();
            }
        } else {
            list = gson.fromJson(new FileSystem().Read(), new TypeToken<Collection<WhereData>>(){}.getType());
        }
        return list;
    }

    public List<WhereData> getDataListByUser(UUID uuid){
        List<WhereData> list = null;
        if (isMySQL && con != null){
            try {
                PreparedStatement statement = con.prepareStatement("SELECT * FROM WhereList WHERE CreateUser = ?;");
                statement.setString(1, uuid.toString());
                ResultSet set = statement.executeQuery();
                boolean b = set.next();

                while(b){
                    list.add(new WhereData(set.getInt("ID"), set.getString("Name"), UUID.fromString(set.getString("CreateUser")), set.getInt("startX"), set.getInt("endX"), set.getInt("startZ"), set.getInt("endZ"), set.getBoolean("Active")));
                    b = set.next();
                }
            } catch (SQLException throwable) {
                ErrorMessage = throwable.getMessage();
                MySQLConnectClose();
                isMySQL = false;
                return new ArrayList<>();
            }
        } else {
            List<WhereData> templist = gson.fromJson(new FileSystem().Read(), new TypeToken<Collection<WhereData>>(){}.getType());

            int i = 0;
            for (WhereData where : templist){
                if (where.getUUID().equals(uuid)){
                    continue;
                }
                templist.remove(i);
            }

            list = templist;
        }

        return list;
    }

    public WhereData getWhereData(int id){
        if (isMySQL && con != null){
            try {
                PreparedStatement statement = con.prepareStatement("SELECT * FROM WhereList WHERE ID = ?;");
                statement.setInt(1, id);
                ResultSet set = statement.executeQuery();
                if (set.next()){
                    return new WhereData(set.getInt("ID"), set.getString("Name"), UUID.fromString(set.getString("CreateUser")), set.getInt("startX"), set.getInt("endX"), set.getInt("startZ"), set.getInt("endZ"), set.getBoolean("Active"));
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
                if (where.getID() == id){
                    return where;
                }
            }
            return new WhereData();
        }
    }

    @Deprecated
    public WhereData getWhereData(String name){
        if (isMySQL && con != null){
            try {
                PreparedStatement statement = con.prepareStatement("SELECT * FROM WhereList WHERE Name = ?;");
                statement.setString(1, name);
                ResultSet set = statement.executeQuery();
                if (set.next()){
                    return new WhereData(set.getInt("ID"), set.getString("Name"), UUID.fromString(set.getString("CreateUser")), set.getInt("startX"), set.getInt("endX"), set.getInt("startZ"), set.getInt("endZ"), set.getBoolean("Active"));
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
        if (isMySQL && con != null){
            try {
                PreparedStatement statement = con.prepareStatement("SELECT * FROM WhereList WHERE Name = ?;");
                statement.setString(1, name);
                ResultSet set = statement.executeQuery();
                if (set.next()){
                    return set.getInt("ID");
                }
                return -1;
            } catch (SQLException throwable) {
                ErrorMessage = throwable.getMessage();
                MySQLConnectClose();
                isMySQL = false;
                return -1;
            }
        } else {
            List<WhereData> templist = gson.fromJson(new FileSystem().Read(), new TypeToken<Collection<WhereData>>(){}.getType());

            for (WhereData where : templist){
                if (where.getLocationName().equals(name)){
                    return where.getID();
                }
            }
            return -1;
        }
    }

    public int getWhereDataID(String name, UUID uuid){
        if (isMySQL && con != null){
            try {
                PreparedStatement statement = con.prepareStatement("SELECT * FROM WhereList WHERE Name = ? AND CreateUser = ?;");
                statement.setString(1, name);
                statement.setString(2, uuid.toString());
                ResultSet set = statement.executeQuery();
                if (set.next()){
                    return set.getInt("ID");
                }
                return -1;
            } catch (SQLException throwable) {
                ErrorMessage = throwable.getMessage();
                MySQLConnectClose();
                isMySQL = false;
                return -1;
            }
        } else {
            List<WhereData> templist = gson.fromJson(new FileSystem().Read(), new TypeToken<Collection<WhereData>>(){}.getType());

            for (WhereData where : templist){
                if (where.getLocationName().equals(name)){
                    return where.getID();
                }
            }
            return -1;
        }
    }

    public boolean addWhereData(WhereData data){
        if (isMySQL && con != null){
            try {
                PreparedStatement statement = con.prepareStatement("INSERT INTO `WhereList` (`ID`, `Name`, `CreateUser`, `startX`, `endX`, `startZ`, `endZ`, `Active`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
                statement.setInt(1, NewID());
                statement.setString(2, data.getLocationName());
                statement.setString(3, data.getUUID().toString());
                statement.setInt(4, data.getStartX());
                statement.setInt(5, data.getEndX());
                statement.setInt(6, data.getStartZ());
                statement.setInt(7, data.getEndZ());
                statement.setBoolean(8, data.isActive());

                return statement.execute();
            } catch (SQLException throwable) {
                ErrorMessage = throwable.getMessage();
                MySQLConnectClose();
                isMySQL = false;
                return false;
            }
        } else {
            List<WhereData> templist = gson.fromJson(new FileSystem().Read(), new TypeToken<Collection<WhereData>>(){}.getType());

            data.setID(NewID());
            templist.add(data);
            FileSystem fileSystem = new FileSystem();
            boolean b = fileSystem.Write(gson.toJson(templist));
            if (!b){
                ErrorMessage = fileSystem.getErrorMessage();
            }
            return b;
        }
    }

    public boolean addWhereData(String name, UUID CreateUser, int StartX, int EndX, int StartZ, int EndZ, boolean Active){
        return addWhereData(new WhereData(0, name, CreateUser, StartX, EndX, StartZ, EndZ, Active));
    }

    public boolean updateWhereData(WhereData data){
        if (isMySQL && con != null){
            try {
                PreparedStatement statement = con.prepareStatement("UPDATE `WhereList` SET `Name` = ? , `CreateUser` = ? , `startX` = ? , `endX` = ? , `startZ` = ? , `endZ` = ? , `Active` = ?  WHERE `WhereList`.`ID` = ? ");
                statement.setString(1, data.getLocationName());
                statement.setString(2, data.getUUID().toString());
                statement.setInt(3, data.getStartX());
                statement.setInt(4, data.getEndX());
                statement.setInt(5, data.getStartZ());
                statement.setInt(6, data.getEndZ());
                statement.setBoolean(7, data.isActive());
                statement.setInt(8, data.getID());

                return statement.execute();
            } catch (SQLException throwable) {
                ErrorMessage = throwable.getMessage();
                MySQLConnectClose();
                isMySQL = false;
                return false;
            }
        } else {
            List<WhereData> templist = gson.fromJson(new FileSystem().Read(), new TypeToken<Collection<WhereData>>(){}.getType());

            int i = 0;
            for (WhereData temp : templist){
                if (temp.getID() == data.getID()){
                    templist.get(i).setLocationName(data.getLocationName());
                    templist.get(i).setUUID(data.getUUID());
                    templist.get(i).setStartX(data.getStartX());
                    templist.get(i).setEndX(data.getEndX());
                    templist.get(i).setStartZ(data.getStartZ());
                    templist.get(i).setEndZ(data.getEndZ());
                    break;
                }
                i++;
            }
            FileSystem system = new FileSystem();
            boolean b = system.Write(gson.toJson(templist));

            if (!b){
                ErrorMessage = system.getErrorMessage();
            }

            return b;
        }
    }

    public boolean updateWhereData(int id, String name, UUID CreateUser, int StartX, int EndX, int StartZ, int EndZ, boolean Active){
        return updateWhereData(new WhereData(id, name, CreateUser, StartX, EndX, StartZ, EndZ, Active));
    }

    public boolean deleteWhereData(int id){
        WhereData whereData = getWhereData(id);
        whereData.setActive(false);
        return updateWhereData(whereData);
    }

    @Deprecated
    public boolean deleteWhereData(String name){
        WhereData whereData = getWhereData(name);
        whereData.setActive(false);
        return updateWhereData(whereData);
    }

    public boolean deleteWhereData(String name, UUID uuid){
        WhereData whereData = getWhereData(getWhereDataID(name, uuid));
        whereData.setActive(false);
        return updateWhereData(whereData);
    }

    public String getUseSystem(){
        if (isMySQL){
            return "MySQL";
        } else {
            return "File";
        }
    }

    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    public void Close(){
        if (isMySQL){
            MySQLConnectClose();
        }
    }

    public String getErrorMessage(){

        return ErrorMessage;
    }

    private void MySQLConnect(){
        if (MySQLServer != null && MySQLUsername != null && MySQLPassword != null && MySQLDatabase != null && MySQLServer.length() != 0 && MySQLUsername.length() != 0 && MySQLPassword.length() != 0 && MySQLDatabase.length() != 0){
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase + MySQLOption, MySQLUsername, MySQLPassword);
                isMySQL = true;
            } catch (SQLException throwable) {
                ErrorMessage = throwable.getMessage();
                con = null;
                isMySQL = false;
            }
        }
    }

    private void MySQLInit(){
        if (con != null){
            try {
                if (!con.prepareStatement("SHOW TABLES LIKE 'WhereList';").executeQuery().next()){
                    con.prepareStatement("CREATE TABLE `WhereList` (\n" +
                            "  `ID` int NOT NULL,\n" +
                            "  `Name` varchar(255) COLLATE utf8mb4_ja_0900_as_cs_ks DEFAULT NULL,\n" +
                            "  `CreateUser` varchar(255) COLLATE utf8mb4_ja_0900_as_cs_ks DEFAULT NULL,\n" +
                            "  `startX` int DEFAULT NULL,\n" +
                            "  `endX` int DEFAULT NULL,\n" +
                            "  `startZ` int DEFAULT NULL,\n" +
                            "  `endZ` int DEFAULT NULL,\n" +
                            "  `Active` tinyint(1) DEFAULT NULL\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_ja_0900_as_cs_ks;").execute();
                    con.prepareStatement("ALTER TABLE `WhereList` ADD PRIMARY KEY (`ID`);").execute();
                }
                isMySQL = true;
            } catch (SQLException throwable) {
                ErrorMessage = throwable.getMessage();
                MySQLConnectClose();
                isMySQL = false;
            }
        }
    }

    private void MySQLConnectClose(){
        try {
            if (con != null){
                con.close();
                isMySQL = true;
            }
        } catch (SQLException throwable) {
            ErrorMessage = throwable.getMessage();
            isMySQL = false;
        }

        con = null;
    }

    private int NewID(){
        if (isMySQL && con != null){
            try {
                PreparedStatement statement = con.prepareStatement("SELECT count(*) FROM WhereList;");
                ResultSet set = statement.executeQuery();
                if (set.next()){
                    return set.getInt("count(*)") + 1;
                }
                return -1;
            } catch (SQLException throwable) {
                ErrorMessage = throwable.getMessage();
                MySQLConnectClose();
                isMySQL = false;
                return -1;
            }
        } else {
            List<WhereData> templist = gson.fromJson(new FileSystem().Read(), new TypeToken<Collection<WhereData>>(){}.getType());
            return templist.size() + 1;
        }
    }
}
