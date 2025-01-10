package org.example.da.chestopenersimulator.visisbleSystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.example.da.chestopenersimulator.ChestOpenerSimulator;

import java.util.HashMap;
import java.util.Map;

public class HideSystem {
    private Map<Player,TeamList> hiddenPlayers = new HashMap<>();

    // Спрятать всех игроков
    private void hidePlayer(){
        for(Player player : hiddenPlayers.keySet()){
            for(Player player1 : hiddenPlayers.keySet()){
                if(!player.equals(player1)){
                    player.hidePlayer(ChestOpenerSimulator.getPluginName(), player1);
                }
            }
        }
    }
    // Спрятать нового игрока
    private void hideNewPlayer(Player newPlayer) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.equals(newPlayer)) {
                player.hidePlayer(ChestOpenerSimulator.getPluginName(), newPlayer);
                newPlayer.hidePlayer(ChestOpenerSimulator.getPluginName(), player);
            }
        }
    }
    // Добавить нового игрока в лист с игроками
    public void addHidePlayersList(Player player){
        hiddenPlayers.putIfAbsent(player, new TeamList(player, null, null));
        hideNewPlayer(player);
    }

    // Добавление игрока в группу
    public String joinTeam(Player owner,Player team){
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
    public String leaveInTeam(Player owner,Player team){
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
    public String kickInTeam(Player owner,Player team){
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
    public void allHidePlayersAndPutList(){
        for(Player player : Bukkit.getOnlinePlayers()){
            hiddenPlayers.put(player,new TeamList(player,null,null));
        }
        hidePlayer();
    }
    // Выйти со всех групп
    public String leaveAllTeam(Player player){
        TeamList team = getTeamListPlayer(player);
        if (team == null) {
            return "Вы не состоите в команде";
        }
        if (player.equals(team.getOwnerName())) {
            ownerLeaving(team);
            return "Вы покинули команду, команда расформирована";
        }

        if (player.equals(team.getTeam1())) {
            team.setTeam1(null);
        } else if (player.equals(team.getTeam2())) {
            team.setTeam2(null);
        }

        addHidePlayersList(player);
        return ChatColor.GREEN + "Вы покинули команду.";
    }
    // Показать всех в группе
    private void showJoinPlayerInTeam(Player owner){
        TeamList teamList = hiddenPlayers.get(owner);
        if (teamList == null) return;

        Player team1 = teamList.getTeam1();
        Player team2 = teamList.getTeam2();
        Player ownerName = teamList.getOwnerName();

        if (team1 != null) {
            ownerName.showPlayer(ChestOpenerSimulator.getPluginName(), team1);
            team1.showPlayer(ChestOpenerSimulator.getPluginName(), ownerName);
        }
        if (team2 != null) {
            ownerName.showPlayer(ChestOpenerSimulator.getPluginName(), team2);
            team2.showPlayer(ChestOpenerSimulator.getPluginName(), ownerName);
        }
        if (team1 != null && team2 != null) {
            team1.showPlayer(ChestOpenerSimulator.getPluginName(), team2);
            team2.showPlayer(ChestOpenerSimulator.getPluginName(), team1);
        }
    }
    // Показать всех игроков
    private void showAllPlayers(Player player) {
        for(Player player1 : Bukkit.getOnlinePlayers()){
            if(!player.equals(player1)) {
                player.showPlayer(ChestOpenerSimulator.getPluginName(), player1);
            }
        }
    }
    public String checkYourTeamList(Player player){
        for (TeamList tl : hiddenPlayers.values()) {
            if (tl.getOwnerName() == player || tl.getTeam1() == player || tl.getTeam2() == player) {
                String owner = tl.getOwnerName().getDisplayName();
                String team1 = (tl.getTeam1() != null) ? tl.getTeam1().getDisplayName() : "нету";
                String team2 = (tl.getTeam2() != null) ? tl.getTeam2().getDisplayName() : "нету";

                return "Владелец: " + owner + ", участники: " + team1 + ", " + team2;
            }
        }
        return null;
    }
    public TeamList getTeamListPlayer(Player player){
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
    // Выход владельца
    private void ownerLeaving(TeamList team) {
        Player team1 = team.getTeam1();
        Player team2 = team.getTeam2();

        if (team1 != null) addHidePlayersList(team1);
        if (team2 != null) addHidePlayersList(team2);

        hiddenPlayers.remove(team.getOwnerName().getUniqueId());
    }
}
