package org.example.da.chestopenersimulator.chestRoulette;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public enum ChestInformation {
    CHEST( 12,236,-62,"LOW"),
    CHEST1(16,236,-62,"LOW"),
    CHEST2(5,236,-62,"BIG"),
    CHEST3(18,236,-53,"LOX"),
    CHEST4( 12,236,-53,"LOX");

    private Location location;
    private double x,y,z;
    private String information;
    ChestInformation(double x, double y, double z, String information){
        this.x= x;
        this.y = y;
        this.z = z;
        this.information = information;
    }

    public Location getLocation() {
        World world = Bukkit.getWorld("world");
        location = new Location(world, x, y, z);
        return  location;
    }

    public String getInformation() {
        return information;
    }
}
