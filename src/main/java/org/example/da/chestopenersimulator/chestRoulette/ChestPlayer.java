package org.example.da.chestopenersimulator.chestRoulette;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChestPlayer {
    private Player name;
    private boolean isChestStatus;
}
