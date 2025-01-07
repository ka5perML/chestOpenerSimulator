package org.example.da.chestopenersimulator.visisbleSystem;

import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class VisibleTeamArmorStand {
    public static void hideArmorStand(ArmorStand armorStand,TeamList tl){
        CraftArmorStand craftArmorStand = (CraftArmorStand) armorStand;
        EntityArmorStand armorStands = craftArmorStand.getHandle();
        PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(armorStands.getId());
        PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving(armorStands);
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if(player1 != tl.getTeam1()  && player1 != tl.getTeam2() && player1 != tl.getOwnerName()){
                ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(destroyPacket);
            }else
                ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(spawnPacket);
        }
    }
}
