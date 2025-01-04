package org.example.da.chestopenersimulator.visisbleSystem;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.example.da.chestopenersimulator.ChestOpenerSimulator;

import java.util.HashMap;
import java.util.Map;

public class HideSystem {
    private static Map<Player,TeamList> hiddenPlayers = new HashMap<>();

    // Спрятать всех игроков
    private static void hidePlayer(){
        for(Player player : hiddenPlayers.keySet()){
            for(Player player1 : hiddenPlayers.keySet()){
                if(!player.equals(player1)){
                    player.hidePlayer(ChestOpenerSimulator.getPluginName(), player1);
                }
            }
        }
    }
    // Спрятать нового игрока
    private static void hideNewPlayer(Player player1){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(!player.equals(player1)) {
                player.hidePlayer(ChestOpenerSimulator.getPluginName(), player1);
            }
            if(!player1.equals(player)) {
                player1.hidePlayer(ChestOpenerSimulator.getPluginName(),player);
            }
        }
    }
    // Добавить нового игрока в лист с игроками
    public static void putHideNewPlayersList(Player player){
        for(TeamList tl : hiddenPlayers.values()) {
            if (tl.getOwnerName() == player){
                return;
            }else if(tl.getTeam1() == player){
                return;
            }else if(tl.getTeam2() == player){
                return;
            }
        }
        hiddenPlayers.put(player, new TeamList(player, null, null));
        hideNewPlayer(player);
    }
    // Добавить без проверок
    public static void putHidePlayersList(Player player){
        hiddenPlayers.put(player, new TeamList(player, null, null));
        hideNewPlayer(player);
    }
    // Добавление игрока в группу
    public static  String joinTeam(Player owner,Player team){
        if(hiddenPlayers.containsKey(team)) {
            if (hiddenPlayers.get(owner).getTeam1() == null) {
                hiddenPlayers.put(owner, new TeamList(owner, team, null));
                hiddenPlayers.remove(team);
                showJoinPlayerInTeam(owner);
                return "Добавлен " + team.getDisplayName();
            } else if (hiddenPlayers.get(owner).getTeam2() == null && hiddenPlayers.get(owner).getTeam1() != hiddenPlayers.get(owner).getTeam2()) {
                hiddenPlayers.put(owner, new TeamList(owner, hiddenPlayers.get(owner).getTeam1(), team));
                hiddenPlayers.remove(team);
                showJoinPlayerInTeam(owner);
                return "Добавлен " + team.getDisplayName();
            } else
                return "Нету масте";
        }else
            return "Ты уже в команде";
    }
    // Ливнуть с команды
    public static String leaveInTeam(Player owner,Player team){
        if(!hiddenPlayers.containsKey(team)) {
            for(TeamList tl : hiddenPlayers.values()) {
                if(tl.getTeam1() == team){
                    hiddenPlayers.put(team, new TeamList(team, null, null));
                    hiddenPlayers.put(owner,new TeamList(owner, null, hiddenPlayers.get(owner).getTeam2()));
                    hideNewPlayer(team);
                    return "Вы ливнули";
                }else if(tl.getTeam2() == team){
                    hiddenPlayers.put(team, new TeamList(team, null, null));
                    hiddenPlayers.put(owner,new TeamList(owner,  hiddenPlayers.get(owner).getTeam1(),null));
                    hideNewPlayer(team);
                    return "Вы ливнули";
                }
            }
            return "Ошибка";
        }else
            return "Ты не в команде";
    }
    // Кикнуть игрока с команды
    public static String kickInTeam(Player owner,Player team){
        if(hiddenPlayers.containsKey(owner)) {
            for(TeamList tl : hiddenPlayers.values()) {
                if(tl.getTeam1() == team){
                    hiddenPlayers.put(team, new TeamList(team, null, null));
                    hiddenPlayers.put(owner,new TeamList(owner, null, hiddenPlayers.get(owner).getTeam2()));
                    hideNewPlayer(team);
                    return "Кикнули " + team.getDisplayName();
                }else if(tl.getTeam2() == team){
                    hiddenPlayers.put(team, new TeamList(team, null, null));
                    hiddenPlayers.put(owner,new TeamList(owner,  hiddenPlayers.get(owner).getTeam1(),null));
                    hideNewPlayer(team);
                    return "Кикнули " + team.getDisplayName();
                }
            }
            return "Игрок не найден";
        }else
            return "Ты не лидер";
    }
    // При запуске всех кто на сервере, спрятать
    public static void allHidePlayersAndPutList(){
        for(Player player : Bukkit.getOnlinePlayers()){
            hiddenPlayers.put(player,new TeamList(player,null,null));
        }
        hidePlayer();
    }
    // Выйти со всех групп
    public static void leaveAllTeam(Player player){
        if(getTeamListPlayer(player).getOwnerName() == player){
            if(getTeamListPlayer(player).getTeam1() != null && getTeamListPlayer(player).getTeam2() != null){
                putHidePlayersList(getTeamListPlayer(player).getTeam1());
                putHidePlayersList(getTeamListPlayer(player).getTeam2());
                getTeamListPlayer(player).getTeam1().sendMessage( ChatColor.GREEN +  "Лидер вышел");
                getTeamListPlayer(player).getTeam2().sendMessage(ChatColor.GREEN +  "Лидер вышел");
                hiddenPlayers.remove(player);
            }else if(getTeamListPlayer(player).getTeam1() != null && getTeamListPlayer(player).getTeam2() == null){
                putHidePlayersList(getTeamListPlayer(player).getTeam1());
                getTeamListPlayer(player).getTeam1().sendMessage( ChatColor.GREEN +  "Лидер вышел");
                hiddenPlayers.remove(player);
            }else if(getTeamListPlayer(player).getTeam1() == null && getTeamListPlayer(player).getTeam2() != null){
                putHidePlayersList(getTeamListPlayer(player).getTeam2());
                getTeamListPlayer(player).getTeam2().sendMessage(ChatColor.GREEN +  "Лидер вышел");
                hiddenPlayers.remove(player);
            }
        }else if(getTeamListPlayer(player).getTeam1() == player){
            if(getTeamListPlayer(player).getTeam2() != null){
                getTeamListPlayer(player).getOwnerName().sendMessage(ChatColor.GREEN +  "Участник" + player.getDisplayName() + " вышел");
                getTeamListPlayer(player).getTeam2().sendMessage(ChatColor.GREEN +  "Участник" + player.getDisplayName() + " вышел");
                hiddenPlayers.put(getTeamListPlayer(player).getOwnerName(), new TeamList(getTeamListPlayer(player).getOwnerName(),
                        getTeamListPlayer(player).getTeam2(),null));
            }
            getTeamListPlayer(player).getOwnerName().sendMessage(ChatColor.GREEN +  "Участник" + player.getDisplayName() + " вышел");
            hiddenPlayers.put(getTeamListPlayer(player).getOwnerName(), new TeamList(getTeamListPlayer(player).getOwnerName(), null,null));
        }else if(getTeamListPlayer(player).getTeam2() == player){
            getTeamListPlayer(player).getOwnerName().sendMessage(ChatColor.GREEN +  "Участник" + player.getDisplayName() + " вышел");
            hiddenPlayers.put(getTeamListPlayer(player).getOwnerName(), new TeamList(getTeamListPlayer(player).getOwnerName(),
                    getTeamListPlayer(player).getTeam1(),null));
        }
    }
    // Показать всех в группе
    private static void showJoinPlayerInTeam(Player owner){
        if(hiddenPlayers.get(owner).getTeam1() != null && hiddenPlayers.get(owner).getTeam2() == null){
            hiddenPlayers.get(owner).getOwnerName().showPlayer(ChestOpenerSimulator.getPluginName(),  hiddenPlayers.get(owner).getTeam1());
            hiddenPlayers.get(owner).getTeam1().showPlayer(ChestOpenerSimulator.getPluginName(),  hiddenPlayers.get(owner).getOwnerName());
        }else if(hiddenPlayers.get(owner).getTeam1() != null && hiddenPlayers.get(owner).getTeam2() != null){
            hiddenPlayers.get(owner).getOwnerName().showPlayer(ChestOpenerSimulator.getPluginName(),  hiddenPlayers.get(owner).getTeam1());
            hiddenPlayers.get(owner).getOwnerName().showPlayer(ChestOpenerSimulator.getPluginName(),  hiddenPlayers.get(owner).getTeam2());
            hiddenPlayers.get(owner).getTeam2().showPlayer(ChestOpenerSimulator.getPluginName(),  hiddenPlayers.get(owner).getTeam1());
            hiddenPlayers.get(owner).getTeam2().showPlayer(ChestOpenerSimulator.getPluginName(),  hiddenPlayers.get(owner).getOwnerName());
            hiddenPlayers.get(owner).getTeam1().showPlayer(ChestOpenerSimulator.getPluginName(),  hiddenPlayers.get(owner).getTeam1());
            hiddenPlayers.get(owner).getTeam1().showPlayer(ChestOpenerSimulator.getPluginName(),  hiddenPlayers.get(owner).getOwnerName());
        } else if (hiddenPlayers.get(owner).getTeam1() == null && hiddenPlayers.get(owner).getTeam2() != null) {
            hiddenPlayers.get(owner).getOwnerName().showPlayer(ChestOpenerSimulator.getPluginName(),  hiddenPlayers.get(owner).getTeam2());
            hiddenPlayers.get(owner).getTeam2().showPlayer(ChestOpenerSimulator.getPluginName(),  hiddenPlayers.get(owner).getOwnerName());
        }
    }
    // Показать всех игроков
    private static void showAllPlayers(Player player) {
        for(Player player1 : Bukkit.getOnlinePlayers()){
            if(!player.equals(player1)) {
                player.showPlayer(ChestOpenerSimulator.getPluginName(), player1);
            }
        }
    }
    public static String checkYourTeamList(Player player){
        String owner = "";
        String team1 = "нету";
        String team2 = "нету";
        for(TeamList tl : hiddenPlayers.values()){
            if(tl.getOwnerName() == player) {
                owner += tl.getOwnerName().getDisplayName();
                if (tl.getTeam1() != null) {
                    team1 = tl.getTeam1().getDisplayName();
                }
                if(tl.getTeam2() != null){
                    team2 = tl.getTeam2().getDisplayName();
                }
                return "Владелец: "+ owner + ", участники: " + team1 + ", "+ team2;
            }else if(tl.getTeam1() == player){
                owner += tl.getOwnerName().getDisplayName();
                if (tl.getTeam1() != null) {
                    team1 = tl.getTeam1().getDisplayName();
                }
                if(tl.getTeam2() != null){
                    team2 = tl.getTeam2().getDisplayName();
                }
                return "Владелец: "+ owner + ", участники: " + team1 + ", "+ team2;
            }else if(tl.getTeam2() == player){
                owner += tl.getOwnerName().getDisplayName();
                if (tl.getTeam1() != null) {
                    team1 = tl.getTeam1().getDisplayName();
                }
                if(tl.getTeam2() != null){
                    team2 = tl.getTeam2().getDisplayName();
                }
                return "Владелец: "+ owner + ", участники: " + team1 + ", "+ team2;
            }

        }
        return "Error 1";
    }
    public static TeamList getTeamListPlayer(Player player){
        for(TeamList tl : hiddenPlayers.values()){
            if(tl.getOwnerName() == player){
                return tl;
            }else if(tl.getTeam1() == player){
                return tl;
            }else if(tl.getTeam2() == player){
                return tl;
            }
        }
        return null;
    }
    private static void hidePlayer(Player viewer, Player viewer1) {
        if (viewer == null || viewer1 == null || viewer ==viewer1) return;

        CraftPlayer craftViewer = (CraftPlayer) viewer;
        EntityPlayer entViewer = ((CraftPlayer) viewer1).getHandle();

        PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(entViewer.getId());
        craftViewer.getHandle().playerConnection.sendPacket(destroyPacket);
    }
    private static void showPlayer(Player viewer, Player viewer1) {
        if (viewer == null || viewer1 == null || viewer == viewer1) return;


        CraftPlayer craftViewer = (CraftPlayer) viewer;
        EntityPlayer entTarget = ((CraftPlayer) viewer1).getHandle();
        PacketPlayOutNamedEntitySpawn spawnPacket = new PacketPlayOutNamedEntitySpawn(entTarget);
        craftViewer.getHandle().playerConnection.sendPacket(spawnPacket);
    }
}
