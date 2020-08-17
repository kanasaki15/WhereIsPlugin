package xyz.n7mn.dev.whereisplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.api.WhereIsData;
import xyz.n7mn.dev.whereisplugin.event.WhereisExecuteCommandEvent;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

public class CommandMain implements CommandExecutor {

    WhereIsPlugin plugin;
    Player p = null;
    WhereIsData WhereIsAPI;

    public CommandMain(WhereIsPlugin p, WhereIsData api){
        this.plugin = p;
        this.WhereIsAPI = api;
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
                    WhereisExecuteCommandEvent event = new WhereisExecuteCommandEvent("Check", sender);

                    plugin.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()){
                        sender.sendMessage(event.getMessage());
                        return true;
                    }

                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null) {
                        if (p != null) {
                            if (!p.hasPermission("whereis.check")) {
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }
                    return new CommandWhere(plugin, args, p, WhereIsAPI).run();
                }

                if (args.length == 0){

                    WhereisExecuteCommandEvent event = new WhereisExecuteCommandEvent("Check", sender);
                    plugin.getServer().getPluginManager().callEvent(event);

                    if (event.isCancelled()){
                        sender.sendMessage(event.getMessage());
                        return true;
                    }

                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                        if (p != null){
                            if (!p.hasPermission("whereis.check")){
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }
                    return new CommandWhere(plugin, args, p, WhereIsAPI).run();
                }

                // /where help
                if (args.length > 0 && args[0].equals("help")){
                    WhereisExecuteCommandEvent event = new WhereisExecuteCommandEvent("Help", sender);

                    plugin.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()){
                        sender.sendMessage(event.getMessage());
                        return true;
                    }

                    return new CommandHelp(p).run();
                }

                // /where add
                if (args.length > 0 && args[0].equals("add")){
                    WhereisExecuteCommandEvent event = new WhereisExecuteCommandEvent("Add", sender);

                    plugin.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()){
                        sender.sendMessage(event.getMessage());
                        return true;
                    }

                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                        if (p != null){
                            if (!p.hasPermission("whereis.add")){
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }
                    return new CommandAdd(plugin, args, p, WhereIsAPI).run();
                }

                // /where update
                if (args.length >= 3 && args[0].equals("update")){
                    WhereisExecuteCommandEvent event = new WhereisExecuteCommandEvent("Update", sender);

                    plugin.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()){
                        sender.sendMessage(event.getMessage());
                        return true;
                    }

                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                        if (p != null){
                            if (!p.hasPermission("whereis.update")){
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }
                    return new CommandUpdate(plugin, args, p, WhereIsAPI).run();
                }

                // /where del
                if (args.length == 2 && args[0].equals("del")){
                    WhereisExecuteCommandEvent event = new WhereisExecuteCommandEvent("Del", sender);

                    plugin.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()){
                        sender.sendMessage(event.getMessage());
                        return true;
                    }

                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                        if (p != null){
                            if (!p.hasPermission("whereis.del")){
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }
                    return new CommandDelete(plugin, args, p, WhereIsAPI).run();
                }

                // /where system
                if (args.length >= 0 && args[0].equals("system")){
                    WhereisExecuteCommandEvent event = new WhereisExecuteCommandEvent("System", sender);

                    plugin.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()){
                        sender.sendMessage(event.getMessage());
                        return true;
                    }

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

                    return new CommandSystem(p, WhereIsAPI).run();
                }

                // /where admin
                if (args.length >= 2 && args[0].equals("admin")){
                    WhereisExecuteCommandEvent event = new WhereisExecuteCommandEvent("Admin", sender);

                    plugin.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()){
                        sender.sendMessage(event.getMessage());
                        return true;
                    }


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
                    return new CommandAdmin(plugin, args, p, WhereIsAPI).run();
                }

                // /where import
                if (args.length > 0 && args[0].equals("import")){
                    WhereisExecuteCommandEvent event = new WhereisExecuteCommandEvent("Import", sender);

                    plugin.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()){
                        sender.sendMessage(event.getMessage());
                        return true;
                    }


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

                    return new CommandImport(plugin, args, p, WhereIsAPI).run();
                }

                // /where list
                if (args.length > 0 && args[0].equals("list")){
                    WhereisExecuteCommandEvent event = new WhereisExecuteCommandEvent("UserList", sender);

                    plugin.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()){
                        sender.sendMessage(event.getMessage());
                        return true;
                    }

                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                        if (p != null){
                            if (!p.hasPermission("whereis.list")){
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }

                    return new CommandList(plugin, p, WhereIsAPI).run();
                }

                // /where dynmap
                if (args.length > 0 && args[0].equals("dynmap")){
                    WhereisExecuteCommandEvent event = new WhereisExecuteCommandEvent("dynmap", sender);

                    plugin.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()){
                        sender.sendMessage(event.getMessage());
                        return true;
                    }

                    if (plugin.getServer().getPluginManager().getPlugin("LuckPerm") != null){
                        if (p != null){
                            if (!p.hasPermission("whereis.dynmap")){
                                p.sendMessage(ChatColor.RED + new MessageList().getPermErrorMessage());
                                return true;
                            }
                        }
                    }

                    return new CommandMarker(plugin, args, p, WhereIsAPI).run();
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
            e.printStackTrace();
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);

            return true;
        }
    }
}
