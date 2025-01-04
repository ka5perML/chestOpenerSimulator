package org.example.da.chestopenersimulator.chestRoulette;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.chestopenersimulator.playerManager.PlayerChangeBalance;

import java.util.*;

public class RouletteSystem implements Listener{
    private final Map<Location, Long> chestCooldowns = new HashMap<>();
    private final Map<Location, Boolean> chestAnimating = new HashMap<>();
    private Map<Location,List<ArmorStand>> blocks = new HashMap<>();
    private Map<Player,Boolean> playerOpenList = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() != Material.CHEST) return;

        Location chestLocation = clickedBlock.getLocation();
        // Проверяем, не находится ли сундук в кулдауне (анимации)
        if (chestAnimating.getOrDefault(chestLocation, false)) {
            event.setCancelled(true); // Отменяем клик
            player.sendMessage("Сундук занят!");
            return;
        }
        // Сундук на рестарте
        if (chestCooldowns.containsKey(chestLocation) &&
                (System.currentTimeMillis() - chestCooldowns.get(chestLocation) < 6000)) {
            event.setCancelled(true);
            player.sendMessage("Сундук пока нельзя открыть!");
            return;
        }
        // Все условия пройдены, запускаем анимацию
        if (!playerOpenList.containsKey(player)) {
            // Обычный кейс
            if (ChestLocation.smallChestLocation(chestLocation)) {
                player.sendMessage(ChatColor.RED + "Началось открытие!");
                event.setCancelled(true); // Отменяем открытие обычного интерфейса сундука
                startRouletteAnimation(chestLocation, player,1);
                chestCooldowns.put(chestLocation, System.currentTimeMillis());
                playerOpenList.put(player, true);
                return;
            }
            // Покупной кейс
            if (ChestLocation.bigChestLocation(chestLocation) && PlayerChangeBalance.isHaveManeyBayPrizes(player)) {
                player.sendMessage(ChatColor.RED + (ChatColor.BOLD + PlayerChangeBalance.buyPrizes(player)));
                startRouletteAnimation(chestLocation, player,2);
                event.setCancelled(true);
                chestCooldowns.put(chestLocation, System.currentTimeMillis());
                playerOpenList.put(player, true);
                return;
            } else {
                player.sendMessage(ChatColor.RED + (ChatColor.BOLD + PlayerChangeBalance.buyPrizes(player)));
                event.setCancelled(true);
                return;
            }
        }
        player.sendMessage(ChatColor.RED + (ChatColor.BOLD +"Вы уже открываете кейс"));
        event.setCancelled(true);
    }
    // Анимация "Чем движет"
    private void startRouletteAnimation(Location chestLocation, Player player,int num) {
        int numberOfBlocks = 16; // Количество блоков
        double radius = 1.5; // Радиус вращения
        List<ArmorStand> stands = new ArrayList<>();
        chestAnimating.put(chestLocation, true);
        stands.removeAll(stands);

        // Спавн армор стендов
        for (int i = 0; i < numberOfBlocks; i++) {
            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity
                    (animationTeleport(i,0.1,chestLocation,radius,numberOfBlocks), EntityType.ARMOR_STAND);
            settingArmorStand(armorStand,num);
            stands.add(armorStand);
        }
        blocks.put(chestLocation, stands);
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
                    ArrayList<ArmorStand> blocks1 = (ArrayList<ArmorStand>) blocks.get(chestLocation);
                    if (ticksPassed >= 100) { // Конец
                        chestAnimating.put(chestLocation, false);
                        this.cancel();
                        String winPrize = getPrize(blocks1);
                            if (winPrize != null) {
                            giveItem(player, winPrize);
                        }
                        stopBlock(chestLocation);
                        playerOpenList.remove(player);
                        return;
                    }
                    if (ticksPassed <= 80) {
                        for (int i = 0; i < blocks1.size(); i++) {
                            blocks1.get(i).teleport(animationTeleport(i, speed, chestLocation, radius, numberOfBlocks));
                        }
                    }
                    speed += 2;
                    Bukkit.getLogger().fine(String.valueOf(ticksPassed));
                    ticksPassed += 1;
                }
            }.runTaskTimer(plugin,0,1);// Запускаем цикл каждую 1 тик (0.05 сек)

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
    private void settingArmorStand(ArmorStand armorStand,int num){
        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setVisible(false);
        ItemStack blockItem = new ItemStack(Material.STONE);
        armorStand.getEquipment().setHelmet(blockItem);
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
    private static String chancePrize(int num) {
        Random random = new Random();
        double randomInt = random.nextDouble() * 100;
        if(num == 1) {
            if (randomInt < 1) {
                return "20000";
            } else if (randomInt < 5) {
                return "8000";
            } else if (randomInt < 15) {
                return "5000";
            } else if (randomInt < 25) {
                return "2500";
            } else if (randomInt < 40) {
                return "1000";
            } else if (randomInt < 60) {
                return "500";
            } else if (randomInt < 80) {
                return "100";
            } else return "100";
        }else{
            if (randomInt < 1) {
                return "100000";
            } else if (randomInt < 5) {
                return "50000";
            } else if (randomInt < 15) {
                return "30000";
            } else if (randomInt < 25) {
                return "25000";
            } else if (randomInt < 40) {
                return "15000";
            } else if (randomInt < 60) {
                return "10000";
            } else if (randomInt < 80) {
                return "5000";
            } else return "5000";
        }
    }
    // Удаляет армор стенды
    private void stopBlock(Location chestLocation) {
        ArrayList<ArmorStand> blocks1 = (ArrayList<ArmorStand>) blocks.get(chestLocation);
        blocks.remove(chestLocation);
        for (ArmorStand block : blocks1){
            block.remove();
        }
    }
    // Выдача приза
    private void giveItem(Player player, String prize) {
        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Выйгрыш ") + prize);
        PlayerChangeBalance.givedPRizes(player, Long.parseLong(prize));
    }
}
