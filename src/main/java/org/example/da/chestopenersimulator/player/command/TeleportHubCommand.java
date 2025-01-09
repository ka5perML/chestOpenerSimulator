package org.example.da.chestopenersimulator.player.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.da.chestopenersimulator.hub.TeleportToSpawn;

public class TeleportHubCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player){
            TeleportToSpawn.teleportPlayerSpawn((Player) commandSender);
            return true;
        }
        return true;
    }
}
