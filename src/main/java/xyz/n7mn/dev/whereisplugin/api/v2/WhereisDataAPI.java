package xyz.n7mn.dev.whereisplugin.api.v2;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class WhereisDataAPI {

    private final Connection con;
    private final Plugin plugin;
    private final WhereisConfigAPI configAPI = new WhereisConfigAPI();

    public WhereisDataAPI(Plugin plugin, Connection con){
        this.plugin = plugin;
        this.con = con;
    }

    public WhereisData getData(int id){
        if (con == null){
            return null;
        }

        if (!isTableFound()){
            createTable();
        }

        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM WhereList WHERE ID = ?");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return new WhereisData(
                        resultSet.getInt("ID"),
                        UUID.fromString(resultSet.getString("CreateUser")),
                        resultSet.getString("Name"),
                        Bukkit.getServer().getWorld(UUID.fromString(resultSet.getString("WorldUUID"))),
                        resultSet.getInt("StartX"),
                        resultSet.getInt("EndX"),
                        resultSet.getInt("StartZ"),
                        resultSet.getInt("EndZ"),
                        resultSet.getBoolean("Active"));
            } else {
                return null;
            }
        } catch (SQLException e){
            plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
            e.printStackTrace();
            return null;
        }
    }

    public List<WhereisData> getDataList(){
        if (con == null){
            return null;
        }

        if (!isTableFound()){
            createTable();
        }

        List<WhereisData> list = new ArrayList<>();

        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM WhereList WHERE ID = ? ORDER BY ID");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                WhereisData data = new WhereisData(
                        resultSet.getInt("ID"),
                        UUID.fromString(resultSet.getString("CreateUser")),
                        resultSet.getString("Name"),
                        Bukkit.getServer().getWorld(UUID.fromString(resultSet.getString("WorldUUID"))),
                        resultSet.getInt("StartX"),
                        resultSet.getInt("EndX"),
                        resultSet.getInt("StartZ"),
                        resultSet.getInt("EndZ"),
                        resultSet.getBoolean("Active"));
                list.add(data);
            }

            return list;
        } catch (SQLException e){
            plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
            e.printStackTrace();
            return null;
        }
    }

    public boolean addData(WhereisData data){
        if (con == null){
            return false;
        }

        if (!isTableFound()){
            createTable();
        }

        try {

            int id = data.getID();
            if (id <= 0){
                PreparedStatement statement1 = con.prepareStatement("SELECT COUNT(*) FROM WhereList");
                ResultSet set = statement1.executeQuery();
                if (set.next()){
                    id = set.getInt("COUNT(*)") + 1;
                } else {
                    id = 1;
                }
            }

            PreparedStatement statement2 = con.prepareStatement("INSERT INTO WhereList (`ID`, `CreateUser`, `Name`, `WorldUUID`, `StartX`, `EndX`, `StartZ`, `EndZ`, `Active`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); ");
            statement2.setInt(1, id);
            statement2.setString(2, data.getCreateUser().toString());
            statement2.setString(3, data.getName());
            statement2.setString(4, data.getWorld().getUID().toString());
            statement2.setInt(5, data.getStartX());
            statement2.setInt(6, data.getEndX());
            statement2.setInt(7, data.getStartZ());
            statement2.setInt(8, data.getEndZ());
            statement2.setBoolean(9, data.isActive());
            statement2.execute();

            return true;
        } catch (Exception e){
            plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
            e.printStackTrace();
            return false;
        }
    }

    public boolean addData(UUID createUser, String name, World world, int startX, int endX, int startZ, int endZ, boolean active){
        return addData(new WhereisData(-1, createUser, name, world, startX, endX, startZ, endZ, active));
    }

    public boolean delData(int id){
        if (con == null){
            return false;
        }

        if (!isTableFound()){
            createTable();
        }

        try {
            PreparedStatement statement = con.prepareStatement("DELETE FROM `WhereList` WHERE `ID` = ? Active = 1");
            statement.setInt(1, id);
            statement.execute();

            return true;
        } catch (Exception e){
            plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
            e.printStackTrace();
            return false;
        }
    }

    public boolean delData(WhereisData data){
        return delData(data.getID());
    }

    public boolean updateData(WhereisData data){
        if (con == null){
            return false;
        }

        if (!isTableFound()){
            createTable();
        }

        try {
            PreparedStatement statement = con.prepareStatement("UPDATE WhereList SET CreateUser = ?, Name = ?, WorldUUID = ?, StartX = ?, EndX = ?, StartZ = ?, EndZ = ?, Active = ? WHERE ID = ?");
            statement.setString(1, data.getCreateUser().toString());
            statement.setString(2, data.getName());
            statement.setString(3, data.getWorld().getUID().toString());
            statement.setInt(4, data.getStartX());
            statement.setInt(5, data.getEndX());
            statement.setInt(6, data.getStartZ());
            statement.setInt(7, data.getEndZ());
            statement.setBoolean(8, data.isActive());
            statement.setInt(9, data.getID());
            statement.execute();
            return true;
        } catch (Exception e){
            plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
            e.printStackTrace();
            return false;
        }
    }


    public boolean isTableFound(){
        try {
            PreparedStatement statement;
            if (configAPI.isUseMySQL()){
                statement = con.prepareStatement("SHOW TABLES LIKE 'WhereList';");
            } else {
                statement = con.prepareStatement("SELECT COUNT(*) FROM WhereList");
            }

            return statement.executeQuery().next();
        } catch (Exception e){
            return false;
        }
    }

    public void createTable(){
        try {
            PreparedStatement statement;
            if (configAPI.isUseMySQL()){
                statement = con.prepareStatement("CREATE TABLE `WhereList` (" +
                        " `ID` INT NOT NULL ," +
                        " `CreateUser` VARCHAR(36) NULL ," +
                        " `Name` VARCHAR(255) NULL ," +
                        " `WorldUUID` VARCHAR(36) NOT NULL ," +
                        " `StartX` INT NOT NULL ," +
                        " `EndX` INT NOT NULL ," +
                        " `StartZ` INT NOT NULL ," +
                        " `EndZ` INT NOT NULL ," +
                        " `Active` BOOLEAN NOT NULL ," +
                        " PRIMARY KEY (`ID`)); "
                );
            } else {
                statement = con.prepareStatement("CREATE TABLE WhereList (" +
                        " ID INTEGER NOT NULL ," +
                        " CreateUser VARCHAR(36) ," +
                        " Name VARCHAR(255) NOT NULL ," +
                        " WorldUUID VARCHAR(36) NOT NULL ," +
                        " StartX INTEGER NOT NULL ," +
                        " EndX INTEGER NOT NULL , " +
                        " StartZ INTEGER NOT NULL , " +
                        " EndZ INTEGER NOT NULL , " +
                        " Active INTEGER NOT NULL);"
                );
            }
            statement.execute();
        } catch (Exception e){
            // e.printStackTrace();
        }
    }


}
