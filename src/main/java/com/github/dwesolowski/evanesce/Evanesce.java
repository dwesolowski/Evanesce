package com.github.dwesolowski.evanesce;

import com.github.dwesolowski.evanesce.commands.VanishCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Evanesce extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommands();
    }

    @Override
    public void onDisable() {
        Bukkit.getPluginManager().disablePlugin(this);
    }

    private void registerCommands() {
        getCommand("vanish").setExecutor(new VanishCommand(this));
    }
}
