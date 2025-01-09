package org.example.da.chestopenersimulator.visisbleSystem;

import lombok.*;
import org.bukkit.entity.Player;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamList {
    private Player ownerName;

    private Player firstTeam;
    private Player secondTeam;
}
