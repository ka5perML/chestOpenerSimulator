package org.example.da.chestopenersimulator.hub;

import lombok.SneakyThrows;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportToSpawn {
    private JavaPlugin plugin;
    public TeleportToSpawn(JavaPlugin plugin){
        this.plugin = plugin;
        playerOnlineList();
        playerZoneCheckTask();
    }
    public void teleportPlayerSpawn(Player pl){
        pl.playSound(TeleportLocation.HUB_LOCATION.getLocation(),  Sound.BLOCK_NOTE_PLING, 0.8f, 1.0f);
        pl.teleport(TeleportLocation.HUB_LOCATION.getLocation());
    }
    public void joinPlayer(Player pl){
        taskLoader(pl);
    }
    private void taskLoader(Player player){
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
        }.runTaskTimer(plugin,0,20);
    }
    private void playerOnlineList(){
        for(Player player : Bukkit.getOnlinePlayers()){
            joinPlayer(player);
        }
    }
    private void playerZoneCheckTask(){
        new BukkitRunnable() {
            @SneakyThrows
            @Override
            public void run() {
                for(Player player: Bukkit.getOnlinePlayers()){
                    if(!isPlayerInZone(player.getLocation())) player.teleport(TeleportLocation.HUB_LOCATION.getLocation());
                }
            }
        }.runTaskTimer(plugin,0,20);
    }
    private boolean isPlayerInZone(Location location) {
        double i = TeleportLocation.HUB_LOCATION.getLocation().distance(location);

        return i < 30 || i>100;
    }
}
