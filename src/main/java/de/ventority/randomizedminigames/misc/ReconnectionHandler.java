package de.ventority.randomizedminigames.misc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class ReconnectionHandler {

    private final File file;
    private final Gson gson;

    private Map<UUID, PlayerData> storedPlayers;

    public ReconnectionHandler(File dataFolder, String fileName) {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        this.file = new File(dataFolder, fileName);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.storedPlayers = new HashMap<>();
        load();
    }

    public void addPlayer(UUID uuid, Inventory inventory) {
        PlayerData data = new PlayerData(uuid, inventory);
        storedPlayers.put(uuid, data);
        save();
    }

    public void removePlayer(UUID uuid) {
        storedPlayers.remove(uuid);
        save();
    }

    public boolean containsPlayer(UUID uuid) {
        return storedPlayers.containsKey(uuid);
    }

    public Inventory getInventory(UUID uuid) {
        PlayerData data = storedPlayers.get(uuid);
        return data != null ? data.toInventory() : null;
    }

    public Set<UUID> getPlayers() {
        return storedPlayers.keySet();
    }

    public void load() {
        if (!file.exists()) {
            save();
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<UUID, PlayerData>>(){}.getType();
            Map<UUID, PlayerData> loaded = gson.fromJson(reader, type);
            if (loaded != null) {
                storedPlayers = loaded;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(storedPlayers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class PlayerData {
        private UUID uuid;
        private List<Map<String, Object>> inventory; // serialisiertes Inventar

        public PlayerData(UUID uuid, Inventory inv) {
            this.uuid = uuid;
            this.inventory = new ArrayList<>();
            for (ItemStack item : inv.getContents()) {
                if (item != null) {
                    this.inventory.add(item.serialize());
                } else {
                    this.inventory.add(null); // Slot leer
                }
            }
        }

        public UUID getUuid() {
            return uuid;
        }

        public Inventory toInventory() {
            Inventory inv = org.bukkit.Bukkit.createInventory(null, 54, "Stored Inventory");
            ItemStack[] items = new ItemStack[inventory.size()];

            for (int i = 0; i < inventory.size(); i++) {
                Map<String, Object> data = inventory.get(i);
                if (data != null) {
                    items[i] = ItemStack.deserialize(data);
                } else {
                    items[i] = null;
                }
            }
            inv.setContents(items);
            return inv;
        }
    }
}
