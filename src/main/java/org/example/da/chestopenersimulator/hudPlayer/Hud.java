package org.example.da.chestopenersimulator.hudPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.example.da.chestopenersimulator.ChestOpenerSimulator;
import org.example.da.chestopenersimulator.playerManager.Manager;

public class Hud {
    private static Scoreboard scoreboard;
    private static Objective objective;
    private Manager manager;
    public Hud(Manager manager){
        runnableUpdateBalanceScoreboard();
        this.manager = manager;
    }
    public void hudCreate(Player player){
        ScoreboardManager manager1 = Bukkit.getScoreboardManager();
        scoreboard = manager1.getNewScoreboard();

        objective = scoreboard.registerNewObjective( ChatColor.GREEN +"Статистика", "");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = objective.getScore(ChatColor.GREEN + "Баланс");
        Score score1 = objective.getScore(ChatColor.GREEN + "Онлайн");
        score.setScore((int) this.manager.getPlayerMap().get(player.getUniqueId()).getMoney());
        score1.setScore(Bukkit.getOnlinePlayers().size());
        setPlayerScoreboard(player);
    }

    private void setPlayerScoreboard(Player player) {
        player.setScoreboard(scoreboard);
    }
    public void removePlayerScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
    private void runnableUpdateBalanceScoreboard(){
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Player player: Bukkit.getOnlinePlayers()){
                    removePlayerScoreboard(player);
                    hudCreate(player);
                }
            }
        }.runTaskTimer(ChestOpenerSimulator.getPluginName(), 0,100);
    }

}
