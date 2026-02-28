package me.pralay.relics;

import me.pralay.relics.commands.RelicCommand;
import me.pralay.relics.listeners.ArmorRelicListener;
import me.pralay.relics.listeners.MaceRelicListener;
import me.pralay.relics.listeners.TotemRelicListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class RelicsPlugin extends JavaPlugin {

    private static RelicsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        
        // Register command
        RelicCommand relicCommand = new RelicCommand(this);
        getCommand("relic").setExecutor(relicCommand);
        getCommand("relic").setTabCompleter(relicCommand);
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new ArmorRelicListener(this), this);
        getServer().getPluginManager().registerEvents(new TotemRelicListener(this), this);
        getServer().getPluginManager().registerEvents(new MaceRelicListener(this), this);
        
        getLogger().info("RelicsPlugin has been enabled successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RelicsPlugin has been disabled!");
    }

    public static RelicsPlugin getInstance() {
        return instance;
    }
}

