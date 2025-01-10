package org.example.da.chestopenersimulator.chestRoulette;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.chestopenersimulator.ChestOpenerSimulator;
import org.example.da.chestopenersimulator.visisbleSystem.HideSystem;
import org.example.da.chestopenersimulator.visisbleSystem.VisibleTeamArmorStand;

import java.util.ArrayList;
import java.util.List;

public class RouletteAnimation {
    private final JavaPlugin plugin;
    private final Prize prize;
    private final ChestManager chestManager;
    private final double radius = 1.5;
    private final int numberOfBlocks = 16;
    private VisibleTeamArmorStand visibleTeamArmorStand;
    private HideSystem hideSystem;
    public RouletteAnimation(JavaPlugin plugin,ChestManager chestManager, Prize prize, HideSystem system){
        this.plugin = plugin;
        this.prize = prize;
        this.chestManager = chestManager;
        this.visibleTeamArmorStand = new VisibleTeamArmorStand();
        this.hideSystem = system;
    }
    public void startRoulette(Location location,Player player, String tipcl){
        startRouletteAnimation(location,player,tipcl);
        playRouletteSound(player);
        chestManager.addPlayerInList(player);
    }
    private void startRouletteAnimation(Location chestLocation, Player player, String num) {
        List<ArmorStand> stands = new ArrayList<>();
        stands.removeAll(stands);
        for (int i = 0; i < numberOfBlocks; i++) {
            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity
                    (animationTeleport(i,0.1,chestLocation,radius,numberOfBlocks), EntityType.ARMOR_STAND);
            visibleTeamArmorStand.hideArmorStand(armorStand, hideSystem.getTeamListPlayer(player));
            settingArmorStand(armorStand,num);
            stands.add(armorStand);
        }
        new BukkitRunnable() {
            double speed = 0.1;
            int ticksPassed = 0;

            @Override
            public void run() {
                if (ticksPassed >= 100) { // Конец
                    int won = wonPrize((ArrayList<ArmorStand>) stands);
                    player.sendMessage("§a§lВыйгрыш " + won);
                    stopRoulette((ArrayList<ArmorStand>) stands,player, won);
                    this.cancel();
                    return;
                }
                if (ticksPassed <= 80) {
                    for (int i = 0; i < stands.size(); i++) {
                        CraftArmorStand craftArmorStand = (CraftArmorStand) stands.get(i);
                        craftArmorStand.teleport(animationTeleport(i, speed, chestLocation, radius, numberOfBlocks));
                    }
                }
                speed += 2;
                Bukkit.getLogger().fine(String.valueOf(ticksPassed));
                ticksPassed += 1;
            }
        }.runTaskTimer(plugin,0,1);
    }
   private void playRouletteSound(Player player) {
        Location location = player.getLocation();

        new BukkitRunnable() {
            int ticket;
            @Override
            public void run() {
                if (ticket >= 80) {
                    cancel();
                    return;
                }
                player.playSound(location, Sound.BLOCK_NOTE_PLING, 0.01f, 1.0f);
                ticket += 2;
            }
        }.runTaskTimer(ChestOpenerSimulator.getPluginName(), 0, 2);
    }

    private Location animationTeleport(int i,double speed, Location chestLocation,double radius,int numberOfBlocks){
        double angle = speed * 0.05 + (i * (Math.PI * 2 / numberOfBlocks)); // Телепортирует блоки по кругу
        double x = chestLocation.getX() + 0.5 + radius * Math.cos(angle);
        double z = chestLocation.getZ() + 0.5 + radius * Math.sin(angle);
        double y = chestLocation.getY() + (0.2 * Math.sin(speed * 0.2)); // Летают вверх и вниз
        Location blockLocation = new Location(chestLocation.getWorld(), x, y, z);
        return blockLocation;
    }
    private void settingArmorStand(ArmorStand armorStand,String num){
        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setVisible(false);
        String prizeName = prize.chancePrize(num);
        armorStand.setCustomName(prizeName);
        armorStand.setCustomNameVisible(true);
    }
    private int wonPrize(ArrayList<ArmorStand> armorStands){
        ArmorStand armorStand = armorStands.get((armorStands.size() * 100) % armorStands.size());
        return Integer.parseInt(armorStand.getName());
    }
    private void stopRoulette(ArrayList<ArmorStand> armorStands, Player player, int priz) {
        armorStands.forEach(armorStand -> armorStand.remove());
        prize.givePlayerPrize(player, priz);
        chestManager.deletePlayerInList(player);
    }
}
