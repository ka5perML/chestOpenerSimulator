package org.example.da.chestopenersimulator.chestRoulette;


import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum ChestInformation {
    CHEST(new Location(Bukkit.getWorld("world"), 12,236,-62),"LOW"),
    CHEST1(new Location(Bukkit.getWorld("world"), 16,236,-62),"LOW"),
    CHEST2(new Location(Bukkit.getWorld("world"), 5,236,-62),"BIG"),
    CHEST3(new Location(Bukkit.getWorld("world"), 18,236,-53),"LOX"),
    CHEST4(new Location(Bukkit.getWorld("world"), 12,236,-53),"LOX");

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
