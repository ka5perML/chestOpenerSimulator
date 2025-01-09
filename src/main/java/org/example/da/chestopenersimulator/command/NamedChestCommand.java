package org.example.da.chestopenersimulator.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class NamedChestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission("permission.admin")) {
            Player player = (Player) commandSender;
            if (strings.length != 1) {
                player.sendMessage("Использование: /spawnarm <имя>");
                return true;
            }
            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().clone().add(0, -0.5, 0), EntityType.ARMOR_STAND);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setVisible(false);
            armorStand.setCustomName(stringConverter(strings[0]));
            armorStand.setCustomNameVisible(true);
            return true;
        }

        return false;
    }

    private static String stringConverter(String string) {
        String result = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == string.toUpperCase().charAt(i)) {
                result += " " + String.valueOf(string.charAt(i)).toUpperCase();
            } else
                result += String.valueOf(string.charAt(i)).toUpperCase();
        }
        return ChatColor.AQUA + (ChatColor.BOLD + result);
    }
}
