package org.example.da.chestopenersimulator.playerCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.da.chestopenersimulator.playerManager.Manager;

public class BalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            return true;
        }
        Player player = (Player) commandSender;
        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD +"Ваш баланс " + Manager.getPlayerMap().get(player.getUniqueId()).getMoney()));
        return true;
    }
}
