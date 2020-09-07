package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.whereisplugin.api.v2.WhereisConfigAPI;
import xyz.n7mn.dev.whereisplugin.api.v2.WhereisData;
import xyz.n7mn.dev.whereisplugin.api.v2.WhereisDataAPI;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

public class WhereisAdd extends CommandInterface implements CommandExecutor {

    private final Plugin plugin;
    private final Connection con;

    private final String DataPass;

    public WhereisAdd(Plugin plugin, Connection con){
        this.plugin = plugin;
        this.con = con;

        if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
            this.DataPass = "./" + plugin.getDataFolder().getPath() + "/temp/".replaceAll("/", "\\\\");
        } else {
            this.DataPass = "./" + plugin.getDataFolder().getPath() + "/temp/";
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (new WhereisConfigAPI().isUseLuckPerms() && new WhereisConfigAPI().isUsePermAdd()){
            if (sender instanceof Player){
                Player player = (Player) sender;
                if (!player.hasPermission("whereis") && !player.hasPermission("whereis.add")){
                    player.sendMessage(ChatColor.RED + "権限がありません。");
                    return true;
                }
            }
        }

        if (args.length == 1){
            if (sender instanceof Player){
                Player player = (Player) sender;

                ItemStack stack = new ItemStack(Material.DIAMOND_AXE);
                ItemMeta itemMeta = stack.getItemMeta();
                itemMeta.setDisplayName("WhereisAxe");
                stack.setItemMeta(itemMeta);

                PlayerInventory inventory = player.getInventory();

                if (!inventory.contains(stack)) {
                    inventory.addItem(stack);
                    player.sendMessage(ChatColor.YELLOW + "今ゲットしたダイヤモンドの斧を持ったままブロックなどを右クリックすると範囲を設定できます！");
                    return true;
                } else {
                    PlayerInventory playerInventory = player.getInventory();
                    int size = playerInventory.getSize();
                    for (int i = 0; i < size; i++){
                        ItemStack item = playerInventory.getItem(i);
                        if (item != null && item.getType() == Material.DIAMOND_AXE){
                            ItemMeta meta = item.getItemMeta();
                            if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("WhereisAxe")){
                                player.getInventory().setItem(i, player.getInventory().getItemInMainHand());
                                player.getInventory().setItemInMainHand(stack);
                                player.sendMessage(ChatColor.YELLOW + "今持っているダイヤモンドの斧を持ったままブロックなどを右クリックすると範囲を設定できます！");
                                return true;
                            }
                        }
                    }
                }
            }

            return true;
        }

        if (args.length == 2 && args[1].equals("axeclear") && sender instanceof Player){
            final Player player = (Player) sender;
            final File file = new File(DataPass + player.getName() +".dat");

            if (file.exists()){
                file.deleteOnExit();
                player.sendMessage(ChatColor.GREEN + "範囲指定を解除しました。");
            }
        }

        if (args.length == 2 && !args[1].equals("axeclear")){
            if (sender instanceof Player){

                Player player = (Player) sender;

                if (!new File(DataPass + player.getName() +".dat").exists() && !new File(DataPass + player.getName() +".axe.dat").exists()){
                    File file = new File(DataPass + player.getName() + ".dat");
                    try {
                        if (file.createNewFile()){
                            String ff = args[1] + ","+player.getLocation().getBlockX() + "," + player.getLocation().getBlockZ();

                            PrintWriter p_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)));
                            p_writer.print(ff);
                            p_writer.close();

                            player.sendMessage(ChatColor.YELLOW + "開始位置がX: "+player.getLocation().getBlockX()+" Z: "+player.getLocation().getBlockZ()+"で仮設定されました。");
                            player.sendMessage(ChatColor.YELLOW + "終了地点を設定するには終了地点に移動してから「/where add "+args[1]+"」を実行してください。");
                        }
                    } catch (IOException e){
                        plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
                        e.printStackTrace();
                    }
                } else if (new File(DataPass + player.getName() +".dat").exists() && !new File(DataPass + player.getName() +".axe.dat").exists()) {
                    File file = new File(DataPass + player.getName() + ".dat");

                    StringBuffer sb = new StringBuffer();
                    BufferedReader buffer = null;
                    try {
                        FileInputStream input = new FileInputStream(file);
                        InputStreamReader stream = new InputStreamReader(input, StandardCharsets.UTF_8);
                        buffer = new BufferedReader(stream);

                        int ch = buffer.read();
                        while (ch != -1){
                            sb.append((char) ch);
                            ch = buffer.read();
                        }
                        buffer.close();
                    } catch (IOException e) {
                        plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
                        e.printStackTrace();
                    } finally {
                        try {
                            if (buffer != null){
                                buffer.close();
                            }
                        } catch (IOException e) {
                            plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
                            e.printStackTrace();
                        }
                    }

                    String[] split = sb.toString().split(",");
                    if (split.length == 3){
                        String name = split[0];
                        int StartX = Integer.parseInt(split[1]);
                        int StartZ = Integer.parseInt(split[2]);
                        int EndX = player.getLocation().getBlockX();
                        int EndZ = player.getLocation().getBlockZ();

                        WhereisData data = new WhereisData(-1, player.getUniqueId(), name, player.getWorld(), StartX, EndX, StartZ, EndZ, true);

                        if (new WhereisDataAPI(plugin, con).addData(data)){
                            player.sendMessage(ChatColor.GREEN + "「"+name+"」を設定しました！");
                            file.deleteOnExit();
                        } else {
                            player.sendMessage(ChatColor.RED + "設定失敗しました");
                        }
                    }
                } else if (new File(DataPass + player.getName() +".axe.dat").exists()) {
                    File file = new File(DataPass + player.getName() + ".axe.dat");

                    StringBuffer sb = new StringBuffer();
                    BufferedReader buffer = null;
                    try {
                        FileInputStream input = new FileInputStream(file);
                        InputStreamReader stream = new InputStreamReader(input, StandardCharsets.UTF_8);
                        buffer = new BufferedReader(stream);

                        int ch = buffer.read();
                        while (ch != -1){
                            sb.append((char) ch);
                            ch = buffer.read();
                        }
                        buffer.close();
                    } catch (IOException e) {
                        plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
                        e.printStackTrace();
                    } finally {
                        try {
                            if (buffer != null){
                                buffer.close();
                            }
                        } catch (IOException e) {
                            plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
                            e.printStackTrace();
                        }
                    }

                    final String[] split = sb.toString().split(",");
                    if (split.length == 4){
                        final String name = args[1];
                        final int StartX = Integer.parseInt(split[0]);
                        final int StartZ = Integer.parseInt(split[1]);
                        final int EndX = Integer.parseInt(split[2]);
                        final int EndZ = Integer.parseInt(split[3]);

                        WhereisData data = new WhereisData(-1, player.getUniqueId(), name, player.getWorld(), StartX, EndX, StartZ, EndZ, true);

                        if (new WhereisDataAPI(plugin, con).addData(data)){
                            player.sendMessage(ChatColor.GREEN + "「"+name+"」を設定しました！");
                            file.deleteOnExit();
                        } else {
                            player.sendMessage(ChatColor.RED + "設定失敗しました");
                        }
                    }
                }
            }
        }

        return true;
    }
}
