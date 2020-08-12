package xyz.n7mn.dev.whereisplugin;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;
import xyz.n7mn.dev.bstats.bukkit.where.Metrics;
import xyz.n7mn.dev.whereisplugin.api.FileSystem;
import xyz.n7mn.dev.whereisplugin.api.WhereData;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;
import xyz.n7mn.dev.whereisplugin.command.CommandMain;
import xyz.n7mn.dev.whereisplugin.command.CommandTab;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public final class WhereIsPlugin extends JavaPlugin {
    WhereIsData WhereIsAPI = null;
    DynmapAPI DynmapAPI = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        // getServer().getPluginManager().registerEvents(new WhereisEventListener(), this);

        WhereIsAPI = new WhereIsData();
        DynmapAPI  = (DynmapAPI) Bukkit.getPluginManager().getPlugin("dynmap");

        getCommand("where").setExecutor(new CommandMain(this, WhereIsAPI));
        getCommand("where").setTabCompleter(new CommandTab(this, WhereIsAPI));

        if (getServer().getPluginManager().getPlugin("LuckPerms") != null){
            getLogger().info("Use LuckPerm Mode");
        }

        String pass = "./" + getDataFolder().toString() + "/Dynmap.json";
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
            pass = pass.replaceAll("/", "\\\\");
        }
        if (new File(pass).exists()){
            if (DynmapAPI != null){
                MarkerAPI markerAPI = DynmapAPI.getMarkerAPI();
                if (markerAPI != null){
                    MarkerSet set = markerAPI.getMarkerSet("n7mn-WhereIsPlugin.MarkerSet");

                    if (set == null){
                        set = markerAPI.createMarkerSet("n7mn-WhereIsPlugin.MarkerSet", "WhereIsPlugin", null, false);
                        set.setMarkerSetLabel("WhereIsPlugin");
                        set.setLayerPriority(1);
                        set.setHideByDefault(false);

                        List<MarkerObject> list = new Gson().fromJson(new FileSystem().Read(pass), new TypeToken<Collection<MarkerObject>>(){}.getType());

                        for (MarkerObject marker : list){
                            AreaMarker marker1 = set.createAreaMarker(marker.getID(), "", false, marker.getWorldName(), marker.getCornerX(), marker.getCornerZ(), true);
                            marker1.setLineStyle(3, 1.0, marker.getLineColor());
                            marker1.setFillStyle(0.0, marker.getFillColor());
                            marker1.setLabel(marker.getLabelName());
                        }
                    }
                }
            }
            new File(pass).delete();
        }

        Metrics metrics = new Metrics(this, 8481);

        getLogger().info("WhereIsPlugin Started!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (WhereIsAPI != null){
            WhereIsAPI.Close();
        }

        if (DynmapAPI != null){
            MarkerAPI markerAPI = DynmapAPI.getMarkerAPI();

            if (markerAPI != null){
                MarkerSet set = markerAPI.getMarkerSet("n7mn-WhereIsPlugin.MarkerSet");

                if (set != null) {

                    Set<AreaMarker> markers = set.getAreaMarkers();

                    List<MarkerObject> markerList = new ArrayList<>();

                    for (AreaMarker marker : markers){
                        markerList.add(new MarkerObject(marker.getMarkerID(), marker.getLabel(), marker.getWorld(), new double[]{marker.getCornerX(0), marker.getCornerX(1)}, new double[]{marker.getCornerZ(0), marker.getCornerZ(1)}, marker.getLineColor(), marker.getFillColor()));
                    }

                    String pass = "./" + getDataFolder().toString() + "/Dynmap.json";
                    if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                        pass = pass.replaceAll("/", "\\\\");
                    }

                    try {
                        if (!new File(pass).exists()){
                            new File(pass).createNewFile();
                        }

                        new FileSystem().Write(pass, new Gson().toJson(markerList));
                    } catch (IOException e) {
                        // e.printStackTrace();
                    }
                }
            }
        }
    }
}

class MarkerObject{
    private String ID;
    private String LabelName;
    private String WorldName;
    private double[] CornerX;
    private double[] CornerZ;
    private int LineColor;
    private int FillColor;

    MarkerObject(String id, String labelName, String worldName, double[] cornerX, double[] cornerZ, int lineColor, int fillColor){
        this.ID = id;
        this.LabelName = labelName;
        this.WorldName = worldName;
        this.CornerX = cornerX;
        this.CornerZ = cornerZ;
        this.LineColor = lineColor;
        this.FillColor = fillColor;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLabelName() {
        return LabelName;
    }

    public void setLabelName(String labelName) {
        LabelName = labelName;
    }

    public String getWorldName() {
        return WorldName;
    }

    public void setWorldName(String worldName) {
        WorldName = worldName;
    }

    public double[] getCornerX() {
        return CornerX;
    }

    public void setCornerX(double[] cornerX) {
        CornerX = cornerX;
    }

    public double[] getCornerZ() {
        return CornerZ;
    }

    public void setCornerZ(double[] cornerZ) {
        CornerZ = cornerZ;
    }

    public int getLineColor() {
        return LineColor;
    }

    public void setLineColor(int lineColor) {
        LineColor = lineColor;
    }

    public int getFillColor() {
        return FillColor;
    }

    public void setFillColor(int fillColor) {
        FillColor = fillColor;
    }
}
