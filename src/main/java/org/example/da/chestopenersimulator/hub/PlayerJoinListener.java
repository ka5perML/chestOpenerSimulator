package org.example.da.chestopenersimulator.hub;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.da.chestopenersimulator.playerManager.Manager;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void playerJoinEvents (PlayerJoinEvent e) {
        TeleportToSpawn.joinPlayer(e.getPlayer());
        if(!(Manager.isPlayerInManager(e.getPlayer()))){
            Manager.addPlayer(e.getPlayer(), 0);
        }
    }
    @EventHandler
    public void spawnMobsEvent(EntitySpawnEvent e){
        e.setCancelled(false);
    }
}
