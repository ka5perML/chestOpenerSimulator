package org.example.da.chestopenersimulator.chestRoulette;

import net.minecraft.server.v1_12_R1.ChatMessage;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.chestopenersimulator.playerManager.Manager;
import org.example.da.chestopenersimulator.playerManager.PlayerChangeBalance;
import org.example.da.chestopenersimulator.visisbleSystem.HideSystem;

public class RouletteListener implements Listener{
    private RouletteAnimation rouletteAnimation;
    private ChestManager chestManager;
    private final Prize prize;
    private PlayerChangeBalance playerChangeBalance;
    public RouletteListener(JavaPlugin plugin, Manager manager, HideSystem hideSystem){
        this.playerChangeBalance = new PlayerChangeBalance(manager);
        this.chestManager = new ChestManager(playerChangeBalance);
        this.prize = new Prize(playerChangeBalance);
        this.rouletteAnimation = new RouletteAnimation(plugin,chestManager,prize, hideSystem);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null || clickedBlock.getType() != Material.CHEST) return;

        Location chestLocation = clickedBlock.getLocation();
        String openCaseTipc = chestManager.infoCase(chestLocation); // тип кейса

        if (chestManager.getOpenPlayerList().contains(player)) {
            event.setCancelled(true);
            player.sendMessage("§c§lТы уже открываешь кейс");
            return;
        }
        if (!chestManager.isGoodOpen(chestLocation, player)) {
            event.setCancelled(true);
            chestManager.sendTitleMessage(player, new ChatMessage( "§c§lНе достаточно средств!"));
            return;
        }

        event.setCancelled(true);
        player.sendMessage("§a§lНачалось открытие!");
        rouletteAnimation.startRoulette(chestLocation, player, openCaseTipc);
    }
    @EventHandler
    public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        event.setCancelled(true);
    }

}
