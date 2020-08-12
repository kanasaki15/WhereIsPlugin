package xyz.n7mn.dev.whereisplugin.api;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

import java.io.File;
import java.util.List;
import java.util.Set;

public class WhereIsDataDynmap {

    private DynmapAPI DynmapAPI = (DynmapAPI) Bukkit.getPluginManager().getPlugin("dynmap");

    public WhereIsDataDynmap() throws DynmapNotFoundException {
        if (DynmapAPI != null){
            MarkerAPI markerAPI = DynmapAPI.getMarkerAPI();

            if (markerAPI != null){
                MarkerSet set = markerAPI.getMarkerSet("n7mn-WhereIsPlugin.MarkerSet");

                if (set == null){
                    set = markerAPI.createMarkerSet("n7mn-WhereIsPlugin.MarkerSet", "WhereIsPlugin", null, false);
                }

                set.setMarkerSetLabel("WhereIsPlugin");
                set.setLayerPriority(1);
                set.setHideByDefault(false);
            }
        } else {
            throw new DynmapNotFoundException();
        }
    }

    public boolean addMarker (int DataID, String color) throws DynmapNotFoundException {

        if (DynmapAPI == null) { throw new DynmapNotFoundException(); }

        try {
            WhereIsData WhereIsAPI = new WhereIsData();
            WhereData data = WhereIsAPI.getWhereData(DataID);
            MarkerAPI markerAPI = DynmapAPI.getMarkerAPI();

            int lineColor = -1;
            try {
                lineColor = Integer.parseInt(color.toLowerCase().replaceAll("#", ""), 16);
            } catch (NumberFormatException e) {
                return false;
            }


            if (data.getLocationName() == null){
                return false;
            }

            if (!data.isActive()){
                return false;
            }

            if (markerAPI != null){
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

                    if (!data.getWorldName().equals(world.getName())){
                        continue;
                    }

                    AreaMarker marker = set.createAreaMarker("WhereIsPlugin_ID_"+data.getID(), "", false, world.getName(), new double[]{data.getStartX(), data.getEndX()}, new double[]{data.getStartZ(), data.getEndZ()}, true);
                    if (lineColor != -1){
                        marker.setLineStyle(3, 1.0, lineColor);
                    } else {
                        marker.setLineStyle(3, 1.0, 0xFF0000);
                    }
                    marker.setFillStyle(0.0, 0x000000);
                    marker.setLabel(data.getLocationName());
                    return true;
                }
            }
        } catch (Exception e){
            return false;
        }

        return false;
    }

    public boolean addMarker(int DataID) throws DynmapNotFoundException {
        return addMarker(DataID, "#ff0000");
    }

    @Deprecated
    public boolean addMarker(String name) throws DynmapNotFoundException {

        return addMarker(new WhereIsData().getWhereData(name).getID());
    }

    public boolean delMarker(int DataID) throws DynmapNotFoundException {

        if (DynmapAPI == null) { throw new DynmapNotFoundException(); }

        try {
            WhereIsData WhereIsAPI = new WhereIsData();
            WhereData data = WhereIsAPI.getWhereData(DataID);
            MarkerAPI markerAPI = DynmapAPI.getMarkerAPI();

            if (data.getLocationName() == null){
                return false;
            }

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
            return false;
        }
    }

    @Deprecated
    public boolean delMarker(String name) throws DynmapNotFoundException{

        return delMarker(new WhereIsData().getWhereData(name).getID());
    }


    @Deprecated
    public boolean updateMaker(int DataID) throws DynmapNotFoundException {
        return updateMarker(DataID);
    }

    public boolean updateMarker(int DataID) throws DynmapNotFoundException {
        if (DynmapAPI == null) { throw new DynmapNotFoundException(); }

        MarkerAPI markerAPI = DynmapAPI.getMarkerAPI();
        if (markerAPI != null) {
            MarkerSet set = markerAPI.getMarkerSet("n7mn-WhereIsPlugin.MarkerSet");
            if (set == null) {

            } else {
                Set<AreaMarker> markers = set.getAreaMarkers();
                for (AreaMarker maker : markers) {
                    if (maker.getMarkerID().equals("WhereIsPlugin_ID_" + DataID)) {
                        if (delMarker(DataID)){
                            return addMarker(DataID, "#"+Integer.toHexString(maker.getLineColor()));
                        }
                    }
                }
            }
        }

        if (delMarker(DataID)){
            return addMarker(DataID);
        }

        return false;
    }

    @Deprecated
    public boolean updateMaker(String name) throws DynmapNotFoundException {

        return updateMarker(new WhereIsData().getWhereData(name).getID());
    }

    @Deprecated
    public boolean updateMarker(String name) throws DynmapNotFoundException {

        return updateMarker(new WhereIsData().getWhereData(name).getID());
    }

    public boolean isDataExists(int DataID){

        WhereIsData WhereIsAPI = new WhereIsData();
        WhereData data = WhereIsAPI.getWhereData(DataID);
        MarkerAPI markerAPI = DynmapAPI.getMarkerAPI();

        // System.out.println("？ : "+DataID);
        if (data != null){
            // System.out.println("？？");
            if (markerAPI != null) {
                MarkerSet set = markerAPI.getMarkerSet("n7mn-WhereIsPlugin.MarkerSet");

                if (set == null) {
                    return false;
                }

                Set<AreaMarker> markers = set.getAreaMarkers();
                // System.out.println("？？？");
                for (AreaMarker maker : markers) {
                    if (maker.getMarkerID().equals("WhereIsPlugin_ID_" + data.getID())) {
                        return true;
                    }
                }
            }
        }
        // System.out.println("！");
        return false;
    }

    public void allDeleteMarker(){
        if (DynmapAPI != null) {
            MarkerAPI markerAPI = DynmapAPI.getMarkerAPI();

            if (markerAPI == null){ return; }

            MarkerSet set = markerAPI.getMarkerSet("n7mn-WhereIsPlugin.MarkerSet");

            if (set == null){ return; }

            Set<AreaMarker> markers = set.getAreaMarkers();

            if (markers == null){ return; }

            for (AreaMarker marker : markers){
                marker.deleteMarker();
            }

            set.deleteMarkerSet();
        }
    }
}
