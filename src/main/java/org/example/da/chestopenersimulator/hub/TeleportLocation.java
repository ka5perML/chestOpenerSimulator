package org.example.da.chestopenersimulator.hub;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TeleportLocation {
    HUB_LOCATION(new Location(Bukkit.getWorld("world"),2.500,235.500,-51.500,240,0)),
    FAKE_LOCATION(new Location(Bukkit.getWorld("world"),-210.500,10000,-69.500,90,0));

    Location location;
}
