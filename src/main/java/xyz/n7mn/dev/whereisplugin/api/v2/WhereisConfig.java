package xyz.n7mn.dev.whereisplugin.api.v2;

// 内部でのJSON読み書き用
class WhereisConfig {
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

    public WhereisConfig(boolean useLuckPerms, boolean usePermAdd, boolean usePermUpdate, boolean usePermDelete, boolean usePermDynmap, boolean usePermHelp, boolean useMySQL, String mySQLServer, String mySQLUsername, String mySQLPassword, String mySQLDatabase, String mySQLOption, boolean dynmapAutoUpdate){
        this.useLuckPerms = useLuckPerms;
        this.usePermAdd = usePermAdd;
        this.usePermUpdate = usePermUpdate;
        this.usePermDelete = usePermDelete;
        this.usePermDynmap = usePermDynmap;
        this.usePermHelp = usePermHelp;
        this.useMySQL = useMySQL;
        this.MySQLServer = mySQLServer;
        this.MySQLUsername = mySQLUsername;
        this.MySQLPassword = mySQLPassword;
        this.MySQLDatabase = mySQLDatabase;
        this.MySQLOption = mySQLOption;
        this.DynmapAutoUpdate = dynmapAutoUpdate;
    }

    public boolean isUseLuckPerms() {
        return useLuckPerms;
    }

    public boolean isUsePermAdd() {
        return usePermAdd;
    }

    public boolean isUsePermUpdate() {
        return usePermUpdate;
    }

    public boolean isUsePermDelete() {
        return usePermDelete;
    }

    public boolean isUsePermDynmap() {
        return usePermDynmap;
    }

    public boolean isUsePermHelp() {
        return usePermHelp;
    }

    public boolean isUseMySQL() {
        return useMySQL;
    }

    public String getMySQLServer() {
        return MySQLServer;
    }

    public String getMySQLUsername() {
        return MySQLUsername;
    }

    public String getMySQLPassword() {
        return MySQLPassword;
    }

    public String getMySQLDatabase() {
        return MySQLDatabase;
    }

    public String getMySQLOption() {
        return MySQLOption;
    }

    public boolean isDynmapAutoUpdate() {
        return DynmapAutoUpdate;
    }

}
