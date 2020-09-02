package xyz.n7mn.dev.whereisplugin;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.n7mn.dev.bstats.bukkit.where.Metrics;


public final class WhereIsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        Metrics metrics = new Metrics(this, 8481);

        // CREATE TABLE `WhereisList` ( `ID` INT NOT NULL , `WhereName` TEXT NOT NULL , `WorldName` TEXT NOT NULL , `StartX` INT NOT NULL , `StartZ` INT NOT NULL , `EndX` INT NOT NULL , `EndZ` INT NOT NULL , `CreateUser` VARCHAR(36) NOT NULL , `Active` BOOLEAN NOT NULL );

        getLogger().info("WhereIsPlugin Started!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}