package xyz.n7mn.dev.whereisplugin.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.*;

@Deprecated
public class FileSystem {
    private final Plugin plugin = Bukkit.getPluginManager().getPlugin("WhereIsPlugin");
    private String ErrorMessage = "This API is deprecated.";

    public String Read(String pass){
        return "";
    }

    String Read(){

        return Read("");
    }

    public boolean Write(String pass, String Contents){
        return false;
    }

    boolean Write(String Contents){
        return false;
    }

    public String getErrorMessage(){

        return ErrorMessage;
    }
}