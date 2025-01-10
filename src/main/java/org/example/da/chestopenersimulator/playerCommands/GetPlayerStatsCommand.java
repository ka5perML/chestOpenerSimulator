package org.example.da.chestopenersimulator.playerCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.da.chestopenersimulator.playerManager.Manager;
import org.example.da.chestopenersimulator.playerManager.PlayerStatsManager;

public class GetPlayerStatsCommand implements CommandExecutor {
    private Manager manager;
    public GetPlayerStatsCommand(Manager manager){
        this.manager = manager;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player pl = (Player) commandSender;
        if(manager.isPlayerMapNoNull()) {
            for (PlayerStatsManager lsm : manager.getPlayerMap().values()) {
                pl.sendMessage("Name: " + lsm.getPlayerName() + "\n     Money: " + lsm.getMoney());
            }
        }else {
            pl.sendMessage("Name: " + "..." + "\n     Money:" + "...");
            pl.sendMessage("Список пуст");
        }
        return true;
    }
}
