package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.JSON;
import xyz.n7mn.dev.whereisplugin.dataSystem.Result.DataResult;

import java.io.File;
import java.util.List;

class CommandImport {

    private WhereIsPlugin plugin;
    private String[] args;
    private Player player;

    public CommandImport(WhereIsPlugin p, String[] args, Player player) {
        this.plugin = p;
        this.args = args;
        this.player = player;
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

                

            } else {
                sender.sendMessage(ChatColor.RED + "No Import File");
            }
        }

        return true;
    }
}
