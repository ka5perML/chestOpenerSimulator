package org.example.da.chestopenersimulator.playerManager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Manager {
    private static Map<UUID, PlayerStatsManager> playerMap = new HashMap<>();

    public static void addPlayer(Player pl, long money){
        UUID playerUUID = pl.getUniqueId();
        if(!playerMap.containsKey(playerUUID)){
            playerMap.put(playerUUID, new PlayerStatsManager(pl.getName(),playerUUID,money));
            System.out.println("New users " + pl.getName());
        }else
            System.out.println("User have " + pl.getDisplayName());
    }
    public static void loadPlayerMap(PlayerStatsManager psm){
        playerMap.put(psm.getPlayerUUID() ,new PlayerStatsManager(psm.getPlayerName(), psm.getPlayerUUID(), psm.getMoney()));
    }
    public void removePlayer(Player pl){
        UUID playerUUID = pl.getUniqueId();
        if(!playerMap.containsKey(playerUUID)){
            playerMap.remove(playerUUID);
            System.out.println("Удаленный пользователь " + pl.getPlayer());
        }
    }
    public PlayerStatsManager getPlayerInfo(Player pl){
        return playerMap.get(pl.getUniqueId());
    }
    public static boolean isPlayerInManager(Player pl){
        return playerMap.containsKey(pl.getUniqueId());
    }
    public int getPlayerCount(){
        return playerMap.size();
    }
    public static boolean isPlayerMapNoNull(){
        return playerMap.size() > 0;
    }

    public static Map<UUID, PlayerStatsManager> getPlayerMap() {
        return playerMap;
    }
    public static void updateBalance(Player pl,long money) {
        playerMap.put(pl.getUniqueId(), new PlayerStatsManager(pl.getDisplayName(), pl.getUniqueId(),money));
    }

    public static void setPlayerMap(Map<UUID, PlayerStatsManager> playerMap) {
        Manager.playerMap = playerMap;
    }
}
