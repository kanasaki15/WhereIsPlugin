package xyz.n7mn.dev.whereisplugin;

import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.n7mn.dev.bstats.bukkit.where.Metrics;
import xyz.n7mn.dev.whereisplugin.api.v2.WhereisConfigAPI;
import xyz.n7mn.dev.whereisplugin.api.v2.WhereisData;
import xyz.n7mn.dev.whereisplugin.api.v2.WhereisDataAPI;
import xyz.n7mn.dev.whereisplugin.command.WhereisCommand;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public final class WhereIsPlugin extends JavaPlugin {

    private Connection con = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Metrics metrics = new Metrics(this, 8481);

        // Ver 1.xのJSONファイルが残ってたら自動変換
        String pass = "./" + getDataFolder().getPath() + "/DataList.json";
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            pass = pass.replaceAll("/", "\\\\");
        }

        File file = new File(pass);
        if (file.exists()){
            getLogger().info(ChatColor.YELLOW + "過去のデータを自動インポートしています。。。");
            StringBuffer sb = new StringBuffer();
            BufferedReader buffer = null;
            try {
                FileInputStream input = new FileInputStream(file);
                InputStreamReader stream = new InputStreamReader(input, StandardCharsets.UTF_8);
                buffer = new BufferedReader(stream);

                int ch = buffer.read();
                while (ch != -1){
                    sb.append((char) ch);
                    ch = buffer.read();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (buffer != null){
                        buffer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            List<oldWhereData> list = new GsonBuilder().setPrettyPrinting().create().fromJson(sb.toString(), new TypeToken<Collection<oldWhereData>>(){}.getType());
            for (oldWhereData data : list){
                WhereisData temp = new WhereisData(data.getID(), data.getUUID(), data.getName(), Bukkit.getServer().getWorld(data.getWorldName()), data.getStartX(), data.getEndX(), data.getStartZ(), data.getEndZ(), data.isActive());
                new WhereisDataAPI(this, con).addData(temp);
            }


            file.deleteOnExit();
            getLogger().info(ChatColor.YELLOW + "過去のデータを自動インポートしました。。。");
        }

        // データベース接続
        if (new WhereisConfigAPI().isUseMySQL()){
            final String MySQLServer = new WhereisConfigAPI().getMySQLServer();
            final String MySQLDatabase = new WhereisConfigAPI().getMySQLDatabase();
            final String MySQLOption = new WhereisConfigAPI().getMySQLOption();
            final String MySQLUsername = new WhereisConfigAPI().getMySQLUsername();
            final String MySQLPassword = new WhereisConfigAPI().getMySQLPassword();

            try {
                con = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase + MySQLOption, MySQLUsername, MySQLPassword);
            } catch (SQLException e){
                con = null;
                // e.printStackTrace();
            }
        } else {
            if (!new File(pass).exists()){
                try {
                    new File(pass).createNewFile();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            }

            try {
                con = DriverManager.getConnection("jdbc:sqlite:"+pass);
                con.setAutoCommit(true);
            } catch (SQLException e){
                // e.printStackTrace();
                con = null;
            }

        }

        saveDefaultConfig();
        getCommand("where").setExecutor(new WhereisCommand(this, con));

        getLogger().info("WhereIsPlugin Started!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            con.close();
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    @Override
    public void onLoad() {
        // super.onLoad();

        try {
            if (con != null){
                con.close();
            }

            if (new WhereisConfigAPI().isUseMySQL()){
                final String MySQLServer = new WhereisConfigAPI().getMySQLServer();
                final String MySQLDatabase = new WhereisConfigAPI().getMySQLDatabase();
                final String MySQLOption = new WhereisConfigAPI().getMySQLOption();
                final String MySQLUsername = new WhereisConfigAPI().getMySQLUsername();
                final String MySQLPassword = new WhereisConfigAPI().getMySQLPassword();

                try {
                    con = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase + MySQLOption, MySQLUsername, MySQLPassword);
                } catch (SQLException e){
                    con = null;
                    // e.printStackTrace();
                }
            } else {
                String pass = "./" + getDataFolder().getPath() + "/data.db";
                if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
                    pass = pass.replaceAll("/", "\\\\");
                }

                try {
                    con = DriverManager.getConnection("jdbc:sqlite:"+pass);
                    con.setAutoCommit(true);
                } catch (SQLException e){
                    // e.printStackTrace();
                    con = null;
                }

            }
        } catch (Exception e){
            // e.printStackTrace();
        }
    }
}