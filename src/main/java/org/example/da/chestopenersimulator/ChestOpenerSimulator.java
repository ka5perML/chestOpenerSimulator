package org.example.da.chestopenersimulator;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.example.da.chestopenersimulator.command.DeleteNameCommand;
import org.example.da.chestopenersimulator.command.HidePlayerCommand;
import org.example.da.chestopenersimulator.command.NamedChestCommand;
import org.example.da.chestopenersimulator.chestRoulette.RouletteSystem;
import org.example.da.chestopenersimulator.hub.PlayerInHubZone;
import org.example.da.chestopenersimulator.hub.PlayerJoinListener;
import org.example.da.chestopenersimulator.hub.TeleportToSpawn;
import org.example.da.chestopenersimulator.player.command.*;
import org.example.da.chestopenersimulator.player.hud.Hud;
import org.example.da.chestopenersimulator.loadAnsSaveData.SaveFileManager;
import org.example.da.chestopenersimulator.player.manager.PlayerListener;
import org.example.da.chestopenersimulator.player.settings.PlayerSetting;
import org.example.da.chestopenersimulator.visisbleSystem.HideListener;
import org.example.da.chestopenersimulator.visisbleSystem.HideSystem;

public final class ChestOpenerSimulator extends JavaPlugin {

    private SaveFileManager saveFileManager;

    public void onEnable() {
        System.out.println("Online");
        saveFileManager = new SaveFileManager(this);
        TeleportToSpawn.playerOnlineList();
        HideSystem.allHidePlayersAndPutList();
        Hud.runnableUpdateBalanceScoreboard();
        //command
        getCommand("stats").setExecutor(new GetPlayerStatsCommand());
        getCommand("hub").setExecutor(new TeleportHubCommand());
        getCommand("team").setExecutor(new TeamCommand());
        getCommand("top").setExecutor(new TopCommand());
        getCommand("balance").setExecutor(new BalanceCommand());

        // Admin command
        getCommand("spawnArm").setExecutor(new NamedChestCommand());
        getCommand("deleteArm").setExecutor(new DeleteNameCommand());
        getCommand("hide").setExecutor(new HidePlayerCommand());

        //listener
        registerListener(
                new PlayerListener(),
                new PlayerJoinListener(),
                new RouletteSystem(),
                new HideListener(),
                new PlayerSetting(this),
                new PlayerInHubZone()
        );
    }

    @Override
    public void onDisable() {
        saveFileManager.saveData();
        System.out.println("Offline");
    }

    public void registerListener(Listener... listeners) {
        PluginManager pluginManager = getServer().getPluginManager();
        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }

    public static Plugin getPluginName() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("chestOpenerSimulator");
        if (plugin == null) {
            Bukkit.getLogger().severe("Don't found ChestOpenerSimulator");
            return null;
        }
        return plugin;
    }
}
