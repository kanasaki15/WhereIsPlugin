package xyz.n7mn.dev.whereisplugin.data;

import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class DataSQL implements DataInterface {
    private Plugin p;
    private String MySQLServer;
    private String MySQLUser;
    private String MySQLPassword;
    private String MySQLDatabase;
    Connection con = null;

    public DataSQL(Plugin plugin){
        p = plugin;
    }

    public boolean NewConnect(){
        try{
            MySQLServer = p.getConfig().getString("mysqlServer");
            MySQLUser = p.getConfig().getString("mysqlUser");
            MySQLPassword = p.getConfig().getString("mysqlPassWord");
            MySQLDatabase = p.getConfig().getString("mysqlDatabase");
            con = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase+"?allowPublicKeyRetrieval=true&useSSL=false", MySQLUser, MySQLPassword);
            PreparedStatement preparedStatement = con.prepareStatement("SHOW TABLES LIKE ?;");
            preparedStatement.setString(1,"WhereList");
            ResultSet query = preparedStatement.executeQuery();
            if (!query.next()){
                PreparedStatement preparedStatement1 = con.prepareStatement("create table WhereList (ID int not null primary key, Name varchar(255) , startX int , endX int , startZ int , endZ int , Active tinyint(1)) character set utf8mb4 collate utf8mb4_ja_0900_as_cs_ks; ");
                preparedStatement1.execute();
            }
            con.close();
            return true;
        } catch (SQLException e) {
            p.getLogger().info("MySQLサーバーに接続失敗 : " + e.getMessage());
            return false;
        } finally {
            try{
                if (con != null){
                    con.close();
                }
            } catch (SQLException e) {
                p.getLogger().info("MySQLサーバーの接続に失敗しました。 : " + e.getMessage());
                return false;
            }
        }
    }

    @Override
    public Data[] GetList(int x , int z){
        Data[] data = GetListAll();
        List<Data> temp = new ArrayList<>();

        for (int i = 0; i < data.length; i++){
            if (data[i].startX <= x && x <= data[i].endX && data[i].startZ <= z && z <= data[i].endZ){
                temp.add(data[i]);
                continue;
            }
            if (data[i].startX >= x && x >= data[i].endX && data[i].startZ >= z && z >= data[i].endZ) {
                temp.add(data[i]);
            }
        }

        data = new Data[temp.size()];
        for (int i = 0; i < data.length; i++){
            data[i] = temp.get(i);
        }
        return data;
    }

    @Override
    public boolean SetName(int startX, int endX, int startZ, int endZ, String name, UUID uuid) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase + "?allowPublicKeyRetrieval=true&useSSL=false", MySQLUser, MySQLPassword);
            PreparedStatement statement1 = con.prepareStatement("SELECT count(*) FROM WhereList;");
            ResultSet resultSet1 = statement1.executeQuery();
            int dataCount = 0;
            if (resultSet1.next()){
                dataCount = resultSet1.getInt(1);
            }
            dataCount++;

            PreparedStatement statement2 = con.prepareStatement("INSERT INTO `WhereList` (`ID`, `Name`, `startX`, `endX`, `startZ`, `endZ`, `Active`) VALUES (?, ?, ?, ?, ?, ?, ?) ");
            statement2.setInt(1,dataCount);
            statement2.setString(2,name);
            statement2.setInt(3,startX);
            statement2.setInt(4,endX);
            statement2.setInt(5,startZ);
            statement2.setInt(6,endZ);
            statement2.setBoolean(7,true);
            statement2.execute();

            con.close();

            return true;
        } catch (SQLException e) {
            p.getLogger().info("MySQLサーバーの接続に失敗しました。 : " + e.getMessage());
            //e.printStackTrace();
            return false;
        } finally {
            try{
                con.close();
            } catch (SQLException e) {
                p.getLogger().info("MySQLサーバーの接続に失敗しました。 : " + e.getMessage());
                return false;
            }
        }
    }

    @Override
    public boolean UpdateName(String OldName, String NewName, UUID uuid) {
        try{
            con = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase + "?allowPublicKeyRetrieval=true&useSSL=false", MySQLUser, MySQLPassword);
            PreparedStatement statement1 = con.prepareStatement("SELECT ID FROM WhereList WHERE Name = ? AND Active = 1;");
            statement1.setString(1, OldName);
            ResultSet set = statement1.executeQuery();
            if (set.next()){
                int id = set.getInt("ID");

                PreparedStatement statement2 = con.prepareStatement("UPDATE `WhereList` SET `Name` = ? WHERE `WhereList`.`ID` = ?; ");
                statement2.setString(1, NewName);
                statement2.setInt(2, id);
                statement2.execute();

                return true;
            }
            return false;
        } catch (SQLException e) {
            p.getLogger().info("MySQLサーバーの接続に失敗しました。 : " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e){
                //e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean DelName(String name, UUID uuid) {
        try{
            con = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase + "?allowPublicKeyRetrieval=true&useSSL=false", MySQLUser, MySQLPassword);
            PreparedStatement statement1 = con.prepareStatement("SELECT ID FROM WhereList WHERE Name = ? AND Active = 1;");
            statement1.setString(1, name);
            ResultSet set = statement1.executeQuery();
            if (set.next()){
                int id = set.getInt("ID");

                PreparedStatement statement2 = con.prepareStatement("UPDATE `WhereList` SET `Active` = ? WHERE `WhereList`.`ID` = ?;");
                statement2.setBoolean(1, false);
                statement2.setInt(2, id);
                statement2.execute();

                con.close();
                return true;
            }
            con.close();
            return false;
        } catch (SQLException e) {
            p.getLogger().info("MySQLサーバーの接続に失敗しました。 : " + e.getMessage());
            return false;
        } finally {
            try {
                con.close();
            }catch (SQLException e){
                p.getLogger().info("MySQLサーバーの接続に失敗しました。 : " + e.getMessage());
                return false;
            }
        }
    }

    @Override
    public Data[] GetListAll() {
        List<Data> temp = new ArrayList<Data>();
        Data[] data = null;
        try{
            con = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase + "?allowPublicKeyRetrieval=true&useSSL=false", MySQLUser, MySQLPassword);
            PreparedStatement statement = con.prepareStatement("SELECT * FROM WhereList WHERE Active = 1;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Data tdata = new Data();
                tdata.Name = resultSet.getString("Name");
                tdata.startX = resultSet.getInt("startX");
                tdata.startZ = resultSet.getInt("startZ");
                tdata.endX = resultSet.getInt("endX");
                tdata.endZ = resultSet.getInt("endZ");
                temp.add(tdata);
            }
            data = new Data[temp.size()];
            for (int i = 0; i < data.length; i++){
                data[i] = temp.get(i);
            }

            con.close();

            return data;
        } catch (SQLException e) {
            p.getLogger().info("MySQLサーバーの接続に失敗しました。 : " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                p.getLogger().info("MySQLサーバーの接続に失敗しました。 : " + e.getMessage());
                return null;
            }
        }

        return null;
    }
}
