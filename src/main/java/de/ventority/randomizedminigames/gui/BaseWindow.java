package de.ventority.randomizedminigames.gui;

import de.ventority.randomizedminigames.RandomizedMinigames;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class BaseWindow {
    protected final Player p;
    protected final Inventory gui;

    protected BaseWindow(Player p) {
        this.p = p;
        gui = Bukkit.createInventory(null, 54,
                RandomizedMinigames.serverSettingsHandler.getServerName() + ChatColor.RESET + ChatColor.DARK_GRAY);
    }

    protected void fillBorder() {
        for (int i = 0; i < 9; i++) placeBorderPane(i);
        for (int row = 1; row < 5; row++) {
            placeBorderPane(9 * row);
            placeBorderPane(8 + 9 * row);
        }
        for (int i = 0; i < 9; i++) placeBorderPane(45 + i);
    }

    private void placeBorderPane(int slot) {
        ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        setName(pane, " ");
        NBTHelper.set(pane, "Action", "none");
        gui.setItem(slot, pane);
    }

    protected abstract void fillGUI();

    public abstract void handleClick(String action, InventoryClickEvent event);

    public void buildWindow() {
        fillBorder();
        fillGUI();
        GUIClickEvent.register(p, this);
        p.openInventory(gui);
    }

    protected ItemStack makeItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        setName(item, name);
        return item;
    }

    protected void setName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

    // Place an item at a specific slot, overwriting whatever is there (including border).
    protected void place(int slot, ItemStack item) {
        gui.setItem(slot, item);
    }

    // Add an item to the next available inner slot.
    protected void add(ItemStack item) {
        gui.addItem(item);
    }

    public Inventory getGUI() {
        return gui;
    }
}
