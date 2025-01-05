package org.example.da.chestopenersimulator.hub;


import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum TeleportLocation {
    HUB_LOCATION(new Location(Bukkit.getWorld("world"),2.500,235.500,-51.500,240,0)),
    FAKE_LOCATION(new Location(Bukkit.getWorld("world"),-210.500,10000,-69.500,90,0));

    private final Location location;
    TeleportLocation(Location loc){
        this.location = loc;
    }

    public Location getLocation() {
        return location;
    }
}
