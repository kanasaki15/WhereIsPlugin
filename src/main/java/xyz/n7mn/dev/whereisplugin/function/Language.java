package xyz.n7mn.dev.whereisplugin.function;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Language {
    public static String getLanguage(){
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WhereIsPlugin");
        return plugin.getConfig().getString("ln");
    }
}
