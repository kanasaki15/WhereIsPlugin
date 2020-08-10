package xyz.n7mn.dev.whereisplugin;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.n7mn.dev.bstats.bukkit.Metrics;
import xyz.n7mn.dev.whereisplugin.command.CommandMain;
import xyz.n7mn.dev.whereisplugin.command.CommandTab;

public final class WhereIsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        // getServer().getPluginManager().registerEvents(new WhereisEventListener(), this);

        getCommand("where").setExecutor(new CommandMain(this));
        getCommand("where").setTabCompleter(new CommandTab(this));

        if (getServer().getPluginManager().getPlugin("LuckPerms") != null){
            getLogger().info("Use LuckPerm Mode");
        }

        Metrics metrics = new Metrics(this, 8481);

        getLogger().info("WhereIsPlugin Started!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
