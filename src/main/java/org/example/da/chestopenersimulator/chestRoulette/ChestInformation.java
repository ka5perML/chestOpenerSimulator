package org.example.da.chestopenersimulator.chestRoulette;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ChestInformation {

    CHEST(new Location(Bukkit.getWorld("world"), 12,236,-62),"LOW"),
    CHEST1(new Location(Bukkit.getWorld("world"), 16,236,-62),"LOW"),
    CHEST2(new Location(Bukkit.getWorld("world"), 5,236,-62),"BIG"),
    CHEST3(new Location(Bukkit.getWorld("world"), 18,236,-53),"LOX"),
    CHEST4(new Location(Bukkit.getWorld("world"), 12,236,-53),"LOX");

    private Location location;
    private String information;
}
