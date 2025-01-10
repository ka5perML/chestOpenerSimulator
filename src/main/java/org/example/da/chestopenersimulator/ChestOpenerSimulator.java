package org.example.da.chestopenersimulator;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import org.example.da.chestopenersimulator.adminCommand.DeleteNameCommand;
import org.example.da.chestopenersimulator.adminCommand.HidePlayerCommand;
import org.example.da.chestopenersimulator.adminCommand.NamedChestCommand;
import org.example.da.chestopenersimulator.chestRoulette.RouletteListener;
import org.example.da.chestopenersimulator.hub.PlayerHubListener;
import org.example.da.chestopenersimulator.hudPlayer.Hud;
import org.example.da.chestopenersimulator.loadAnsSaveData.SaveFileManager;
import org.example.da.chestopenersimulator.playerCommands.*;
import org.example.da.chestopenersimulator.playerManager.Manager;
import org.example.da.chestopenersimulator.playerManager.PlayerManagerListener;
import org.example.da.chestopenersimulator.playerSetting.PlayerSetting;
import org.example.da.chestopenersimulator.visisbleSystem.HideListener;
import org.example.da.chestopenersimulator.visisbleSystem.HideSystem;

public final class ChestOpenerSimulator extends JavaPlugin {
    private SaveFileManager saveFileManager;
    private Manager manager = new Manager();
    private HideSystem hideSystem = new HideSystem();
    public void onEnable() {
        hideSystem = new HideSystem();
        System.out.println("Online");
        saveFileManager = new SaveFileManager(this, manager);
        hideSystem.allHidePlayersAndPutList();
        new Hud(manager);
        //command
        getCommand("stats").setExecutor(new GetPlayerStatsCommand(manager));
        getCommand("hub").setExecutor(new TeleportHubCommand(this));
        getCommand("team").setExecutor(new TeamCommand(hideSystem));
        getCommand("top").setExecutor(new TopCommand(manager));
        getCommand("balance").setExecutor(new BalanceCommand(manager));

        // Admin command
        getCommand("spawnArm").setExecutor(new NamedChestCommand());
        getCommand("deleteArm").setExecutor(new DeleteNameCommand());
        getCommand("hide").setExecutor(new HidePlayerCommand());

        //listener
        listenerLoader(
                new PlayerManagerListener(manager),
                new RouletteListener(this,manager,hideSystem),
                new HideListener(hideSystem),
                new PlayerSetting(this),
                new PlayerHubListener(this,manager)
        );
    }

    @Override
    public void onDisable() {
        saveFileManager.saveData();
        System.out.println("Offline");
    }
    public void listenerLoader(Listener... listeners){
        PluginManager pluginManager = getServer().getPluginManager();
        for(Listener listener : listeners){
            pluginManager.registerEvents(listener,this);
        }
    }
    public static Plugin getPluginName(){
        Plugin plugin = Bukkit.getPluginManager().getPlugin("chestOpenerSimulator");
        if (plugin == null) {
            Bukkit.getLogger().severe("Don't found ChestOpenerSimulator");
            return null;
        }
        return plugin;
    }
}
