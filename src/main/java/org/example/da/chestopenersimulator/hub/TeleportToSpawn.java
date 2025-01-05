package org.example.da.chestopenersimulator.hub;

import lombok.SneakyThrows;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.chestopenersimulator.ChestOpenerSimulator;

import java.util.Collection;

import static org.bukkit.Sound.*;

public class TeleportToSpawn {
    public static void teleportPlayerSpawn(Player pl){
        pl.playSound(TeleportLocation.HUB_LOCATION.getLocation(),  Sound.BLOCK_NOTE_PLING, 0.8f, 1.0f);
        pl.teleport(TeleportLocation.HUB_LOCATION.getLocation());
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
        player.teleport(TeleportLocation.FAKE_LOCATION.getLocation());
        new BukkitRunnable() {
            int ticked = 0;
            @SneakyThrows
            @Override
            public void run() {
                player.sendTitle(ChatColor.RED + (ChatColor.BOLD +  loadList[ticked % 3]), "", 1, 100, 1);
                if(ticked >= 5){
                    player.setFallDistance(0);
                    player.playSound(TeleportLocation.HUB_LOCATION.getLocation(),  Sound.BLOCK_NOTE_PLING, 0.8f, 2.0f);
                    player.sendTitle(ChatColor.GREEN + (ChatColor.BOLD + "Case Simulator"), ChatColor.GREEN + "Приятной игры", 10, 20, 10);
                    player.teleport(TeleportLocation.HUB_LOCATION.getLocation());
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
