package xyz.n7mn.dev.whereisplugin.api.v2;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class WhereIsDataAPI {

    final private Plugin plugin;

    public WhereIsDataAPI(){
        plugin = null;
    }

    public WhereIsDataAPI(Plugin plugin){
        this.plugin = plugin;
    }

}
