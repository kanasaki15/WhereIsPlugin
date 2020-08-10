package xyz.n7mn.dev.whereisplugin;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.n7mn.dev.bstats.bukkit.Metrics;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;
import xyz.n7mn.dev.whereisplugin.command.CommandMain;
import xyz.n7mn.dev.whereisplugin.command.CommandTab;

public final class WhereIsPlugin extends JavaPlugin {
    WhereIsData WhereIsAPI = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        // getServer().getPluginManager().registerEvents(new WhereisEventListener(), this);

        WhereIsAPI = new WhereIsData();

        getCommand("where").setExecutor(new CommandMain(this, WhereIsAPI));
        getCommand("where").setTabCompleter(new CommandTab(this, WhereIsAPI));

        if (getServer().getPluginManager().getPlugin("LuckPerms") != null){
            getLogger().info("Use LuckPerm Mode");
        }

        Metrics metrics = new Metrics(this, 8481);

        getLogger().info("WhereIsPlugin Started!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (WhereIsAPI != null){
            WhereIsAPI.Close();
        }
    }
}
