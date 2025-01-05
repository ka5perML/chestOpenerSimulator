package org.example.da.chestopenersimulator.chestRoulette;

import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.chestopenersimulator.ChestOpenerSimulator;
import org.example.da.chestopenersimulator.playerManager.PlayerChangeBalance;
import org.example.da.chestopenersimulator.visisbleSystem.HideSystem;
import org.example.da.chestopenersimulator.visisbleSystem.VisibleTeamArmorStand;

import java.util.*;
import java.util.stream.Collectors;

public class RouletteSystem implements Listener{
    private final Map<Location, Long> chestCooldowns = new HashMap<>();
    private Map<Player,Boolean> playerOpenList = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() != Material.CHEST) return;

        Location chestLocation = clickedBlock.getLocation();

        // Все условия пройдены, запускаем анимацию
        if (!playerOpenList.containsKey(player)) {
            // Обычный кейс
            if (isGoodOpen(chestLocation,player)) {
                player.sendMessage(ChatColor.RED + "Началось открытие!");
                event.setCancelled(true);
                startRouletteAnimation(chestLocation, player,infoCase(chestLocation));
                playRouletteSound(player);
                chestCooldowns.put(chestLocation, System.currentTimeMillis());
                playerOpenList.put(player, true);
                return;
            }
            event.setCancelled(true);
            sendTitleMessage(player, new ChatMessage(ChatColor.RED +(ChatColor.BOLD + "Не достаточно средств!")));
            return;
        }
        sendTitleMessage(player, new ChatMessage(ChatColor.RED + (ChatColor.BOLD +"Вы уже открываете кейс")));
        event.setCancelled(true);
    }
    public void sendTitleMessage(Player player, IChatBaseComponent iChatBaseComponent){
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, iChatBaseComponent);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutTitle);
    }
    // Добро на откыртие
    private boolean isGoodOpen(Location chestLocation, Player player){
        try {
            List<ChestInformation> lowChests = Arrays.stream(ChestInformation.values())
                    .filter(chest -> chest.getLocation().equals(chestLocation))
                   .collect(Collectors.toList());
            if(lowChests.get(0).getInformation().equals("LOW")){
                if (PlayerChangeBalance.isHaveManeyBayPrizes(player,1000))
                    return true;
            }else if(lowChests.get(0).getInformation().equals("BIG")) {
                if (PlayerChangeBalance.isHaveManeyBayPrizes(player,20000))
                    return true;
            }else if(lowChests.get(0).getInformation().equals("LOX")) {
                if (PlayerChangeBalance.isHaveManeyBayPrizes(player,0))
                    return true;
            }
        }catch (IndexOutOfBoundsException e){

        }
        return false;
    }
    // Тип кейса
    private String infoCase(Location chestLocation){
        try {
            List<ChestInformation> lowChests = Arrays.stream(ChestInformation.values())
                    .filter(chest -> chest.getLocation().equals(chestLocation))
                    .collect(Collectors.toList());
            if (lowChests.get(0).getInformation().equals("LOW")) {
                return "LOW";
            } else if (lowChests.get(0).getInformation().equals("BIG")) {
                return "BIG";
            } else if (lowChests.get(0).getInformation().equals("LOX")) {
                return "LOX";
            }
        }catch (IndexOutOfBoundsException e){
        }
        return null;
    }
    // Анимация "Чем движет"
    private void startRouletteAnimation(Location chestLocation, Player player,String num) {
        int numberOfBlocks = 16; // Количество блоков
        double radius = 1.5; // Радиус вращения
        List<ArmorStand> stands = new ArrayList<>();
        stands.removeAll(stands);

        // Спавн армор стендов
        for (int i = 0; i < numberOfBlocks; i++) {
            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity
                    (animationTeleport(i,0.1,chestLocation,radius,numberOfBlocks), EntityType.ARMOR_STAND);
            VisibleTeamArmorStand.hideArmorStand(armorStand, HideSystem.getTeamListPlayer(player));
            settingArmorStand(armorStand,num);
            stands.add(armorStand);
        }
            Plugin plugin = Bukkit.getPluginManager().getPlugin("chestOpenerSimulator"); // Определение главного класса
            if (plugin == null) {
                Bukkit.getLogger().severe("Don't found ChestOpenerSimulator");
                return;
            }
            new BukkitRunnable() {
                double speed = 0.1;// Скорость вращения
                int ticksPassed = 0;

                @Override
                public void run() {
                    if (ticksPassed >= 100) { // Конец
                        String winPrize = getPrize((ArrayList<ArmorStand>) stands);
                            if (winPrize != null) {
                            giveItem(player, winPrize);
                        }
                        stopBlock((ArrayList<ArmorStand>) stands);
                        playerOpenList.remove(player);
                        this.cancel();
                        return;
                    }
                    if (ticksPassed <= 80) {
                        for (int i = 0; i < stands.size(); i++) {
                            CraftArmorStand craftArmorStand = (CraftArmorStand) stands.get(i);
                            craftArmorStand.teleport(animationTeleport(i, speed, chestLocation, radius, numberOfBlocks));
                        }
                    }
                    speed += 2;
                    Bukkit.getLogger().fine(String.valueOf(ticksPassed));
                    ticksPassed += 1;
                }
            }.runTaskTimer(plugin,0,1);

    }
    // Звук рулетке
    public void playRouletteSound(Player player) {
        Location location = player.getLocation(); // Получаем местоположение игрока

        new BukkitRunnable() {
            int ticket;
            @Override
            public void run() {
                if (ticket >= 80) {
                    cancel();
                    return;
                }
                player.playSound(location, Sound.BLOCK_NOTE_PLING, 0.01f, 1.0f);
                ticket += 2;
            }
        }.runTaskTimer(ChestOpenerSimulator.getPluginName(), 0, 2);

    }

    // Анимация движения
    private Location animationTeleport(int i,double speed, Location chestLocation,double radius,int numberOfBlocks){
        double angle = speed * 0.05 + (i * (Math.PI * 2 / numberOfBlocks)); // Телепортирует блоки по кругу
        double x = chestLocation.getX() + 0.5 + radius * Math.cos(angle);
        double z = chestLocation.getZ() + 0.5 + radius * Math.sin(angle);
        double y = chestLocation.getY() + (0.2 * Math.sin(speed * 0.2)); // Летают вверх и вниз
        Location blockLocation = new Location(chestLocation.getWorld(), x, y, z);
        return blockLocation;
    }
    // Настройки армор стенда
    private void settingArmorStand(ArmorStand armorStand,String num){
        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setVisible(false);
        String prizeName = chancePrize(num);
        armorStand.setCustomName(prizeName);
        armorStand.setCustomNameVisible(true);
    }
    // Получаем армор стенд который стоит на шерсти
    private String getPrize(ArrayList<ArmorStand> armorStands){
        for(ArmorStand armorStand : armorStands){
            if(armorStand.getLocation().clone().add(0,-1,0).getBlock().getType()== Material.WOOL){
                String str = armorStand.getCustomName();
                return str;
            }
        }
        return null;
    }
    // Шансы на призы
    private static String chancePrize(String num) {
        Random random = new Random();
        double randomInt = random.nextDouble() * 100;
        if(num == "LOW") {
            if (randomInt < 1) {return "20000";
            } else if (randomInt < 5) {return "8000";
            } else if (randomInt < 15) {return "5000";
            } else if (randomInt < 25) {return "2500";
            } else if (randomInt < 40) {return "1000";
            } else if (randomInt < 60) {return "500";
            } else if (randomInt < 80) {return "100";
            } else return "100";
        }else if(num == "BIG"){
            if (randomInt < 1) {return "100000";
            } else if (randomInt < 5) {return "50000";
            } else if (randomInt < 15) {return "30000";
            } else if (randomInt < 25) {return "25000";
            } else if (randomInt < 40) {return "15000";
            } else if (randomInt < 60) {return "10000";
            } else if (randomInt < 80) {return "5000";
            } else return "5000";
        }else if(num == "LOX"){
            if (randomInt < 1) {return "1000";
            } else if (randomInt < 5) {return "800";
            } else if (randomInt < 15) {return "600";
            } else if (randomInt < 25) {return "400";
            } else if (randomInt < 40) {return "250";
            } else if (randomInt < 60) {return "100";
            } else if (randomInt < 80) {return "50";
            } else return "50";
        }
        return null;
    }
    // Удаляет армор стенды
    private void stopBlock(ArrayList<ArmorStand> armorStands) {
        for (ArmorStand block : armorStands){
            block.remove();
        }
    }
    // Выдача приза
    private void giveItem(Player player, String prize) {
        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Выйгрыш ") + prize);
        PlayerChangeBalance.givedPRizes(player, Long.parseLong(prize));
    }
}
