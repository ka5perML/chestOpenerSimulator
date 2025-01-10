package org.example.da.chestopenersimulator.playerCommands;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.example.da.chestopenersimulator.playerManager.Manager;

public class BalanceCommand implements CommandExecutor {
    private Manager manager;
    public BalanceCommand(Manager manager){
        this.manager = manager;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            return true;
        }
        Player player = (Player) commandSender;
        IChatBaseComponent text = new ChatMessage(ChatColor.GREEN + "Баланс: " + manager.getPlayerMap().get(player.getUniqueId()).getMoney());
        player.sendMessage(ChatColor.GREEN + text.getText());
        checkBalance(player, text);
        return true;
    }
    private void checkBalance(Player player, IChatBaseComponent iChatBaseComponent) {
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, iChatBaseComponent);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutTitle);
    }
}
