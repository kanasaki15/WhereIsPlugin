package xyz.n7mn.dev.whereisplugin.data;

import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DataSQL {
    private Plugin p;
    private String MySQLServer;
    private String MySQLUser;
    private String MySQLPassword;
    private String MySQLDatabase;

    public DataSQL(Plugin plugin){
        p = plugin;

        Connection con = null;
        try{
            MySQLServer = p.getConfig().getString("mysqlServer");
            MySQLUser = p.getConfig().getString("mysqlUser");
            MySQLPassword = p.getConfig().getString("mysqlPassWord");
            MySQLDatabase = p.getConfig().getString("mysqlDatabase");
            con = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase+"?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true", MySQLUser, MySQLPassword);
            PreparedStatement preparedStatement = con.prepareStatement("SHOW TABLES LIKE ?;");
            preparedStatement.setString(1,"WhereList");
            ResultSet query = preparedStatement.executeQuery();
            if (query.getRow() == 0){
                PreparedStatement preparedStatement1 = con.prepareStatement("create table WhereList (int ID , Name varchar(255) , int startX , int startZ character set utf8mb4 collate utf8mb4_ja_0900_as_cs_ks; ");
                preparedStatement1.execute();
            }
            con.close();
        } catch (SQLException e) {
            p.getLogger().info("MySQLサーバーに接続失敗 : " + e.getMessage());
        } finally {
            try{
                if (con != null){
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Data[] GetList(int x , int z){
        Connection con = null;
        Data[] data = null;
        List<Data> temp = new ArrayList<Data>();

        try{
            con = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase, MySQLUser, MySQLPassword);
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
            e.printStackTrace();
        } finally {
            if (con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }
}
