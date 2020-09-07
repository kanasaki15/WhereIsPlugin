package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class WhereisListener implements Listener {

    final Plugin plugin;
    private final String DataPass;

    public WhereisListener(Plugin plugin){
        this.plugin = plugin;
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
            this.DataPass = "./" + plugin.getDataFolder().getPath() + "/temp/".replaceAll("/", "\\\\");
        } else {
            this.DataPass = "./" + plugin.getDataFolder().getPath() + "/temp/";
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerInteractEntityEvent(PlayerInteractEntityEvent e){
        System.out.println(e.getRightClicked().getType());
    }

    @EventHandler
    public void PlayerInteract(PlayerInteractEvent e){
        final File file = new File(DataPass + e.getPlayer().getName() + ".axe.dat");
        final Player player = e.getPlayer();

        if (e.getHand() != null && e.getHand().name().equals("OFF_HAND")){
            if (player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_AXE){
                ItemStack hand = player.getInventory().getItemInMainHand();
                if (hand.getItemMeta() != null && hand.getItemMeta().hasDisplayName() && hand.getItemMeta().getDisplayName().equals("WhereisAxe")){
                    if (!file.exists()){
                        try {
                            if (file.createNewFile()){
                                final String text = player.getLocation().getBlockX() + "," + player.getLocation().getBlockZ();
                                PrintWriter p_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)));
                                p_writer.print(text);
                                p_writer.close();
                                String[] msg = new String[]{
                                        ChatColor.YELLOW + "開始位置がX: "+player.getLocation().getBlockX()+" Z: "+player.getLocation().getBlockZ()+"に設定されました。",
                                        ChatColor.YELLOW + "再設定するには「/where axeclear」を実行してください"
                                };
                                player.sendMessage(msg);
                            }
                        } catch (IOException ex) {
                            // ex.printStackTrace();
                        }
                    } else {
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
                        } catch (IOException ex) {
                            plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
                            ex.printStackTrace();
                        } finally {
                            try {
                                if (buffer != null){
                                    buffer.close();
                                }
                            } catch (IOException ex) {
                                plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
                                ex.printStackTrace();
                            }
                        }

                        sb.append(",");
                        sb.append(player.getLocation().getBlockX());
                        sb.append(",");
                        sb.append(player.getLocation().getBlockZ());

                        try {
                            PrintWriter p_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)));
                            p_writer.print(sb.toString());
                            p_writer.close();
                        } catch (IOException ex){
                            plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
                            ex.printStackTrace();
                        } finally {
                            try {
                                if (buffer != null){
                                    buffer.close();
                                }
                            } catch (IOException ex) {
                                plugin.getLogger().info(ChatColor.RED + "エラーが発生しました。");
                                ex.printStackTrace();
                            }
                        }

                        String[] msg = new String[]{
                                ChatColor.YELLOW + "終了位置がX: "+player.getLocation().getBlockX()+" Z: "+player.getLocation().getBlockZ()+"に設定されました。",
                                ChatColor.YELLOW + "名前を設定するには「/where add <名前>」を",
                                ChatColor.YELLOW + "既存の範囲を再設定するには「/where update <名前>」を",
                                ChatColor.YELLOW + "範囲を再設定するには「/where axeclear」を実行してからもう一度右クリックしてください"
                        };

                        player.sendMessage(msg);
                    }
                }
            }
        }
    }
}
