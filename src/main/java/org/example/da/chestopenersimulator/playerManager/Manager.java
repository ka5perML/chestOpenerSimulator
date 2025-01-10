package org.example.da.chestopenersimulator.playerManager;

import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Manager {
    private Map<UUID, PlayerStatsManager> playerMap = new HashMap<>();

    public void addPlayer(Player pl, long money){
        UUID playerUUID = pl.getUniqueId();
        if(!playerMap.containsKey(playerUUID)){
            playerMap.put(playerUUID, new PlayerStatsManager(pl.getName(),playerUUID,money));
            System.out.println("New users " + pl.getName());
        }else
            System.out.println("User have " + pl.getDisplayName());
    }
    public void loadPlayerMap(PlayerStatsManager psm){
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
    public boolean isPlayerInManager(Player pl){
        return playerMap.containsKey(pl.getUniqueId());
    }
    public int getPlayerCount(){
        return playerMap.size();
    }
    public boolean isPlayerMapNoNull(){
        return playerMap.size() > 0;
    }

    public Map<UUID, PlayerStatsManager> getPlayerMap() {
        return playerMap;
    }
    public void updateBalance(Player pl,long money) {
        playerMap.put(pl.getUniqueId(), new PlayerStatsManager(pl.getDisplayName(), pl.getUniqueId(),money));
    }

    public void setPlayerMap(Map<UUID, PlayerStatsManager> playerMap) {
        this.playerMap = playerMap;
    }
    public List<PlayerStatsManager> getTopPlayers(int limit) {
        return playerMap.values().stream()
                .sorted(Comparator.comparingInt(player -> (int) -player.getMoney()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
