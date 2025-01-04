package org.example.da.chestopenersimulator;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import org.example.da.chestopenersimulator.adminCommand.DeleteNameCommand;
import org.example.da.chestopenersimulator.adminCommand.HidePlayerCommand;
import org.example.da.chestopenersimulator.adminCommand.NamedChestCommand;
import org.example.da.chestopenersimulator.chestRoulette.RouletteSystem;
import org.example.da.chestopenersimulator.hub.PlayerJoinListener;
import org.example.da.chestopenersimulator.hub.TeleportToSpawn;
import org.example.da.chestopenersimulator.playerCommands.GetPlayerStatsCommand;
import org.example.da.chestopenersimulator.playerCommands.TeamCommand;
import org.example.da.chestopenersimulator.playerCommands.TeleportHubCommand;
import org.example.da.chestopenersimulator.playerManager.PlayerListener;
import org.example.da.chestopenersimulator.visisbleSystem.HideListener;
import org.example.da.chestopenersimulator.visisbleSystem.HideSystem;

public final class ChestOpenerSimulator extends JavaPlugin {

    public void onEnable() {
        System.out.println("Online");

        TeleportToSpawn.playerOnlineList();
        HideSystem.allHidePlayersAndPutList();

        //command
        getCommand("stats").setExecutor(new GetPlayerStatsCommand());
        getCommand("hub").setExecutor(new TeleportHubCommand());
        getCommand("team").setExecutor(new TeamCommand());

        // Admin command
        getCommand("spawnArm").setExecutor(new NamedChestCommand());
        getCommand("deleteArm").setExecutor(new DeleteNameCommand());

        //listener
        listenerLoader(
                new PlayerListener(),
                new PlayerJoinListener(),
                new RouletteSystem(),
                new HideListener()
        );
    }

    @Override
    public void onDisable() {
        System.out.println("Offline");
    }
    public void listenerLoader(Listener... listeners){
        PluginManager pluginManager = getServer().getPluginManager();
        for(Listener listener : listeners){
            pluginManager.registerEvents(listener,this);
        }
    }
}
