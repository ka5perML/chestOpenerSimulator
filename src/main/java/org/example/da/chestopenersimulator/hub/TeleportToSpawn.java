package org.example.da.chestopenersimulator.hub;

import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.chestopenersimulator.ChestOpenerSimulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;

import static org.bukkit.Sound.*;

public class TeleportToSpawn {
    @Getter
    private static final Location hubLucation = new Location(Bukkit.getWorld("world"),-123.500,84.500,-189.500,90,0);
    @Getter
    private static final Location fakeHubLucation = new Location(Bukkit.getWorld("world"),-210.500,10000,-69.500,90,0);
    @Getter
    private static boolean isAliveExecute = false;

    public static void teleportPlayerSpawn(Player pl){
        pl.playSound(hubLucation, BLOCK_NOTE_BASS,10,1);
        pl.teleport(hubLucation);
    }
    public static void teleportAllPlayer(Collection<? extends Player> players){
        for (Player player : players){
            teleportPlayerSpawn(player);
        }
    }
    public static void joinPlayer(Player pl){
        taskLoader(pl);

    }
    public static String taskLoader(Player player){
        String[] loadList = {"Загрузка.","Загрузка..", "Загрузка..."};
        player.teleport(fakeHubLucation);
        new BukkitRunnable() {
            int ticked = 0;
            @SneakyThrows
            @Override
            public void run() {
                player.sendTitle(ChatColor.RED + loadList[ticked % 3], "", 1, 100, 1);
                if(ticked >= 5){
                    player.setFallDistance(0);
                    player.playSound(hubLucation, BLOCK_NOTE_BASS, 10, 1);
                    player.sendTitle(ChatColor.GREEN + (ChatColor.BOLD + "Case Simulator"), ChatColor.GREEN + "Приятной игры", 10, 20, 10);
                    player.teleport(hubLucation);
                    this.cancel();
                    return;
                }
                ticked++;
            }
        }.runTaskTimer(ChestOpenerSimulator.getPluginName(),0,20);
        return "";
    }
    public static void playerOnlineList(){
        for(Player player : Bukkit.getOnlinePlayers()){
            joinPlayer(player);
        }
    }
}
