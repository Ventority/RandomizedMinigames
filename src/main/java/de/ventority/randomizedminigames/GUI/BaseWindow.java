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

public abstract class BaseWindow {
    private final Player p;
    private final Inventory gui;
    private String status;

    public BaseWindow(Player p, String status) {
        this.p = p;
        gui = Bukkit.createInventory(p, 54, RandomizedMinigames.serverSettingsHandler.getServerName()
                + ChatColor.RESET + ChatColor.DARK_GRAY + " " + status);
        this.status = status;
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

    public abstract void fillGUI();

    public void buildWindow() {
        fillBorder();
        fillGUI();
        p.openInventory(gui);
    }

    public ItemMeta addNBT(ItemMeta meta, String key, String value) {
        NamespacedKey namespacedKey = new NamespacedKey(RandomizedMinigames.serverSettingsHandler.getPlugin(), key);
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(namespacedKey, PersistentDataType.STRING, value);
        return meta;
    }

    public Inventory getGUI() {
        return gui;
    }

    private Player getPlayer() {
        return p;
    }

    private String getStatus() {
        return status;
    }
}
