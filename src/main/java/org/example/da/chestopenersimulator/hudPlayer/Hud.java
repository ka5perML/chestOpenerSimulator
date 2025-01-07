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

    public static void hudCreate(Player player){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();

        objective = scoreboard.registerNewObjective( ChatColor.GREEN +"Статистика", "");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = objective.getScore(ChatColor.GREEN + "Баланс");
        Score score1 = objective.getScore(ChatColor.GREEN + "Онлайн");
        score.setScore((int) Manager.getPlayerMap().get(player.getUniqueId()).getMoney());
        score1.setScore(Bukkit.getOnlinePlayers().size());
        setPlayerScoreboard(player);
    }

    private static void setPlayerScoreboard(Player player) {
        player.setScoreboard(scoreboard);
    }
    public static void removePlayerScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
    public static void runnableUpdateBalanceScoreboard(){
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
