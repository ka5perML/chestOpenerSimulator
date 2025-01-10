package org.example.da.chestopenersimulator.playerCommands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.da.chestopenersimulator.visisbleSystem.HideSystem;
import org.example.da.chestopenersimulator.visisbleSystem.TeamList;

import java.util.HashMap;
import java.util.Map;

public class TeamCommand implements CommandExecutor {
    private Map<Player,Player> invitedList = new HashMap<>();
    private HideSystem hideSystem;
    public TeamCommand(HideSystem hideSystem){
        this.hideSystem = hideSystem;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            return false;
        }
        Player player = (Player) commandSender;
        if(command.getName().equalsIgnoreCase("team")) {
            if (strings.length != 1) {
                Player player1 = Bukkit.getPlayer(strings[1]);
                if (strings[0].equals("add") || strings[0].equals("invite") || strings[0].equals("i")){
                    if (player1 != null && player1 != player && hideSystem.getTeamListPlayer(player).getOwnerName() == player) {
                        invitedList.put(player,player1);
                        sendYesNoButtons(player1, player);
                        return true;
                    } else {
                        player.sendMessage(ChatColor.RED + "Игрок не найден");
                        return true;
                    }
                } else if (strings[0].equals("kick")) {
                    if (player1 != null && player1 != player) {
                        player.sendMessage(ChatColor.GREEN + hideSystem.kickInTeam(player, player1));
                        return true;
                    } else {
                        player.sendMessage(ChatColor.RED + "Игрок не найден");
                        return true;
                    }
                }
                player.sendMessage(ChatColor.RED + "/team <add|kick> <Name>");
                return true;
            }else if(strings.length != 0) {
                if(strings[0].equals("yes") && containsValue(invitedList,player)){
                    player.sendMessage(ChatColor.GREEN + (hideSystem.joinTeam(getKeyByValue(invitedList,player), player)));
                    removeByValueLambda(invitedList,player);
                    return true;
                }else if(strings[0].equals("no") && containsValue(invitedList,player)){
                    player.sendMessage(ChatColor.RED + "Вы отказались.");
                    removeByValueLambda(invitedList,player);
                    return true;
                }else if(strings[0].equals("remove") && invitedList.containsKey(player)) {
                    player.sendMessage(ChatColor.RED + "Прошлый запрос удален.");
                    invitedList.remove(player);
                }else if(strings[0].equals("list")) {
                    player.sendMessage(hideSystem.checkYourTeamList(player));
                    return true;
                }else if(strings[0].equals("leave")) {
                    player.sendMessage(ChatColor.RED + (hideSystem.leaveInTeam(hideSystem.getTeamListPlayer(player).getOwnerName(),player)));
                    return true;
                }else
                    player.sendMessage(ChatColor.RED + "/team <remove|list|leave>");
                return true;
            }else{
                player.sendMessage(ChatColor.RED + "/team <add|kick> <Name>");
            }
        }
        return true;
    }
    private void sendYesNoButtons(Player player, Player inviter) {

        TextComponent yesButton = new TextComponent(ChatColor.GREEN +" [Да]  ");
        yesButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team yes"));
        yesButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN +"Нажмите, чтобы согласиться.").create()));

        TextComponent noButton = new TextComponent(ChatColor.RED + "  [Нет]  ");
        noButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team no"));
        noButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.RED +"Нажмите, чтобы отказаться.").create()));


        TextComponent message = new TextComponent("Вас пригласил в команду "+ inviter.getDisplayName() +", принять? ");

        message.addExtra(yesButton);
        message.addExtra(noButton);


        player.spigot().sendMessage(message);
    }
    private static void removeByValueLambda(Map<Player, Player> map, Player valueToRemove) {
        map.entrySet().removeIf(entry -> entry.getValue().equals(valueToRemove));
    }
    private static boolean containsValue(Map<Player, Player> map, Player valueToCheck) {
        for (Player value : map.values()) {
            if (value == valueToCheck) {
                return true;
            }
        }
        return false;
    }
    public static <K, V> Player getKeyByValue(Map<Player, Player> map, Player value) {
        for (Map.Entry<Player, Player> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
