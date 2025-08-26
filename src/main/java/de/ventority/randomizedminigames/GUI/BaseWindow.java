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

    public BaseWindow(Player p) {
        this.p = p;
        gui = Bukkit.createInventory(p, 54, RandomizedMinigames.serverSettingsHandler.getServerName()
                + ChatColor.RESET + ChatColor.DARK_GRAY);
    }

    protected void fillBorder() {
        ItemStack stack;
        for (int i = 0; i < 9; i++) {
            stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
            addNBT(stack, "Action", "none");
            setItemName(stack, " ");
            addItemToGUI(i, stack);
        }
        for (int i = 1; i < 5; i++) {
            stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
            addNBT(stack, "Action", "none");
            setItemName(stack, " ");
            addItemToGUI(9 * i, stack);
            stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
            addNBT(stack, "Action", "none");
            setItemName(stack, " ");
            addItemToGUI(8 + 9 * i, stack);
        }
        for (int i = 0; i < 9; i++) {
            stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
            setItemName(stack, " ");
            addNBT(stack, "Action", "none");
            addItemToGUI(i + 5 * 9, stack);
        }
    }

    protected abstract void fillGUI();

    public void buildWindow() {
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
        item.setItemMeta(meta);
    }

    protected void setItemName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

    protected void addItemToGUI(int index, ItemStack item) {
        addNBT(item, "ItemMiniGame", "true");
        gui.setItem(index, item);
    }

    protected void addItemToGUI(ItemStack item) {
        addNBT(item, "ItemMiniGame", "true");
        gui.addItem(item);
    }

    public Inventory getGUI() {
        return gui;
    }

    private Player getPlayer() {
        return p;
    }

    protected String getNBT(ItemStack item, String key) {
        if (item == null) return "ItemIsNull";
        ItemMeta meta = item.getItemMeta();
        NamespacedKey nsKey = new NamespacedKey(RandomizedMinigames.serverSettingsHandler.getPlugin(), key);
        if (meta == null) return "NoItemMeta";
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.get(nsKey, PersistentDataType.STRING);
    }
}
