package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

class WhereisAxeClear extends CommandInterface {

    final String DataPass;

    public WhereisAxeClear(Plugin plugin){
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
            this.DataPass = "./" + plugin.getDataFolder().getPath() + "/temp/".replaceAll("/", "\\\\");
        } else {
            this.DataPass = "./" + plugin.getDataFolder().getPath() + "/temp/";
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            final Player player = (Player) sender;
            final File file = new File(DataPass + player.getName() +".axe.dat");

            if (file.exists()){
                file.deleteOnExit();
                player.sendMessage(ChatColor.GREEN + "範囲指定を解除しました。");
            }
        }

        return true;
    }
}
