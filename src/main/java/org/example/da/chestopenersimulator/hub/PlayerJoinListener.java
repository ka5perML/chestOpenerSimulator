package org.example.da.chestopenersimulator.hub;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.da.chestopenersimulator.player.manager.Manager;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        TeleportToSpawn.joinPlayer(e.getPlayer());
        if (!(Manager.isPlayerInManager(e.getPlayer()))) {
            Manager.addPlayer(e.getPlayer(), 0);
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        e.setCancelled(false);
    }
}
