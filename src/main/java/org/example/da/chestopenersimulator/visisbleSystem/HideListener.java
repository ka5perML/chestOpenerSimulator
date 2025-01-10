package org.example.da.chestopenersimulator.visisbleSystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HideListener implements Listener {
    private HideSystem hideSystem;
    public HideListener(HideSystem hideSystem){
        this.hideSystem = hideSystem;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        hideSystem.addHidePlayersList(player);
    }
    @EventHandler
    public void onLeaveJoin(PlayerQuitEvent e){
        Player player = e.getPlayer();
        hideSystem.leaveAllTeam(player);
    }
}
