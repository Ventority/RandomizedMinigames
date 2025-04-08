package de.ventority.randomizedminigames.GUI;

import de.ventority.randomizedminigames.RandomizedMinigames;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MinigamesDisplayWindow {
    private final Player p;
    private final Inventory gui;
    private String status;

    public MinigamesDisplayWindow(Player p, String status) {
        this.p = p;
        gui = Bukkit.createInventory(p, 54, RandomizedMinigames.serverSettingsHandler.getServerName()
                + ChatColor.RESET + ChatColor.DARK_GRAY + " Minigames");
        this.status = status;
    }

    public void buildWindow() {
        fillBorder();
        fillGUI();
        addNBT();
        p.openInventory(gui);
    }

    private void fillGUI() {
        gui.addItem(new ItemStack(Material.DIAMOND_SWORD));
    }

    private void fillBorder() {
        ItemStack stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(" ");
        for (int i = 0; i < 9; i++) {
            gui.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
            gui.getItem(i).setItemMeta(meta);
        }
        for (int i = 1; i < 5; i++) {
            gui.setItem(9 * i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
            gui.getItem(9 * i).setItemMeta(meta);
            gui.setItem(8 + 9 * i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
            gui.getItem(8 + 9 * i).setItemMeta(meta);
        }
        for (int i = 0; i < 9; i++) {
            gui.setItem(i + 5 * 9, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
            gui.getItem(i + 5 * 9).setItemMeta(meta);
        }
    }

    private void addNBT() {
        ItemStack item = gui.getItem(0);
        assert item != null;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key1 = new NamespacedKey(RandomizedMinigames.serverSettingsHandler.getPlugin(), "Status");
        NamespacedKey key2 = new NamespacedKey(RandomizedMinigames.serverSettingsHandler.getPlugin(), "IsMinigamePlugin");
        assert meta != null;
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key1, PersistentDataType.STRING, status);
        data.set(key2, PersistentDataType.STRING, "1");
        item.setItemMeta(meta);
    }
}
