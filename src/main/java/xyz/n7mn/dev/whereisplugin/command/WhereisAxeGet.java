package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

class WhereisAxeGet extends CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

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
}
