package de.ventority.randomizedminigames.GUI;

import de.ventority.randomizedminigames.Minigames.Minigame;
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

public class MinigamesDisplayWindow extends BaseWindow{

    public MinigamesDisplayWindow(Player p, String status) {
        super(p, status);
    }

    public void buildWindow() {
        fillBorder();
        fillGUI();
        p.openInventory(gui);
    }

    @Override
    protected void fillGUI() {
        for (Minigame minigame : Minigame.values()) {
            ItemStack item = new ItemStack(minigame.getMaterial(), 1);
            ItemMeta meta = item.getItemMeta();
            setItemName(item, minigame.getName());
            addNBT(item, "Type", "Minigame");
            addNBT(item, "Action", minigame.getAction());
            item.setItemMeta(meta);
            gui.addItem(item);
        }
        addSettings();
    }

    private void addSettings() {
        ItemStack settings = new ItemStack(Material.REDSTONE, 1);
        ItemMeta settingsMeta = settings.getItemMeta();
        setItemName(settings, "Settings");
        addNBT(settings, "Type", "Misc");
        addNBT(settings, "Action", "selectSettings");
        settings.setItemMeta(settingsMeta);
        gui.setItem(45, settings);
    }
}
