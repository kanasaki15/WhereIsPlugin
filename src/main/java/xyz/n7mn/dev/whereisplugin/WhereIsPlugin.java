package xyz.n7mn.dev.whereisplugin;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.n7mn.dev.whereisplugin.command.WhereIsCommand;
import xyz.n7mn.dev.whereisplugin.command.WhereisTab;

public final class WhereIsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getCommand("where").setExecutor(new WhereIsCommand(this));
        getCommand("where").setTabCompleter(new WhereisTab(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
