package org.example.da.chestopenersimulator.visisbleSystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HideListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        HideSystem.putHideNewPlayersList(player);
    }
    @EventHandler
    public void onLeaveJoin(PlayerQuitEvent e){
        Player player = e.getPlayer();
        HideSystem.leaveAllTeam(player);
    }
}
