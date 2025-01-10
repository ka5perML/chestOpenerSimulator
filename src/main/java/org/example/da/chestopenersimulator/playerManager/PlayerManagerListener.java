package org.example.da.chestopenersimulator.playerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerManagerListener implements Listener {
    private Manager manager;
    public PlayerManagerListener(Manager manager){
        this.manager = manager;
    }
    @EventHandler
    public void playerJoinEvents (PlayerJoinEvent e) {
        e.setJoinMessage(null);
        manager.addPlayer(e.getPlayer(), 0);
    }
    @EventHandler
    public void playerExitEvents(PlayerQuitEvent e){
        e.setQuitMessage(null);
    }
}
