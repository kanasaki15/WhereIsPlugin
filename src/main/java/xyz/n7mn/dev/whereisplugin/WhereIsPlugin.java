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
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public final class WhereIsPlugin extends JavaPlugin {

    private Connection con = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Metrics metrics = new Metrics(this, 8481);

        // Ver 1.xの設定ファイルを自動変換
        String s_pass = "./" + getDataFolder().getPath() + "/config.yml";
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            s_pass = s_pass.replaceAll("/", "\\\\");
        }

        File file = new File(s_pass);
        if (file.exists()){
            String ln = getConfig().getString("ln");
            if (ln != null && ln.length() > 0){

                getLogger().info(ChatColor.YELLOW + "設定ファイルの自動アップデートをしています。。。");

                boolean mysqlflag = false;
                if (getConfig().getString("mysqlServer") != null && getConfig().getString("mysqlServer").length() > 0){
                    mysqlflag = true;
                }

                String newConfig = "# WhereIsPlugin 設定ファイル\n" +
                        "# by ちのなみ (https://github.com/kanasaki15)\n" +
                        "\n" +
                        "# コマンドから設定をできるようにする (「true」で有効)\n" +
                        "useCommandSetting: false\n" +
                        "\n" +
                        "#\n" +
                        "# ここから下は useCommandSettingの設定が\n" +
                        "# 「false」のときのみ反映されます。\n" +
                        "#\n" +
                        "\n" +
                        "#\n" +
                        "# LuckPerms関係\n" +
                        "#\n" +
                        "\n" +
                        "## LuckPermsを使う場合はtrue\n" +
                        "useLuckPerms: false\n" +
                        "\n" +
                        "## LuckPermsを使う場合どの部分で使うかを設定\n" +
                        "usePermAdd: true # 追加\n" +
                        "usePermUpdate: true # 更新\n" +
                        "usePermDelete: true # 削除\n" +
                        "usePermDynmap: true # Dynmap連携\n" +
                        "usePermHelp: true # ヘルプ\n" +
                        "\n" +
                        "#\n" +
                        "# MySQL関係\n" +
                        "# (使用する場合はMySQL 5.6以降を推奨)\n" +
                        "#\n" +
                        "\n" +
                        "## MySQLを使う場合はtrue\n" +
                        "useMySQL: "+mysqlflag+"\n" +
                        "\n" +
                        "## MySQLのサーバーアドレス\n" +
                        "MySQLServer: '"+getConfig().getString("mysqlServer")+"'\n" +
                        "\n" +
                        "## MySQLのユーザー名\n" +
                        "MySQLUsername: '"+getConfig().getString("mysqlUser")+"'\n" +
                        "\n" +
                        "## MySQLのパスワード\n" +
                        "MySQLPassword: '"+getConfig().getString("mysqlPassWord")+"'\n" +
                        "\n" +
                        "## MySQLのデータベース名\n" +
                        "MySQLDatabase: '"+getConfig().getString("mysqlDatabase")+"'\n" +
                        "\n" +
                        "## MySQLの接続オプション\n" +
                        "MySQLOption: '?allowPublicKeyRetrieval=true&useSSL=false'\n" +
                        "\n" +
                        "#\n" +
                        "# Dynmap関係\n" +
                        "#\n" +
                        "\n" +
                        "## Dynmapに自動で連携する場合は「true」\n" +
                        "DynmapAutoUpdate: true";

                try {
                    PrintWriter p_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)));
                    p_writer.print(newConfig);
                    p_writer.close();
                } catch (FileNotFoundException e) {
                    // e.printStackTrace();
                }

                getLogger().info(ChatColor.YELLOW + "設定ファイルの自動アップデートをしました。");
            }

        }

        // Ver 1.xのJSONファイルが残ってたら自動変換
        String pass1 = "./" + getDataFolder().getPath() + "/DataList.json";
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            pass1 = pass1.replaceAll("/", "\\\\");
        }

        File file2 = new File(pass1);
        if (file2.exists()){
            getLogger().info(ChatColor.YELLOW + "過去のデータを自動インポートしています。。。");
            StringBuffer sb = new StringBuffer();
            BufferedReader buffer = null;
            try {
                FileInputStream input = new FileInputStream(file2);
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

            if (sb.toString().length() >= 2){
                String pass2 = "./" + getDataFolder().getPath() + "/data.db";
                if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
                    pass2 = pass2.replaceAll("/", "\\\\");
                }

                try {
                    con = DriverManager.getConnection("jdbc:sqlite:" + pass2);
                    con.setAutoCommit(true);
                } catch (SQLException throwable) {
                    // e.printStackTrace();
                }

                List<oldWhereData> list = new GsonBuilder().setPrettyPrinting().create().fromJson(sb.toString(), new TypeToken<Collection<oldWhereData>>(){}.getType());
                for (oldWhereData data : list){
                    WhereisData temp = new WhereisData(data.getID(), data.getUUID(), data.getName(), Bukkit.getServer().getWorld(data.getWorldName()), data.getStartX(), data.getEndX(), data.getStartZ(), data.getEndZ(), data.isActive());
                    new WhereisDataAPI(this, con).addData(temp);
                }

                try {
                    con.close();
                    con = null;
                } catch (SQLException e) {
                    // e.printStackTrace();
                }
            }

            file2.deleteOnExit();
            getLogger().info(ChatColor.YELLOW + "過去のデータを自動インポートしました。");
        }

        String pass = "./" + getDataFolder().getPath() + "/data.db";
        // Ver 1.xから2.xへのMySQLインポート
        if (new WhereisConfigAPI().isUseMySQL()){
            boolean b;
            Connection connection = null;
            final String MySQLServer = new WhereisConfigAPI().getMySQLServer();
            final String MySQLDatabase = new WhereisConfigAPI().getMySQLDatabase();
            final String MySQLOption = new WhereisConfigAPI().getMySQLOption();
            final String MySQLUsername = new WhereisConfigAPI().getMySQLUsername();
            final String MySQLPassword = new WhereisConfigAPI().getMySQLPassword();

            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase + MySQLOption, MySQLUsername, MySQLPassword);
                PreparedStatement statement = connection.prepareStatement("SELECT WorldName FROM WhereList");
                ResultSet set = statement.executeQuery();
                b = set.next();
            }catch (Exception e){
                b = false;
            } finally {
                try {
                    connection.close();
                } catch (Exception e){
                    // e.printStackTrace();
                }
            }

            if (b){
                getLogger().info(ChatColor.YELLOW + "過去のデータを自動インポートしています。。。");

                try {
                    connection = DriverManager.getConnection("jdbc:mysql://" + MySQLServer + "/" + MySQLDatabase + MySQLOption, MySQLUsername, MySQLPassword);

                    List<oldWhereData> oldList = new ArrayList<>();
                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM WhereList");
                    ResultSet set = statement.executeQuery();

                    while (set.next()){
                        oldWhereData data = new oldWhereData(set.getInt("ID"), set.getString("Name"), UUID.fromString(set.getString("CreateUser")), set.getString("WorldName"), set.getInt("startX"), set.getInt("endX"), set.getInt("startZ"), set.getInt("endZ"), set.getBoolean("Active"));
                        oldList.add(data);
                    }
                    connection.prepareStatement("DROP TABLE WhereList").execute();

                    for (oldWhereData data : oldList){
                        WhereisData temp = new WhereisData(data.getID(), data.getUUID(), data.getName(), getServer().getWorld(data.getWorldName()), data.getStartX(), data.getEndX(), data.getStartZ(), data.getEndZ(), data.isActive());
                        new WhereisDataAPI(this, connection).addData(temp);
                    }

                    connection.close();
                } catch (SQLException throwable) {
                    // throwable.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (Exception e){
                        // e.printStackTrace();
                    }
                }
                getLogger().info(ChatColor.YELLOW + "過去のデータを自動インポートしました。。。");
            }
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

        String pass2 = "./" + getDataFolder().getPath() + "/temp/";
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            pass2 = pass2.replaceAll("/", "\\\\");
        }

        File file1 = new File(pass2);
        if (!file1.exists()){
            if (file1.mkdir()){
                file1.setReadable(true);
                file1.setWritable(true);
                file1.setExecutable(true);
            }
        }

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