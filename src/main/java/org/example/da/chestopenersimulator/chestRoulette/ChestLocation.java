package org.example.da.chestopenersimulator.chestRoulette;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;

public class ChestLocation {
    public static boolean smallChestLocation(Location chestLocation){
        ArrayList<Location> chestLocations = new ArrayList<>();
        chestLocations.add(new Location(Bukkit.getWorld("world"), -132,84,-194));
        chestLocations.add(new Location(Bukkit.getWorld("world"), -132,84,-190));
        chestLocations.add(new Location(Bukkit.getWorld("world"), -132,84,-186));
        for (Location location: chestLocations){
            if(chestLocation.equals(location)){
                return  true;
            }
        }
        return false;
    }
    public static boolean bigChestLocation(Location chestLocation){
        ArrayList<Location> chestLocations = new ArrayList<>();
        chestLocations.add(new Location(Bukkit.getWorld("world"), -119,85,-190));
        for (Location location: chestLocations){
            if(chestLocation.equals(location)){
                return  true;
            }
        }
        return false;
    }
}
