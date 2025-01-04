package org.example.da.chestopenersimulator.playerCommands;

import net.minecraft.server.v1_12_R1.Packet;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.boss.CraftBossBar;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.example.da.chestopenersimulator.playerManager.Manager;
import org.example.da.chestopenersimulator.playerManager.PlayerStatsManager;

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
