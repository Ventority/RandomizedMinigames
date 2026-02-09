package de.ventority.randomizedminigames.gui;

import com.google.gson.Gson;
import de.ventority.randomizedminigames.util.MinigameHandler;
import de.ventority.randomizedminigames.util.Settings;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GUICreator extends BaseWindow {
    private final String path;

    // DTOs als statische Klassen
    private static class GUIItem {
        int slot;
        String material;
        String name;
        Map<String, String> nbt;
    }
    private static class GUIConfig {
        String title;
        int rows;
        List<GUIItem> items;
    }

    public GUICreator(Player p, String path) {
        super(p);
        this.path = path;
    }

    @Override
    protected void fillGUI() {
        Settings playerSettings = MinigameHandler.getSettings(p);

        Gson gson = new Gson();
        GUIConfig config;

        try (InputStream in = Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream(path),
                "Resource not found: " + path
        );
             Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {

            config = gson.fromJson(reader, GUIConfig.class);
            if (config == null) {
                throw new IllegalStateException("Parsed GUI config is null; is the file empty? Path: " + path);
            }
            if (config.items == null) {
                throw new IllegalStateException("'items' is null in GUI config. Path: " + path);
            }
        } catch (Exception e) {
            System.out.println("Error loading " + path + ". Check that the file is packaged under src/main/resources and not empty.");
            return;
        }

        for (GUIItem guiItem : config.items) {
            Material mat = Material.valueOf(guiItem.material.toUpperCase());
            ItemStack item = new ItemStack(mat);

            if (guiItem.nbt != null) {
                for (Map.Entry<String, String> entry : guiItem.nbt.entrySet()) {
                    addNBT(item, entry.getKey(), entry.getValue());
                }
            }

            String displayName = guiItem.name
                    .replace("%scoreboard%", playerSettings.getScoreboardStatus() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off")
                    .replace("%scoreboardState%", playerSettings.getScoreboardStatus() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off")
                    .replace("%timer%", playerSettings.isTimed ? ChatColor.GREEN + "On" : ChatColor.RED + "Off");

            setItemName(item, displayName);

            if (guiItem.slot == -1) {
                addItemToGUI(item);
            } else {
                addItemToGUI(guiItem.slot, item);
            }
        }
    }
}