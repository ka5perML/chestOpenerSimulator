package org.example.da.chestopenersimulator.chestRoulette;

import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.example.da.chestopenersimulator.playerManager.PlayerChangeBalance;

import java.util.*;

public class Prize {
    private PlayerChangeBalance playerChangeBalance;
    public Prize(PlayerChangeBalance playerChangeBalance){
        this.playerChangeBalance = playerChangeBalance;
    }
    @SneakyThrows
    public String chancePrize(String caseName) {
        List<String> lowPrize = Arrays.asList("20000","8000","5000","2500","1000","500","100");
        List<String> bigPrize = Arrays.asList("100000","30000","25000","15000","10000","5000");
        List<String> loxPrize = Arrays.asList("1000","800","600","400","250","100","50");

        List<Double> сhanсe = Arrays.asList(1.0, 4.0, 10.0, 10.0, 15.0, 20.0, 40.0);

        Map<String, List<String>> prizeMap = new HashMap<>();
        prizeMap.put("LOW", lowPrize);
        prizeMap.put("BIG", bigPrize);
        prizeMap.put("LOX", loxPrize);

        if (!prizeMap.containsKey(caseName)) {
            throw new IllegalArgumentException("Invalid case name: " + caseName);
        }
        Random random = new Random();
        double randomInt = random.nextDouble() * 100;
        double cumulative = 0.0;

        for (int i = 0; i < сhanсe.size(); i++) {
            cumulative += сhanсe.get(i);
            if (randomInt <= cumulative) {
                return prizeMap.get(caseName).get(i);
            }
        }
        return null;
    }
    public void givePlayerPrize(Player player, int money){
        playerChangeBalance.givedPrizes(player, money);
    }
}
