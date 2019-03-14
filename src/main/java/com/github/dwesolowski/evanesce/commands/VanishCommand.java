package com.github.dwesolowski.evanesce.commands;

import com.github.dwesolowski.evanesce.Evanesce;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class VanishCommand implements CommandExecutor {

    private final Evanesce plugin;

    public VanishCommand(Evanesce plugin) {
        this.plugin = plugin;
    }

    private ArrayList<Player> vanished = new ArrayList<org.bukkit.entity.Player>();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                sender.sendMessage(ChatColor.RED + "Usage: /<command>");
                return false;
            }

            if (player.hasPermission("evanesce.vanish")) {
                if (!this.vanished.contains(player)) {
                    for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                        all.hidePlayer(plugin, player);
                    }
                    this.vanished.add(player);
                    player.sendMessage(ChatColor.GREEN + "You have vanished from other players!");
                    return true;
                }
                for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                    all.showPlayer(plugin, player);
                }
                this.vanished.remove(player);
                player.sendMessage(ChatColor.GREEN + "You are now visible to other players!");
                return true;

            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
                return true;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Command not allowed in console, must be used in-game only!");
            return true;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (Player player : this.vanished) {
            event.getPlayer().hidePlayer(plugin, player);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        this.vanished.remove(event.getPlayer());
    }
}