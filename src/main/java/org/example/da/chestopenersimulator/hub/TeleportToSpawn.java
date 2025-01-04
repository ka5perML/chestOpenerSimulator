package org.example.da.chestopenersimulator.hub;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        executor1.submit(taskLoader(pl));
        executor.submit(() -> {
            pl.teleport(fakeHubLucation);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Error " + e.getMessage());
            }
            pl.setFallDistance(0);
            pl.playSound(hubLucation, BLOCK_NOTE_BASS,10,1);
            pl.sendTitle(ChatColor.GREEN + (ChatColor.BOLD +"Case Simulator"),ChatColor.GREEN +"Приятной игры",10,20,10);
            pl.teleport(hubLucation);

            Thread.currentThread().interrupt();
        });
        executor1.shutdown();
        executor.shutdown();
    }
    public static Runnable taskLoader(Player... players){
        Runnable task = () -> {
            try {
                for (int i = 0; i < players.length; i++) {
                    for(Player player : players) {
                        player.sendTitle(ChatColor.RED + (ChatColor.BOLD + "Загрузка."), "", 1, 100, 1);
                    }
                    Thread.sleep(800);
                    for(Player player : players) {
                        player.sendTitle(ChatColor.RED + (ChatColor.BOLD + "Загрузка.."), "", 1, 100, 1);
                    }
                    Thread.sleep(800);
                    for(Player player : players) {
                        player.sendTitle(ChatColor.RED + (ChatColor.BOLD + "Загрузка..."), "", 1, 100, 1);
                    }
                    Thread.sleep(800);
                    for(Player player : players) {
                        player.sendTitle(ChatColor.RED + (ChatColor.BOLD + "Загрузка."), "", 1, 100, 1);
                    }
                    Thread.sleep(800);
                    for(Player player : players) {
                        player.sendTitle(ChatColor.RED + (ChatColor.BOLD + "Загрузка.."), "", 1, 100, 1);
                    }
                    Thread.sleep(800);
                    for(Player player : players) {
                        player.sendTitle(ChatColor.RED + (ChatColor.BOLD + "Загрузка..."), "", 1, 100, 1);
                    }
                    Thread.sleep(800);
                }
            }catch(InterruptedException e){
                System.out.println("Error " + e.getMessage());
            }

        };
        return task;
    }
    public static void playerOnlineList(){
        for(Player player : Bukkit.getOnlinePlayers()){
            joinPlayer(player);
        }
    }
}
