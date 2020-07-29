package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

public class CommandMain implements CommandExecutor {

    WhereIsPlugin plugin;
    Player p = null;

    public CommandMain(WhereIsPlugin p){
        plugin = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        try {
            if (args.length >= 0){

                if (sender instanceof Player){
                    p = (Player)sender;
                }

                // /where
                if (args.length == 2 && p == null && !args[0].equals("add") && !args[0].equals("del") && !args[0].equals("update") && !args[0].equals("help") && !args[0].equals("system") && !args[0].equals("admin") && !args[0].equals("import")){
                    return new CommandWhere(plugin, args, p).run();
                }

                if (args.length == 0){
                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                        if (p != null){
                            if (!p.hasPermission("whereis.check")){
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }
                    return new CommandWhere(plugin, args, p).run();
                }

                // /where help
                if (args.length > 0 && args[0].equals("help")){
                    return new CommandHelp(p).run();
                }

                // /where add
                if (args.length > 0 && args[0].equals("add")){
                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                        if (p != null){
                            if (!p.hasPermission("whereis.add")){
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }
                    return new CommandAdd(plugin, args, p).run();
                }

                // /where update
                if (args.length == 3 && args[0].equals("update")){
                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                        if (p != null){
                            if (!p.hasPermission("whereis.update")){
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }
                    return new CommandUpdate(plugin, args, p).run();
                }

                // /where del
                if (args.length == 2 && args[0].equals("del")){
                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                        if (p != null){
                            if (!p.hasPermission("whereis.del")){
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }
                    return new CommandDelete(plugin, args, p).run();
                }

                // /where system
                if (args.length > 0 && args[0].equals("system")){
                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                        if (p != null){
                            if (!p.hasPermission("whereis.system")){
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }

                    if (p != null && !p.isOp()){
                        p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                        return true;
                    }

                    return new CommandSystem(p).run();
                }

                // /where admin
                if (args.length >= 2 && args[0].equals("admin")){
                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                        if (p != null){
                            if (!p.hasPermission("whereis.admin")){
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }

                    if (p != null && !p.isOp()){
                        p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                        return true;
                    }
                    return new CommandAdmin(plugin, args, p).run();
                }

                // /where import
                if (args.length > 0 && args[0].equals("import")){
                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                        if (p != null){
                            if (!p.hasPermission("whereis.admin")){
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }

                    if (p != null && !p.isOp()){
                        p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                        return true;
                    }

                    return new CommandImport(plugin, args, p).run();
                }
            }

            if (p != null){
                p.sendMessage(ChatColor.RED + new MessageList().getCommandSyntaxError());
            } else {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED +  new MessageList().getCommandSyntaxError());
            }
            return true;

        } catch (Exception e) {

            if (p != null){
                p.sendMessage(ChatColor.RED + "Plugin Error : " + e.getMessage());
            } else {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Plugin Error : " + e.getMessage());
            }

            plugin.getLogger().info("PluginError : " + e.getMessage() );
            e.fillInStackTrace();
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);

            return true;
        }
    }
}
