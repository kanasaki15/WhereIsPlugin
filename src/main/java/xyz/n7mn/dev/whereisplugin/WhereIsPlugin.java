package xyz.n7mn.dev.whereisplugin;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.n7mn.dev.bstats.bukkit.where.Metrics;


public final class WhereIsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        Metrics metrics = new Metrics(this, 8481);

        getLogger().info("WhereIsPlugin Started!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}