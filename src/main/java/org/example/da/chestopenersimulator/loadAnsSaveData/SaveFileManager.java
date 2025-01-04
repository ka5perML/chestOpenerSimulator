package org.example.da.chestopenersimulator.loadAnsSaveData;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.chestopenersimulator.playerManager.Manager;
import org.example.da.chestopenersimulator.playerManager.PlayerStatsManager;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SaveFileManager {
    private final JavaPlugin plugin;
    private final File dataFile;
    private FileConfiguration config;

    public SaveFileManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "playerData.yml");
        loadData();
    }

    public void loadData() {
        if (!dataFile.exists()) {
            try {
                dataFile.getParentFile().mkdirs();
                dataFile.createNewFile();
                System.out.println("Created new file: " + dataFile.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
                return;
            }
        }
        config = YamlConfiguration.loadConfiguration(dataFile);
        if (config.contains("playerStats")) {
            for (String uuidString : config.getConfigurationSection("playerStats").getKeys(false)) {
                try {
                    UUID uuid = UUID.fromString(uuidString);
                    String name = config.getString("playerStats."+uuidString+".playerName");
                    int money = config.getInt("playerStats."+uuidString+".money");
                    if(uuid == null){
                        continue;
                    }
                    PlayerStatsManager psm = new PlayerStatsManager(name, uuid, money);
                    Manager.loadPlayerMap(psm);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid UUID: " + uuidString);
                }
            }
            System.out.println("Data loaded: " + Manager.getPlayerMap());
        } else {
            System.out.println("No data was found in " + dataFile.getName());
        }
    }


    public void saveData() {
        try {
            if (!dataFile.exists()) {
                dataFile.getParentFile().mkdirs();
                dataFile.createNewFile();
                System.out.println("Created new file: " + dataFile.getAbsolutePath());
            }

            if (config == null) {
                config = new YamlConfiguration();
            }

            config.createSection("playerStats");

            for (PlayerStatsManager psm : Manager.getPlayerMap().values()) {
                UUID uuid = psm.getPlayerUUID();
                config.set("playerStats." + uuid.toString() + ".playerName", psm.getPlayerName());
                config.set("playerStats." + uuid.toString() + ".money", psm.getMoney());
            }
            config.save(dataFile);
            System.out.println("Data saved: " + Manager.getPlayerMap());
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}

