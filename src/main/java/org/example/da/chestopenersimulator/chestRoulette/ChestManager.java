package org.example.da.chestopenersimulator.chestRoulette;

import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.example.da.chestopenersimulator.playerManager.PlayerChangeBalance;

import java.util.*;

public class ChestManager {
    @Getter
    private ArrayList<Player> openPlayerList;
    private final PlayerChangeBalance playerChangeBalance;
    public ChestManager(PlayerChangeBalance playerChangeBalance){
        this.playerChangeBalance = playerChangeBalance;
        this.openPlayerList = new ArrayList<>();
    }
    public void addPlayerInList(Player player){
        openPlayerList.add(player);
    }
    public void deletePlayerInList(Player player){
        openPlayerList.remove(player);
    }
    @SneakyThrows
    public String infoCase(Location chestLocation){
        return Arrays.stream(ChestInformation.values())
                .filter(chest -> chest.getLocation().equals(chestLocation))
                .findFirst().orElse(null).getInformation();
    }
    @SneakyThrows
    public boolean isGoodOpen(Location chestLocation, Player player){
        String chestType = infoCase(chestLocation);
        int cost = 0;
        switch (chestType) {
            case "LOW":
                cost = 1000;
                break;
            case "BIG":
                cost = 20000;
                break;
            case "LOX":
                cost = 0;
                break;
        }
        return playerChangeBalance.isHaveMoneyBayPrizes(player,cost);
    }
    public void sendTitleMessage(Player player, IChatBaseComponent iChatBaseComponent){
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, iChatBaseComponent);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutTitle);
    }
}
