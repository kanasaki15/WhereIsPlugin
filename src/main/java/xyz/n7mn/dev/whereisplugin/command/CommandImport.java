package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;
import xyz.n7mn.dev.whereisplugin.dataSystem.DataSystemResult;
import xyz.n7mn.dev.whereisplugin.dataSystem.JSON;
import xyz.n7mn.dev.whereisplugin.dataSystem.MySQL;
import xyz.n7mn.dev.whereisplugin.dataSystem.Result.DataResult;
import xyz.n7mn.dev.whereisplugin.dataSystem.Result.JsonResult;

import java.io.File;
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

        if (args.length == 1 || (args.length == 2 && args[1].equals("mysql"))){

            String filePass = "./" + plugin.getDataFolder().toString() + "/DataList.json";
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                filePass = filePass.replaceAll("/", "\\\\");
            }
            File file = new File(filePass);

            CommandSender sender;
            if (player != null){
                sender = player;
            } else {
                sender = plugin.getServer().getConsoleSender();
            }

            if (file.exists()){
                List<DataResult> list = new JSON(plugin).getAllList();

                if (list.size() == 0){
                    sender.sendMessage(ChatColor.RED + "No Import Data");
                    return true;
                }

                MySQL mysql = new MySQL(plugin);

                int mysqlId = mysql.getAllList().size();

                sender.sendMessage(ChatColor.YELLOW + "Found "+list.size()+" item.");

                if (mysql.isConnect()){

                    sender.sendMessage("Import Start");
                    for (int i = 0; i < list.size(); i++){

                        DataSystemResult result = mysql.addList(new DataResult(mysqlId + (i + 1), list.get(i).LocationName, list.get(i).UUID, list.get(i).StartX, list.get(i).EndX, list.get(i).StartZ, list.get(i).EndZ, list.get(i).Active));
                        if (result.isError()){
                            sender.sendMessage(ChatColor.RED + "Import Error : " + result.getErrorMessage());
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
            CommandSender sender;
            if (player != null){
                sender = player;
            } else {
                sender = plugin.getServer().getConsoleSender();
            }

            MySQL mysql = new MySQL(plugin);
            if (!mysql.isConnect()){
                sender.sendMessage(ChatColor.RED + "MySQL Server is Not Connect.");
                return true;
            }

            List<DataResult> list = mysql.getAllList();
            if (list.size() == 0){
                sender.sendMessage(ChatColor.RED + "No Import Data");
                return true;
            }

            JSON json = new JSON(plugin);
            sender.sendMessage(ChatColor.YELLOW + "Found "+list.size()+" item.");

            int jsonId = json.getAllList().size();
            for (int i = 0; i < list.size(); i++){

                DataSystemResult result = json.addList(new DataResult(jsonId + (i + 1), list.get(i).LocationName, list.get(i).UUID, list.get(i).StartX, list.get(i).EndX, list.get(i).StartZ, list.get(i).EndZ, list.get(i).Active));

                if (result.isError()){
                    sender.sendMessage(ChatColor.RED + "Import Error : " + result.getErrorMessage());
                    return true;
                }

                if (!(sender instanceof Player)){
                    sender.sendMessage("Item Import : " + (i + 1) + " / "+list.size());
                }
            }

            sender.sendMessage(ChatColor.GREEN + "Import Complete!!");
        }

        return true;
    }
}
