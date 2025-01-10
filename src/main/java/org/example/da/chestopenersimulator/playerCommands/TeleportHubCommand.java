package org.example.da.chestopenersimulator.playerCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.chestopenersimulator.hub.TeleportToSpawn;

public class TeleportHubCommand implements CommandExecutor {
    private TeleportToSpawn teleportToSpawn;
    public TeleportHubCommand(JavaPlugin plugin){
        this.teleportToSpawn = new TeleportToSpawn(plugin);
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player){
            teleportToSpawn.teleportPlayerSpawn((Player) commandSender);
            return true;
        }
        return true;
    }
}
