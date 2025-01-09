package org.example.da.chestopenersimulator.player.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.da.chestopenersimulator.player.manager.Manager;
import org.example.da.chestopenersimulator.player.manager.PlayerStatsManager;

public class GetPlayerStatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player pl = (Player) commandSender;
        if(Manager.isPlayerMapNoNull()) {
            for (PlayerStatsManager lsm : Manager.getPlayerMap().values()) {
                pl.sendMessage("Name: " + lsm.getPlayerName() + "\n     Money: " + lsm.getMoney());
            }
        }else {
            pl.sendMessage("Name: " + "..." + "\n     Money:" + "...");
            pl.sendMessage("Список пуст");
        }
        return true;
    }
}
