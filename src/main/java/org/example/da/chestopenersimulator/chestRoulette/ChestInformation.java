package org.example.da.chestopenersimulator.chestRoulette;


import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum ChestInformation {
    CHEST(new Location(Bukkit.getWorld("world"), -132,84,-194),"LOW"),
    CHEST1(new Location(Bukkit.getWorld("world"), -132,84,-190),"LOW"),
    CHEST2(new Location(Bukkit.getWorld("world"), -132,84,-186),"LOW"),
    CHEST3(new Location(Bukkit.getWorld("world"), -119,85,-190),"BIG"),
    CHEST4(new Location(Bukkit.getWorld("world"), -125,84,-182),"LOX");

    private Location location;
    private String information;
    ChestInformation(Location lock, String information){
        this.location = lock;
        this.information = information;
    }

    public Location getLocation() {
        return location;
    }

    public String getInformation() {
        return information;
    }
}
