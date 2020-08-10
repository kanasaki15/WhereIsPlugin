package xyz.n7mn.dev.whereisplugin.command;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.api.FileSystem;
import xyz.n7mn.dev.whereisplugin.api.WhereData;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;

import java.io.File;
import java.util.Collection;
import java.util.List;

class CommandImport {

    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;

    private WhereIsData WhereIsAPI;

    @Deprecated
    public CommandImport(WhereIsPlugin p, String[] args, Player player) {
        this.plugin = p;
        this.args = args;
        this.player = player;
        this.WhereIsAPI = new WhereIsData();
    }

    public CommandImport(WhereIsPlugin p, String[] args, Player player, WhereIsData api) {
        this.plugin = p;
        this.args = args;
        this.player = player;
        this.WhereIsAPI = api;
    }

    public boolean run(){

        CommandSender sender = Bukkit.getConsoleSender();
        if (player != null){
            sender = player;
        }

        if (args.length == 1 || (args.length == 2 && args[1].equals("mysql"))){

            String filePass = "./" + plugin.getDataFolder().toString() + "/DataList.json";
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                filePass = filePass.replaceAll("/", "\\\\");
            }
            File file = new File(filePass);

            String read = new FileSystem().Read(filePass);

            if (file.exists()){
                List<WhereData> list = new Gson().fromJson(read, new TypeToken<Collection<WhereData>>(){}.getType());

                if (list.size() == 0){
                    sender.sendMessage(ChatColor.RED + "No Import Data");
                    return true;
                }

                sender.sendMessage(ChatColor.YELLOW + "Found "+list.size()+" item.");

                if (WhereIsAPI.getUseSystem().equals("MySQL")){

                    sender.sendMessage("Import Start");
                    for (int i = 0; i < list.size(); i++){

                        list.get(i).setID(WhereIsAPI.getDataListByALL().size() + (i + 1));

                        boolean b = WhereIsAPI.addWhereData(list.get(i));
                        if (!b){
                            sender.sendMessage(ChatColor.RED + "Import Error : " + WhereIsAPI.getErrorMessage());
                            return true;
                        }

                        if (!(sender instanceof Player)){
                            sender.sendMessage("Item Import : " + (i + 1) + " / "+list.size());
                        }
                    }

                    sender.sendMessage(ChatColor.GREEN + "Import Complete!!");

                } else {
                    sender.sendMessage(ChatColor.RED + "MySQL Server is Not Connect.");
                    return true;
                }

            } else {
                sender.sendMessage(ChatColor.RED + "No Import File");
            }
        }

        if (args.length == 2 && args[1].equals("json")){

            if (!WhereIsAPI.getUseSystem().equals("MySQL")){
                sender.sendMessage(ChatColor.RED + "MySQL Server is Not Connect.");
                return true;
            }

            List<WhereData> list = WhereIsAPI.getDataListByALL();
            if (list.size() == 0){
                sender.sendMessage(ChatColor.RED + "No Import Data");
                return true;
            }

            sender.sendMessage(ChatColor.YELLOW + "Found "+list.size()+" item.");

            for (int i = 0; i < list.size(); i++){

                list.get(i).setID(WhereIsAPI.getDataListByALL().size() + (i + 1));

                if (!(sender instanceof Player)){
                    sender.sendMessage("Item Import : " + (i + 1) + " / "+list.size());
                }
            }

            String filePass = "./" + plugin.getDataFolder().toString() + "/DataList.json";
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                filePass = filePass.replaceAll("/", "\\\\");
            }

            FileSystem system = new FileSystem();
            boolean b = system.Write(filePass, new GsonBuilder().setPrettyPrinting().create().toJson(list));
            if (!b){
                sender.sendMessage(ChatColor.RED + "Import Error : " + system.getErrorMessage());
            }
            sender.sendMessage(ChatColor.GREEN + "Import Complete!!");
        }

        return true;
    }
}
