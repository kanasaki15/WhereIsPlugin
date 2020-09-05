package xyz.n7mn.dev.whereisplugin.api.v2;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

public class WhereisDynmapAPI {
    private final DynmapAPI dynmap;
    private final Plugin plugin;
    private final Connection con;
    private final WhereisDataAPI dataAPI;

    public WhereisDynmapAPI(Plugin plugin, Connection con){
        this.dynmap = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
        this.plugin = plugin;
        this.con = con;
        this.dataAPI = new WhereisDataAPI(plugin, con);
    }

    public boolean addDynmapAreaMaker(int id, String color){
        if (dynmap == null){
            return false;
        }

        WhereisData data = dataAPI.getData(id);
        if (data == null){
            return false;
        }

        if (!data.isActive()){
            return false;
        }

        MarkerAPI markerAPI = dynmap.getMarkerAPI();
        if (markerAPI == null){
            return false;
        }

        int lineColor = -1;
        try {
            lineColor = Integer.parseInt(color.toLowerCase().replaceAll("#", ""), 16);
        } catch (NumberFormatException e) {
            return false;
        }

        MarkerSet set = markerAPI.getMarkerSet("n7mn-WhereIsPlugin.MarkerSet");
        if (set == null){
            set = markerAPI.createMarkerSet("n7mn-WhereIsPlugin.MarkerSet", "WhereIsPlugin", null, false);
        }

        set.setMarkerSetLabel("WhereIsPlugin");
        set.setLayerPriority(1);
        set.setHideByDefault(false);

        Set<AreaMarker> markers = set.getAreaMarkers();
        for (AreaMarker maker : markers){
            if (maker.getLabel().equals("WhereIsPlugin_ID_"+data.getID())){
                return false;
            }
        }

        List<World> worlds = Bukkit.getServer().getWorlds();
        for (World world : worlds){

            if (!data.getWorld().getUID().equals(world.getUID())){
                continue;
            }

            AreaMarker marker = set.createAreaMarker("WhereIsPlugin_ID_"+data.getID(), "", false, world.getName(), new double[]{data.getStartX(), data.getEndX()}, new double[]{data.getStartZ(), data.getEndZ()}, true);
            if (lineColor != -1){
                marker.setLineStyle(3, 1.0, lineColor);
            } else {
                marker.setLineStyle(3, 1.0, 0xFF0000);
            }
            marker.setFillStyle(0.0, 0x000000);
            marker.setLabel(data.getName());
            return true;
        }

        return false;
    }

    public boolean addDynmapAreaMaker(int id){
        return addDynmapAreaMaker(id, "#ff0000");
    }

    public boolean delDynmapAreaMaker(int id){
        if (dynmap == null) { return false; }

        WhereisData data = dataAPI.getData(id);
        if (data == null){
            return false;
        }

        if (!data.isActive()){
            return false;
        }

        try {
            MarkerAPI markerAPI = dynmap.getMarkerAPI();

            if (markerAPI != null){
                MarkerSet set = markerAPI.getMarkerSet("n7mn-WhereIsPlugin.MarkerSet");

                if (set == null){
                    return false;
                }

                Set<AreaMarker> markers = set.getAreaMarkers();
                for (AreaMarker maker : markers){
                    if (maker.getMarkerID().equals("WhereIsPlugin_ID_"+data.getID())){
                        maker.deleteMarker();
                        return true;
                    }
                }
            }

            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Set<AreaMarker> getAreaMakerList(){
        if (dynmap == null){
            return null;
        }

        MarkerAPI markerAPI = dynmap.getMarkerAPI();
        if (markerAPI == null){
            return null;
        }

        MarkerSet set = markerAPI.getMarkerSet("n7mn-WhereIsPlugin.MarkerSet");
        if (set == null){
            return null;
        }

        return set.getAreaMarkers();
    }
}
