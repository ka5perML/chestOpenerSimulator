package org.example.da.chestopenersimulator.playerCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.da.chestopenersimulator.playerManager.Manager;
import org.example.da.chestopenersimulator.playerManager.PlayerStatsManager;

import java.util.ArrayList;

public class TopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            return true;
        }
        Player player = (Player) commandSender;
        ArrayList<PlayerStatsManager> playerList = (ArrayList<PlayerStatsManager>) Manager.getTopPlayers(10);
        String topMessage = "  Лист Лидеров  \n";
        for(int i = 0; i < 50 ; i++){
            try {
                topMessage += (i + 1) + ". " + playerList.get(i).getPlayerName() + " " + playerList.get(i).getMoney() + "\n";
            }catch (IndexOutOfBoundsException e){
            }
        }
        player.sendMessage(ChatColor.YELLOW + (ChatColor.BOLD + topMessage));
        return true;
    }
}
