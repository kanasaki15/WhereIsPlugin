package xyz.n7mn.dev.whereisplugin.dataSystem;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.Result.DataResult;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySQL implements DataSystemInterface {
    final private String version = "1.1";
    final private String systemName = "MySQL";

    private String MySQL_Server;
    private String MySQL_Username;
    private String MySQL_Password;
    private String MySQL_Database;

    List<DataResult> DataList;
    private boolean isConnect = false;
    Connection con = null;

    public MySQL(WhereIsPlugin p){
        MySQL_Server = p.getConfig().getString("mysqlServer");
        MySQL_Username = p.getConfig().getString("mysqlUser");
        MySQL_Password = p.getConfig().getString("mysqlPassWord");
        MySQL_Database = p.getConfig().getString("mysqlDatabase");

        if (MySQL_Server == null || (MySQL_Server != null && MySQL_Server.length() == 0)){

            isConnect = false;
        } else {
            try{
                con = DriverManager.getConnection("jdbc:mysql://" + MySQL_Server + "/" + MySQL_Database+"?allowPublicKeyRetrieval=true&useSSL=false", MySQL_Username, MySQL_Password);
                PreparedStatement statement1 = con.prepareStatement("SHOW TABLES LIKE ?;");
                statement1.setString(1, "WhereList");
                ResultSet query = statement1.executeQuery();
                if (!query.next()) {
                    PreparedStatement statement2 = con.prepareStatement("create table WhereList (ID int not null primary key, Name varchar(255), CreateUser varchar(255) , startX int , endX int , startZ int , endZ int , Active tinyint(1)) character set utf8mb4 collate utf8mb4_ja_0900_as_cs_ks; ");
                    statement2.execute();
                }
                isConnect = true;
            } catch (SQLException e) {
                isConnect = false;
                System.out.println(e.getMessage());
            } finally {
                try{
                    if (con != null){
                        con.close();
                    }
                } catch (SQLException e) {
                    isConnect = false;
                }
            }
        }
    }

    @Override
    public List<DataResult> getAllList() {

        DataList = new ArrayList<>();

        if (!isConnect){
            return DataList;
        }

        try{
            con = DriverManager.getConnection("jdbc:mysql://" + MySQL_Server + "/" + MySQL_Database+"?allowPublicKeyRetrieval=true&useSSL=false", MySQL_Username, MySQL_Password);
            PreparedStatement statement = con.prepareStatement("SELECT * FROM WhereList WHERE Active = 1 ORDER BY ID ASC;");
            ResultSet set = statement.executeQuery();
            while (set.next()){
                DataResult temp = new DataResult();
                temp.ID = set.getInt("ID");
                temp.LocationName = set.getString("Name");
                temp.UUID = UUID.fromString(set.getString("CreateUser"));
                temp.StartX = set.getInt("startX");
                temp.EndX = set.getInt("endX");
                temp.StartZ = set.getInt("startZ");
                temp.EndZ = set.getInt("endZ");
                temp.Active = set.getBoolean("Active");

                DataList.add(temp);
            }

        } catch (SQLException e) {
            //e.printStackTrace();
            isConnect = false;
            return new ArrayList<>();
        }

        return DataList;
    }

    @Override
    public List<DataResult> getListBySearch(String word) {
        DataList = new ArrayList<>();
        if (!isConnect){
            return DataList;
        }

        List<DataResult> list = getAllList();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).LocationName.equals(word)){
                DataList.add(list.get(i));
            }
        }

        return DataList;
    }

    @Override
    public List<DataResult> getLocationName(int X, int Z) {
        DataList = new ArrayList<>();
        if (!isConnect){
            return DataList;
        }
        DataList = getAllList();
        for (int i = 0; i < DataList.size(); i++){

            if (DataList.get(i).StartX <= X && X <= DataList.get(i).EndX && DataList.get(i).StartZ <= Z && Z <= DataList.get(i).EndZ){
                continue;
            }

            if (DataList.get(i).StartX >= X && X >= DataList.get(i).EndX && DataList.get(i).StartZ >= Z && Z >= DataList.get(i).EndZ) {
                continue;
            }

            DataList.remove(i);
        }

        return DataList;
    }

    @Override
    public List<DataResult> getLocationName(Location loc) {
        return getLocationName(loc.getBlockX(), loc.getBlockZ());
    }

    @Override
    public List<DataResult> getLocationName(Player player) {
        return getLocationName(player.getLocation());
    }

    @Override
    public DataResult getLocationName(int ID) {
        DataResult result = new DataResult();
        try{
            con = DriverManager.getConnection("jdbc:mysql://" + MySQL_Server + "/" + MySQL_Database+"?allowPublicKeyRetrieval=true&useSSL=false", MySQL_Username, MySQL_Password);
            PreparedStatement statement = con.prepareStatement("SELECT * FROM WhereList WHERE ID = ? AND Active = 1 ORDER BY ID ASC;");
            statement.setInt(1,ID);
            ResultSet set = statement.executeQuery();
            if (set.next()){
                result.ID = set.getInt("ID");
                result.LocationName = set.getString("Name");
                result.UUID = UUID.fromString(set.getString("CreateUser"));
                result.StartX = set.getInt("startX");
                result.EndX = set.getInt("endX");
                result.StartZ = set.getInt("startZ");
                result.EndZ = set.getInt("endZ");
                result.Active = set.getBoolean("Active");

                return result;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            isConnect = false;
        } finally {
            try{
                if (con != null){
                    con.close();
                }
            } catch (SQLException e){
                isConnect = false;
            }
        }
        return result;
    }

    @Override
    public DataSystemResult addList(DataResult newData) {
        try{
            con = DriverManager.getConnection("jdbc:mysql://" + MySQL_Server + "/" + MySQL_Database+"?allowPublicKeyRetrieval=true&useSSL=false", MySQL_Username, MySQL_Password);
            PreparedStatement statement = con.prepareStatement("INSERT INTO `WhereList` (`ID`, `Name`, `CreateUser`, `startX`, `endX`, `startZ`, `endZ`, `Active`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");

            statement.setInt(1, newData.ID);
            statement.setString(2, newData.LocationName);
            statement.setString(3, newData.UUID.toString());
            statement.setInt(4, newData.StartX);
            statement.setInt(5, newData.EndX);
            statement.setInt(6, newData.StartZ);
            statement.setInt(7, newData.EndZ);
            statement.setBoolean(8, true);

            statement.execute();
            con.close();
            isConnect = true;

            return new DataSystemResult("",false);

        } catch (SQLException e) {
            isConnect = false;
            return new DataSystemResult(e.getMessage(),true);
        } finally {
            try{
                if (con != null){
                    con.close();
                }
            } catch (SQLException e){
                isConnect = false;
                return new DataSystemResult(e.getMessage(),true);
            }
        }
    }

    @Override
    public DataSystemResult updateData(int ID, String NewName, UUID uuid) {
        try{
            con = DriverManager.getConnection("jdbc:mysql://" + MySQL_Server + "/" + MySQL_Database+"?allowPublicKeyRetrieval=true&useSSL=false", MySQL_Username, MySQL_Password);
            PreparedStatement statement1 = con.prepareStatement("SELECT ID FROM WhereList WHERE ID = ? AND CreateUser = ? AND Active = 1;");
            statement1.setInt(1, ID);
            statement1.setString(2, uuid.toString());

            ResultSet set = statement1.executeQuery();
            if (set.next()){
                PreparedStatement statement2 = con.prepareStatement("UPDATE `WhereList` SET `Name` = ? WHERE `WhereList`.`ID` = ? AND Active = 1;");
                statement2.setString(1, NewName);
                statement2.setInt(2, set.getInt("ID"));

                statement2.execute();

                return new DataSystemResult("",false);
            }

            return new DataSystemResult(new MessageList().getUpdateNotFoundErrorMessage(),true);
        } catch (SQLException e) {
            isConnect = false;
            return new DataSystemResult(e.getMessage(),true);
        } finally {
            try{
                if (con != null){
                    con.close();
                }
            } catch (SQLException e){
                isConnect = false;
                return new DataSystemResult(e.getMessage(),true);
            }
        }
    }

    @Override
    public DataSystemResult updateData(String OldName, String NewName, UUID uuid) {
        try{
            con = DriverManager.getConnection("jdbc:mysql://" + MySQL_Server + "/" + MySQL_Database+"?allowPublicKeyRetrieval=true&useSSL=false", MySQL_Username, MySQL_Password);
            PreparedStatement statement = con.prepareStatement("SELECT ID FROM WhereList WHERE Name = ? AND Active = 1;");

            statement.setString(1, OldName);
            ResultSet set = statement.executeQuery();

            if (set.next()){
                return updateData(set.getInt("ID"), NewName, uuid);
            }

            return new DataSystemResult(new MessageList().getUpdateNotFoundErrorMessage(),true);
        } catch (SQLException e) {
            isConnect = false;
            return new DataSystemResult(e.getMessage(),true);
        } finally {
            try{
                if (con != null){
                    con.close();
                }
            } catch (SQLException e){
                isConnect = false;
                return new DataSystemResult(e.getMessage(),true);
            }
        }
    }

    @Override
    public DataSystemResult deleteData(int ID, UUID uuid) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + MySQL_Server + "/" + MySQL_Database+"?allowPublicKeyRetrieval=true&useSSL=false", MySQL_Username, MySQL_Password);
            PreparedStatement statement1 = con.prepareStatement("SELECT ID FROM WhereList WHERE ID = ? AND Active = 1;");
            statement1.setInt(1, ID);
            ResultSet set1 = statement1.executeQuery();

            if (set1.next()){
                PreparedStatement statement2 = con.prepareStatement("UPDATE `WhereList` SET `Active` = ? WHERE `WhereList`.`ID` = ? AND Active = 1;");
                statement2.setBoolean(1, false);
                statement2.setInt(2, set1.getInt("ID"));
                statement2.execute();

                return new DataSystemResult("", false);
            }

            return new DataSystemResult(new MessageList().getUpdateNotFoundErrorMessage(),true);
        } catch (SQLException e){
            isConnect = false;
            return new DataSystemResult(e.getMessage(),true);
        } finally {
            try{
                if (con != null){
                    con.close();
                }
            } catch (SQLException e){
                isConnect = false;
                return new DataSystemResult(e.getMessage(),true);
            }
        }
    }

    @Override
    public DataSystemResult deleteData(String Name, UUID uuid) {
        try{
            con = DriverManager.getConnection("jdbc:mysql://" + MySQL_Server + "/" + MySQL_Database+"?allowPublicKeyRetrieval=true&useSSL=false", MySQL_Username, MySQL_Password);
            PreparedStatement statement1 = con.prepareStatement("SELECT ID FROM WhereList WHERE Name = ? AND CreateUser = ? AND Active = 1;");
            statement1.setString(1, Name);
            statement1.setString(2, uuid.toString());
            ResultSet set1 = statement1.executeQuery();

            if (set1.next()){
                return deleteData(set1.getInt("ID"), uuid);
            }

            return new DataSystemResult(new MessageList().getUpdateNotFoundErrorMessage(),true);
        } catch (SQLException e){
            isConnect = false;
            return new DataSystemResult(e.getMessage(),true);
        } finally {
            try{
                if (con != null){
                    con.close();
                }
            } catch (SQLException e){
                isConnect = false;
                return new DataSystemResult(e.getMessage(),true);
            }
        }
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getDataSystemName() {
        return systemName;
    }

    public boolean isConnect() {
        return isConnect;
    }
}
