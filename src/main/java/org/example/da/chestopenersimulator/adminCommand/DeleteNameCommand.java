package org.example.da.chestopenersimulator.adminCommand;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public class DeleteNameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission("permission.admin")) {

            Player player = (Player) commandSender;
            if (strings.length > 2) {
                player.sendMessage("Использование: /deleteArm all");
                return true;
            }
            if(strings[0].equals("all")){
                Location playerLocation = player.getLocation();

                List<Entity> entities = (List<Entity>) player.getWorld().getNearbyEntities(playerLocation,10,10,10);

                for (Entity entity : entities) {
                    if (entity.getType() == EntityType.ARMOR_STAND) {
                        ArmorStand armorStand = (ArmorStand) entity;
                        armorStand.remove();
                    }
                }
                return true;
            } else if (strings[0].equals("solo")) {
                Location playerLocation = player.getLocation();

                List<Entity> entities = (List<Entity>) player.getWorld().getNearbyEntities(playerLocation, 0.5, 0.5, 0.5);

                for (Entity entity : entities) {
                    if (entity.getType() == EntityType.ARMOR_STAND) {
                        ArmorStand armorStand = (ArmorStand) entity;
                        armorStand.remove();
                    }
                }
                return true;
            }
        }
        return false;
    }
}
