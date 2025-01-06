package org.example.da.chestopenersimulator.hub;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerInHubZone implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();

        if (!isPlayerInZone(playerLocation)) {
            player.teleport(TeleportLocation.HUB_LOCATION.getLocation());
            player.sendMessage("Вы покинули зону!");
        }
    }

    private boolean isPlayerInZone(Location location) {
         double i = TeleportLocation.HUB_LOCATION.getLocation().distance(location);

        return i < 30 || i>100;
    }
}
