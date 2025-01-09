package org.example.da.chestopenersimulator.player.settings;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerSetting implements Listener {

    private JavaPlugin plugin;

    public PlayerSetting(JavaPlugin plugin){
        this.plugin = plugin;

        startFullHealthAndFoodTask();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.getWorld().setTime(1000);
        setWeather();
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    private void setWeather() {
        World world = Bukkit.getWorld("world");
        world.setThundering(false);
        world.setStorm(false);
    }

    private void startFullHealthAndFoodTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    setFullHealthAndFood(player);
                    player.getWorld().setTime(1000);
                }
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }

    private void setFullHealthAndFood(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(20);
    }
}
