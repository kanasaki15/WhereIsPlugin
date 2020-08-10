package xyz.n7mn.dev.whereisplugin.api;

import com.sun.istack.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystemResult;

import java.io.*;

public class FileSystem {
    private final Plugin plugin = Bukkit.getPluginManager().getPlugin("WhereIsPlugin");
    private String ErrorMessage = null;

    public String Read(String pass){
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
            pass = pass.replaceAll("/", "\\\\");
        }

        BufferedReader buffer = null;
        File file = new File(pass);
        try {
            FileInputStream input = new FileInputStream(file);
            InputStreamReader stream = new InputStreamReader(input, "UTF-8");
            buffer = new BufferedReader(stream);
            StringBuffer sb = new StringBuffer();
            int ch = buffer.read();
            while (ch != -1) {
                sb.append((char) ch);
                ch = buffer.read();
            }
            buffer.close();
            return sb.toString();
        } catch (IOException e) {
            try {
                if (buffer != null){
                    buffer.close();
                }
            } catch (IOException ex) {
                // ex.printStackTrace();
            }
            return "";
        } finally {
            try {
                if (buffer != null){
                    buffer.close();
                }
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
    }

    String Read(){

        String pass = "./" + plugin.getDataFolder().toString() + "/DataList.json";
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
            pass = pass.replaceAll("/", "\\\\");
        }

        if (!new File(pass).exists()){
            try {
                new File(pass).createNewFile();
                return "[]";
            } catch (IOException e) {
                ErrorMessage = e.getMessage();
                return "";
            }
        }

        return Read(pass);
    }

    public boolean Write(String pass, String Contents){
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
            pass = pass.replaceAll("/", "\\\\");
        }

        File file = new File(pass);
        PrintWriter p_writer = null;

        try{
            p_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")));
            p_writer.print(Contents);
            p_writer.close();
            return true;
        } catch (FileNotFoundException e) {
            ErrorMessage = e.getMessage();
            return false;
        } catch (UnsupportedEncodingException e) {
            ErrorMessage = e.getMessage();
            return false;
        } finally {
            if (p_writer != null){
                p_writer.close();
            }
        }
    }

    boolean Write(String Contents){
        String pass = "./" + plugin.getDataFolder().toString() + "/DataList.json";
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
            pass = pass.replaceAll("/", "\\\\");
        }

        if (!new File(pass).exists()){
            try {
                new File(pass).createNewFile();
            } catch (IOException e) {
                ErrorMessage = e.getMessage();
                return false;
            }
        }
        return Write(pass, Contents);
    }

    public String getErrorMessage(){

        return ErrorMessage;
    }
}
