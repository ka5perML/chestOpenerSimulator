package org.example.da.chestopenersimulator.playerManager;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class PlayerChangeBalance {
    private Manager manager;
    public PlayerChangeBalance(Manager manager){
        this.manager = manager;
    }
    public void givedPrizes(Player player, long money){
        if(checkPlayer(player)) {
            manager.updateBalance(player, getMoney(manager.getPlayerMap(),player) + money);
        }else{
            manager.addPlayer(player,money);
        }
    }
    public String buyPrizes(Player player,int price){
        if(checkPlayer(player)) {
            if(getMoney(manager.getPlayerMap(),player) - price >= 0) {
                manager.updateBalance(player, getMoney(manager.getPlayerMap(), player) - price);
                return "";
            }
        }
        return null;
    }
    public boolean isHaveMoneyBayPrizes(Player player,int price){
        if(checkPlayer(player)) {
            if(getMoney(manager.getPlayerMap(),player) - price >= 0) {
                buyPrizes(player, price);
                return true;
            }else
                return false;
        }
        manager.addPlayer(player,0);
        return false;
    }
    private boolean checkPlayer(Player player){
        if(manager.getPlayerMap().containsKey(player.getUniqueId())){
            return true;
        }
        return false;
    }
    private long getMoney(Map<UUID,PlayerStatsManager> maps, Player player){
        return maps.get(player.getUniqueId()).getMoney();
    }
}
