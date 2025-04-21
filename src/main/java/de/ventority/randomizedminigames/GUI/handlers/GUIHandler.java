package de.ventority.randomizedminigames.GUI.handlers;

import de.ventority.randomizedminigames.RandomizedMinigames;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public abstract class GUIHandler {
    protected final InventoryClickEvent event;
    protected final String action;

    public GUIHandler(InventoryClickEvent event, String action) {
        this.event = event;
        this.action = action;
    }

    public abstract void handle();

    protected String getNBT(ItemStack item, String key) {
        if (item == null) return "ItemIsNull";
        ItemMeta meta = item.getItemMeta();
        NamespacedKey nsKey = new NamespacedKey(RandomizedMinigames.serverSettingsHandler.getPlugin(), key);
        if (meta == null) return "NoItemMeta";
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.get(nsKey, PersistentDataType.STRING);
    }
}
