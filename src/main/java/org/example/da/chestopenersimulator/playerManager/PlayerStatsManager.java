package org.example.da.chestopenersimulator.playerManager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatsManager {
    private String playerName;
    private UUID playerUUID;
    private long money;
}
