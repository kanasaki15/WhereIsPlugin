package xyz.n7mn.dev.whereisplugin.api.v2;

import java.sql.*;

public class ConnectSystem {

    public Connection getMySQLConnect(String serverName, String userName, String userPassword, String database, String option){

        try {
            final Connection con = DriverManager.getConnection("jdbc:mysql://" + serverName + "/" + database + option, userName, userPassword);
            return con;
        } catch (SQLException throwable) {
            // throwable.printStackTrace();
            return null;
        }

    }

    public boolean isMySQLConnect(Connection con){
        try {
            PreparedStatement statement = con.prepareStatement("SELECT 1 FROM DUMMY;");
            ResultSet set = statement.executeQuery();
            if (set.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException throwable) {
            // throwable.printStackTrace();
            return false;
        }
    }

    public boolean closeMySQLConnect(Connection con){
        try {
            con.close();
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
