package org.example.da.chestopenersimulator.player.manager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void playerJoinEvents (PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Manager.addPlayer(e.getPlayer(), 0);
    }
    @EventHandler
    public void playerExitEvents(PlayerQuitEvent e){
        e.setQuitMessage(null);
    }
}
