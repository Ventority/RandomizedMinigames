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

import java.util.Objects;

public abstract class BaseWindow {
    protected final Player p;
    protected final Inventory gui;
    protected String status;

    public BaseWindow(Player p, String status) {
        this.p = p;
        gui = Bukkit.createInventory(p, 54, RandomizedMinigames.serverSettingsHandler.getServerName()
                + ChatColor.RESET + ChatColor.DARK_GRAY + status);
        this.status = status;
    }

    protected void fillBorder() {
        ItemStack stack;
        for (int i = 0; i < 9; i++) {
            stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
            gui.setItem(i, stack);
            setItemName(stack, " " + i);
        }
        for (int i = 1; i < 5; i++) {
            stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
            gui.setItem(9 * i, stack);
            setItemName(stack, " " + 9 * i);
            stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
            gui.setItem(8 + 9 * i, stack);
            setItemName(stack, " " + 8 + 9 * i);
        }
        for (int i = 0; i < 9; i++) {
            stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
            gui.setItem(i + 5 * 9, stack);
            setItemName(stack, " " + i + 5 * 9);
        }
    }

    protected abstract void fillGUI();

    protected void buildWindow() {
        fillBorder();
        fillGUI();
        addNBT(Objects.requireNonNull(gui.getItem(0)), "ItemMiniGame", "true");
        p.openInventory(gui);
    }

    protected void addNBT(ItemStack item, String key, String value) {
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key1 = new NamespacedKey(RandomizedMinigames.serverSettingsHandler.getPlugin(), key);
        if (meta == null) return;
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key1, PersistentDataType.STRING, value);
    }

    protected void setItemName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.setDisplayName(name);
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
