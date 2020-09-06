package xyz.n7mn.dev.whereisplugin.api.v2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class WhereisConfigAPI {
    private static Plugin plugin = Bukkit.getPluginManager().getPlugin("WhereIsPlugin");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String filePass;

    private final boolean useCommandSetting = plugin.getConfig().getBoolean("useCommandSetting");
    private boolean useLuckPerms;
    private boolean usePermAdd;
    private boolean usePermUpdate;
    private boolean usePermDelete;
    private boolean usePermDynmap;
    private boolean usePermHelp;
    private boolean useMySQL;
    private String MySQLServer;
    private String MySQLUsername;
    private String MySQLPassword;
    private String MySQLDatabase;
    private String MySQLOption;
    private boolean DynmapAutoUpdate;

    public WhereisConfigAPI(){
        plugin = Bukkit.getPluginManager().getPlugin("WhereIsPlugin");

        if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
            filePass = "./"+plugin.getDataFolder().getPath()+"/setting.json".replaceAll("/", "\\\\");
        }else{
            filePass = "./"+plugin.getDataFolder().getPath()+"/setting.json";
        }

        init();
    }

    public static WhereisConfigAPI WhereisConfigReload(){
        plugin.reloadConfig();
        return new WhereisConfigAPI();
    }

    public boolean isUseCommandSetting() {
        return useCommandSetting;
    }

    public boolean isUseLuckPerms() {
        return useLuckPerms;
    }

    public void setUseLuckPerms(boolean useLuckPerms) {
        if (isUseCommandSetting()) {
            this.useLuckPerms = useLuckPerms;
            WhereisConfig config = new WhereisConfig(
                    this.useLuckPerms,
                    this.usePermAdd,
                    this.usePermUpdate,
                    this.usePermDelete,
                    this.usePermDynmap,
                    this.usePermHelp,
                    this.useMySQL,
                    this.MySQLServer,
                    this.MySQLUsername,
                    this.MySQLPassword,
                    this.MySQLDatabase,
                    this.MySQLOption,
                    this.DynmapAutoUpdate
            );

            fileWrite(filePass, gson.toJson(config));
        }
    }

    public boolean isUsePermAdd() {
        return usePermAdd;
    }

    public void setUsePermAdd(boolean usePermAdd) {
        if (isUseCommandSetting()) {
            this.usePermAdd = usePermAdd;
            WhereisConfig config = new WhereisConfig(
                    this.useLuckPerms,
                    this.usePermAdd,
                    this.usePermUpdate,
                    this.usePermDelete,
                    this.usePermDynmap,
                    this.usePermHelp,
                    this.useMySQL,
                    this.MySQLServer,
                    this.MySQLUsername,
                    this.MySQLPassword,
                    this.MySQLDatabase,
                    this.MySQLOption,
                    this.DynmapAutoUpdate
            );

            fileWrite(filePass, gson.toJson(config));
        }
    }

    public boolean isUsePermUpdate() {
        return usePermUpdate;
    }

    public void setUsePermUpdate(boolean usePermUpdate) {
        if (isUseCommandSetting()) {
            this.usePermUpdate = usePermUpdate;
            WhereisConfig config = new WhereisConfig(
                    this.useLuckPerms,
                    this.usePermAdd,
                    this.usePermUpdate,
                    this.usePermDelete,
                    this.usePermDynmap,
                    this.usePermHelp,
                    this.useMySQL,
                    this.MySQLServer,
                    this.MySQLUsername,
                    this.MySQLPassword,
                    this.MySQLDatabase,
                    this.MySQLOption,
                    this.DynmapAutoUpdate
            );

            fileWrite(filePass, gson.toJson(config));
        }
    }

    public boolean isUsePermDelete() {
        return usePermDelete;
    }

    public void setUsePermDelete(boolean usePermDelete) {
        if (isUseCommandSetting()) {
            this.usePermDelete = usePermDelete;
            WhereisConfig config = new WhereisConfig(
                    this.useLuckPerms,
                    this.usePermAdd,
                    this.usePermUpdate,
                    this.usePermDelete,
                    this.usePermDynmap,
                    this.usePermHelp,
                    this.useMySQL,
                    this.MySQLServer,
                    this.MySQLUsername,
                    this.MySQLPassword,
                    this.MySQLDatabase,
                    this.MySQLOption,
                    this.DynmapAutoUpdate
            );

            fileWrite(filePass, gson.toJson(config));
        }
    }

    public boolean isUsePermDynmap() {
        return usePermDynmap;
    }

    public void setUsePermDynmap(boolean usePermDynmap) {
        if (isUseCommandSetting()) {
            this.usePermDynmap = usePermDynmap;
            WhereisConfig config = new WhereisConfig(
                    this.useLuckPerms,
                    this.usePermAdd,
                    this.usePermUpdate,
                    this.usePermDelete,
                    this.usePermDynmap,
                    this.usePermHelp,
                    this.useMySQL,
                    this.MySQLServer,
                    this.MySQLUsername,
                    this.MySQLPassword,
                    this.MySQLDatabase,
                    this.MySQLOption,
                    this.DynmapAutoUpdate
            );

            fileWrite(filePass, gson.toJson(config));
        }
    }

    public boolean isUsePermHelp() {
        return usePermHelp;
    }

    public void setUsePermHelp(boolean usePermHelp) {
        if (isUseCommandSetting()) {
            this.usePermHelp = usePermHelp;
            WhereisConfig config = new WhereisConfig(
                    this.useLuckPerms,
                    this.usePermAdd,
                    this.usePermUpdate,
                    this.usePermDelete,
                    this.usePermDynmap,
                    this.usePermHelp,
                    this.useMySQL,
                    this.MySQLServer,
                    this.MySQLUsername,
                    this.MySQLPassword,
                    this.MySQLDatabase,
                    this.MySQLOption,
                    this.DynmapAutoUpdate
            );

            fileWrite(filePass, gson.toJson(config));
        }
    }

    public boolean isUseMySQL() {
        return useMySQL;
    }

    public void setUseMySQL(boolean useMySQL) {
        if (isUseCommandSetting()) {
            this.useMySQL = useMySQL;
            WhereisConfig config = new WhereisConfig(
                    this.useLuckPerms,
                    this.usePermAdd,
                    this.usePermUpdate,
                    this.usePermDelete,
                    this.usePermDynmap,
                    this.usePermHelp,
                    this.useMySQL,
                    this.MySQLServer,
                    this.MySQLUsername,
                    this.MySQLPassword,
                    this.MySQLDatabase,
                    this.MySQLOption,
                    this.DynmapAutoUpdate
            );

            fileWrite(filePass, gson.toJson(config));
        }
    }

    public String getMySQLServer() {
        return MySQLServer;
    }

    public void setMySQLServer(String mySQLServer) {
        if (isUseCommandSetting()) {
            this.MySQLServer = mySQLServer;
            WhereisConfig config = new WhereisConfig(
                    this.useLuckPerms,
                    this.usePermAdd,
                    this.usePermUpdate,
                    this.usePermDelete,
                    this.usePermDynmap,
                    this.usePermHelp,
                    this.useMySQL,
                    this.MySQLServer,
                    this.MySQLUsername,
                    this.MySQLPassword,
                    this.MySQLDatabase,
                    this.MySQLOption,
                    this.DynmapAutoUpdate
            );

            fileWrite(filePass, gson.toJson(config));
        }
    }

    public String getMySQLUsername() {
        return MySQLUsername;
    }

    public void setMySQLUsername(String mySQLUsername) {
        if (isUseCommandSetting()) {
            this.MySQLUsername = mySQLUsername;
            WhereisConfig config = new WhereisConfig(
                    this.useLuckPerms,
                    this.usePermAdd,
                    this.usePermUpdate,
                    this.usePermDelete,
                    this.usePermDynmap,
                    this.usePermHelp,
                    this.useMySQL,
                    this.MySQLServer,
                    this.MySQLUsername,
                    this.MySQLPassword,
                    this.MySQLDatabase,
                    this.MySQLOption,
                    this.DynmapAutoUpdate
            );

            fileWrite(filePass, gson.toJson(config));
        }
    }

    public String getMySQLPassword() {
        return MySQLPassword;
    }

    public void setMySQLPassword(String mySQLPassword) {
        if (isUseCommandSetting()) {
            this.MySQLPassword = mySQLPassword;
            WhereisConfig config = new WhereisConfig(
                    this.useLuckPerms,
                    this.usePermAdd,
                    this.usePermUpdate,
                    this.usePermDelete,
                    this.usePermDynmap,
                    this.usePermHelp,
                    this.useMySQL,
                    this.MySQLServer,
                    this.MySQLUsername,
                    this.MySQLPassword,
                    this.MySQLDatabase,
                    this.MySQLOption,
                    this.DynmapAutoUpdate
            );

            fileWrite(filePass, gson.toJson(config));
        }
    }

    public String getMySQLDatabase() {
        return MySQLDatabase;
    }

    public void setMySQLDatabase(String mySQLDatabase) {
        if (isUseCommandSetting()) {
            this.MySQLDatabase = mySQLDatabase;
            WhereisConfig config = new WhereisConfig(
                    this.useLuckPerms,
                    this.usePermAdd,
                    this.usePermUpdate,
                    this.usePermDelete,
                    this.usePermDynmap,
                    this.usePermHelp,
                    this.useMySQL,
                    this.MySQLServer,
                    this.MySQLUsername,
                    this.MySQLPassword,
                    this.MySQLDatabase,
                    this.MySQLOption,
                    this.DynmapAutoUpdate
            );

            fileWrite(filePass, gson.toJson(config));
        }
    }

    public String getMySQLOption() {
        return MySQLOption;
    }

    public void setMySQLOption(String mySQLOption) {
        if (isUseCommandSetting()) {
            this.MySQLOption = mySQLOption;
            WhereisConfig config = new WhereisConfig(
                    this.useLuckPerms,
                    this.usePermAdd,
                    this.usePermUpdate,
                    this.usePermDelete,
                    this.usePermDynmap,
                    this.usePermHelp,
                    this.useMySQL,
                    this.MySQLServer,
                    this.MySQLUsername,
                    this.MySQLPassword,
                    this.MySQLDatabase,
                    this.MySQLOption,
                    this.DynmapAutoUpdate
            );

            fileWrite(filePass, gson.toJson(config));
        }
    }

    public boolean isDynmapAutoUpdate() {
        return DynmapAutoUpdate;
    }

    public void setDynmapAutoUpdate(boolean dynmapAutoUpdate) {
        if (isUseCommandSetting()) {
            this.DynmapAutoUpdate = dynmapAutoUpdate;
            WhereisConfig config = new WhereisConfig(
                    this.useLuckPerms,
                    this.usePermAdd,
                    this.usePermUpdate,
                    this.usePermDelete,
                    this.usePermDynmap,
                    this.usePermHelp,
                    this.useMySQL,
                    this.MySQLServer,
                    this.MySQLUsername,
                    this.MySQLPassword,
                    this.MySQLDatabase,
                    this.MySQLOption,
                    this.DynmapAutoUpdate
            );

            fileWrite(filePass, gson.toJson(config));
        }
    }



    private String fileRead(String pass){
        try {
            File file = new File(pass);
            FileInputStream input = new FileInputStream(file);
            InputStreamReader stream = new InputStreamReader(input, StandardCharsets.UTF_8);
            BufferedReader buffer = new BufferedReader(stream);
            StringBuffer sb = new StringBuffer();

            int ch = buffer.read();
            while (ch != -1){
                sb.append((char) ch);
                ch = buffer.read();
            }
            return sb.toString();
        } catch (IOException e) {
            // e.printStackTrace();
            return null;
        }
    }

    private boolean fileWrite(String pass, String contents){
        File file = new File(pass);
        PrintWriter p_writer = null;
        try{
            p_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)));
            p_writer.print(contents);
            p_writer.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (p_writer != null){
                p_writer.close();
            }
        }
    }

    private void init(){
        if (useCommandSetting){
            File file = new File(filePass);
            if (!file.exists()){
                try {
                    file.createNewFile();

                    WhereisConfig config = new WhereisConfig(
                            plugin.getConfig().getBoolean("useLuckPerms"),
                            plugin.getConfig().getBoolean("usePermAdd"),
                            plugin.getConfig().getBoolean("usePermUpdate"),
                            plugin.getConfig().getBoolean("usePermDelete"),
                            plugin.getConfig().getBoolean("usePermDynmap"),
                            plugin.getConfig().getBoolean("usePermHelp"),
                            plugin.getConfig().getBoolean("useMySQL"),
                            plugin.getConfig().getString("MySQLServer"),
                            plugin.getConfig().getString("MySQLUsername"),
                            plugin.getConfig().getString("MySQLPassword"),
                            plugin.getConfig().getString("MySQLDatabase"),
                            plugin.getConfig().getString("MySQLOption"),
                            plugin.getConfig().getBoolean("DynmapAutoUpdate")
                    );


                    fileWrite(filePass,gson.toJson(config));

                } catch (IOException e) {
                    // e.printStackTrace();
                }
            } else {
                WhereisConfig config = gson.fromJson(fileRead(filePass), WhereisConfig.class);
                useLuckPerms = config.isUseLuckPerms();
                usePermAdd = config.isUsePermAdd();
                usePermUpdate = config.isUsePermUpdate();
                usePermDelete = config.isUsePermDelete();
                usePermDynmap = config.isUsePermDynmap();
                usePermHelp = config.isUsePermHelp();
                useMySQL = config.isUseMySQL();
                MySQLServer = config.getMySQLServer();
                MySQLUsername = config.getMySQLUsername();
                MySQLPassword = config.getMySQLPassword();
                MySQLDatabase = config.getMySQLDatabase();
                MySQLOption = config.getMySQLOption();
                DynmapAutoUpdate = config.isDynmapAutoUpdate();
            }
        } else {
            WhereisConfig config = new WhereisConfig(
                    plugin.getConfig().getBoolean("useLuckPerms"),
                    plugin.getConfig().getBoolean("usePermAdd"),
                    plugin.getConfig().getBoolean("usePermUpdate"),
                    plugin.getConfig().getBoolean("usePermDelete"),
                    plugin.getConfig().getBoolean("usePermDynmap"),
                    plugin.getConfig().getBoolean("usePermHelp"),
                    plugin.getConfig().getBoolean("useMySQL"),
                    plugin.getConfig().getString("MySQLServer"),
                    plugin.getConfig().getString("MySQLUsername"),
                    plugin.getConfig().getString("MySQLPassword"),
                    plugin.getConfig().getString("MySQLDatabase"),
                    plugin.getConfig().getString("MySQLOption"),
                    plugin.getConfig().getBoolean("DynmapAutoUpdate")
            );

            useLuckPerms = config.isUseLuckPerms();
            usePermAdd = config.isUsePermAdd();
            usePermUpdate = config.isUsePermUpdate();
            usePermDelete = config.isUsePermDelete();
            usePermDynmap = config.isUsePermDynmap();
            usePermHelp = config.isUsePermHelp();
            useMySQL = config.isUseMySQL();
            MySQLServer = config.getMySQLServer();
            MySQLUsername = config.getMySQLUsername();
            MySQLPassword = config.getMySQLPassword();
            MySQLDatabase = config.getMySQLDatabase();
            MySQLOption = config.getMySQLOption();
            DynmapAutoUpdate = config.isDynmapAutoUpdate();
        }
    }
}
