package org.example.da.chestopenersimulator.hub;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void playerJoinEvents (PlayerJoinEvent e) {
        if (!TeleportToSpawn.isAliveExecute()){
            TeleportToSpawn.joinPlayer(e.getPlayer());
        }
    }
    @EventHandler
    public void spawnMobsEvent(EntitySpawnEvent e){
        e.setCancelled(false);
    }
}
