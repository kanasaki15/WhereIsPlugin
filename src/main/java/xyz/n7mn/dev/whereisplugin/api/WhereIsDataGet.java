package xyz.n7mn.dev.whereisplugin.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystem;
import xyz.n7mn.dev.whereisplugin.dataSystem.Result.DataResult;

import java.util.List;

@Deprecated
public class WhereIsDataGet {

    private final Plugin plugin = Bukkit.getPluginManager().getPlugin("WhereIsPlugin");
    private final DataSystem system = new DataSystem((WhereIsPlugin) plugin);

    public WhereIsDataGet(){

    }

    public List<DataResult> getListByALL(){
        return system.getDataAllList();
    }

    public List<DataResult> getListByPlayer(Player player){
        return system.getData(player);
    }
}
