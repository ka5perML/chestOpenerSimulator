package org.example.da.chestopenersimulator.loadAnsSaveData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.da.chestopenersimulator.playerManager.Manager;
import org.example.da.chestopenersimulator.playerManager.PlayerStatsManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoadPlayerInfo {
    static Manager manager = new Manager();
    public static int loadPlayerInfo(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<PlayerStatsManager> players = mapper.readValue(new File("playerData.json"), new TypeReference<List<PlayerStatsManager>>(){});
            if(players.size() == 0) return 0;
            for (PlayerStatsManager psm : players){
                manager.loadPlayerMap(psm);
            }
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }
        return 1;
    }
}

