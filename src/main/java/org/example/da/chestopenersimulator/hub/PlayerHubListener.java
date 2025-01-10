package org.example.da.chestopenersimulator.hub;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.chestopenersimulator.playerManager.Manager;

public class PlayerHubListener implements Listener {
    private JavaPlugin plugin;
    private TeleportToSpawn teleportToSpawn;
    private Manager manager;
    public PlayerHubListener(JavaPlugin plugin,Manager manager){
        this.plugin = plugin;
        this.teleportToSpawn = new TeleportToSpawn(plugin);
        this.manager = manager;
    }

    @EventHandler
    public void playerJoinEvents (PlayerJoinEvent e) {
        teleportToSpawn.joinPlayer(e.getPlayer());
        if(!(manager.isPlayerInManager(e.getPlayer()))){
            manager.addPlayer(e.getPlayer(), 0);
        }
    }
    @EventHandler
    public void spawnMobsEvent(EntitySpawnEvent e){
        e.setCancelled(false);
    }
}
