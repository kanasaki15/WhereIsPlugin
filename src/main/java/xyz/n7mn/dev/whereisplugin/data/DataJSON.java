package xyz.n7mn.dev.whereisplugin.data;

import org.bukkit.plugin.Plugin;

class DataJSON implements DataInterface {
    Plugin plugin;
    DataJSON(Plugin p){
        plugin = p;
    }

    @Override
    public boolean NewConnect() {
        return false;
    }

    @Override
    public Data[] GetList(int x, int z) {
        return null;
    }

    @Override
    public boolean SetName(int startX, int endX, int startZ, int endZ, String name) {
        return false;
    }

    @Override
    public boolean UpdateName(String OldName, String NewName) {
        return true;
    }

    @Override
    public boolean DelName(String name) {
        return true;
    }
}
