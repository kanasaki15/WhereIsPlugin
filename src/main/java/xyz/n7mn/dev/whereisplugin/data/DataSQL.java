package xyz.n7mn.dev.whereisplugin.data;

import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                PreparedStatement preparedStatement1 = con.prepareStatement("create table WhereList (ID int , Name varchar(255) , startX int , endX int , startZ int , endZ int , Active tinyint(1)) character set utf8mb4 collate utf8mb4_ja_0900_as_cs_ks; ");
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
                //e.printStackTrace();
            }
        }
    }

    @Override
    public Data[] GetList(int x , int z){
        Data[] data = null;
        List<Data> temp = new ArrayList<Data>();

        try{
            con = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase + "?allowPublicKeyRetrieval=true&useSSL=false", MySQLUser, MySQLPassword);
            PreparedStatement statement = con.prepareStatement("SELECT * FROM WhereList ORDER BY ID ASC;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Data tdata = new Data();
                tdata.Name = resultSet.getString("Name");
                tdata.startX = resultSet.getInt("startX");
                tdata.startZ = resultSet.getInt("startZ");
                tdata.endX = resultSet.getInt("endX");
                tdata.endZ = resultSet.getInt("endZ");
                if (tdata.startX <= x && x <= tdata.endX && tdata.startZ <= z && z <= tdata.endZ){
                    temp.add(tdata);
                    continue;
                }
                if (tdata.startX >= x && x >= tdata.endX && tdata.startZ >= z && z >= tdata.endZ){
                    temp.add(tdata);
                    continue;
                }
            }
            data = new Data[temp.size()];
            for (int i = 0; i < data.length; i++){
                data[i] = temp.get(i);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        } finally {
            if (con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    //e.printStackTrace();
                }
            }
        }
        return data;
    }

    @Override
    public boolean SetName(int startX, int endX,int startZ,int endZ,String name) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase + "?allowPublicKeyRetrieval=true&useSSL=false", MySQLUser, MySQLPassword);
            PreparedStatement statement1 = con.prepareStatement("SELECT count(*) FROM WhereList");
            ResultSet resultSet1 = statement1.executeQuery();
            int dataCount = 0;
            if (resultSet1.next()){
                dataCount = resultSet1.getInt(0);
            }
            dataCount++;
            System.out.println("Debug : " + dataCount);
            return true;
        } catch (SQLException e) {
            //e.printStackTrace();
            return false;
        } finally {
            try{
                con.close();
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }
    }

    @Override
    public boolean UpdateName(String OldName, String NewName) {
        return true;
    }

    @Override
    public boolean DelName(String name) {
        return true;
    }
}
