package xyz.n7mn.dev.whereisplugin;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.n7mn.dev.bstats.bukkit.where.Metrics;
import xyz.n7mn.dev.whereisplugin.command.WhereisCommand;

import java.sql.Connection;
import java.sql.SQLException;

public final class WhereIsPlugin extends JavaPlugin {

    private Connection con = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Metrics metrics = new Metrics(this, 8481);


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
    }
}