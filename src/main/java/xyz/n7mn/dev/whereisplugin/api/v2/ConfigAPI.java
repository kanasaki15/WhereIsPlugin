package xyz.n7mn.dev.whereisplugin.api.v2;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ConfigAPI {

    private String name;
    private Object value;

    public ConfigAPI(){

    }

    public ConfigAPI(String name, Object value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public static ConfigAPI getConfig(String name){
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("WhereIsPlugin");

        if (!plugin.getConfig().getBoolean("useCommandSetting")){
            return new ConfigAPI(name, plugin.getConfig().get(name));
        }

        return new ConfigAPI();

    }
}
