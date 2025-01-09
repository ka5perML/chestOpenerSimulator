package org.example.da.chestopenersimulator.player.manager;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class PlayerChangeBalance {
    public static void givedPRizes(Player player, long money){
        if(checkPlayer(player)) {
            Manager.updateBalance(player, getMoney(Manager.getPlayerMap(),player) + money);
        }else{
            Manager.addPlayer(player,money);
        }
    }
    public static String buyPrizes(Player player,int price){
        if(checkPlayer(player)) {
            if(getMoney(Manager.getPlayerMap(),player) - price >= 0) {
                Manager.updateBalance(player, getMoney(Manager.getPlayerMap(), player) - price);
                return "";
            }else
                return "";
        }
        return null;
    }
    public static boolean isHaveManeyBayPrizes(Player player,int price){
        if(checkPlayer(player)) {
            if(getMoney(Manager.getPlayerMap(),player) - price >= 0) {
                buyPrizes(player, price);
                return true;
            }else
                return false;
        }
        Manager.addPlayer(player,0);
        return false;
    }
    private static boolean checkPlayer(Player player){
        if(Manager.getPlayerMap().containsKey(player.getUniqueId())){
            return true;
        }
        return false;
    }
    private static long getMoney(Map<UUID,PlayerStatsManager> maps, Player player){
        return maps.get(player.getUniqueId()).getMoney();
    }
}
