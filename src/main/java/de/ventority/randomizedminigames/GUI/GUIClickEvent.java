package de.ventority.randomizedminigames.GUI;

import de.ventority.randomizedminigames.GUI.handlers.MinigameSelectHandler;
import de.ventority.randomizedminigames.GUI.handlers.MinigameSetupHandler;
import de.ventority.randomizedminigames.GUI.handlers.SettingsHandler;
import de.ventority.randomizedminigames.RandomizedMinigames;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class GUIClickEvent implements Listener {
    @EventHandler
    public void onClickEvent(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (event.getCurrentItem() == null) return;
        if (inventory == null) return;
        if (inventory.getItem(0) == null) return;
        if (isMinigamePlugin(inventory.getItem(0))) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == (Material.BLACK_STAINED_GLASS_PANE)) return;
            switch (getNBT(event.getCurrentItem(), "Type")) {
                case "none": return;
                case "MinigameSelect": new MinigameSelectHandler(event, getNBT(event.getCurrentItem(), "Action")).handle();
                case "MinigameSetup": new MinigameSetupHandler(event, getNBT(event.getCurrentItem(), "Action")).handle();
                case "Misc": new SettingsHandler(event, getNBT(event.getCurrentItem(), "Action")).handle();
            }
        }
    }

    private String getNBT(ItemStack item, String key) {
        if (item == null) return "ItemIsNull";
        ItemMeta meta = item.getItemMeta();
        NamespacedKey nsKey = new NamespacedKey(RandomizedMinigames.serverSettingsHandler.getPlugin(), key);
        if (meta == null) return "NoItemMeta";
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.get(nsKey, PersistentDataType.STRING);
    }

    private boolean isMinigamePlugin(ItemStack item) {
        return getNBT(item, "ItemMiniGame") != null && getNBT(item, "ItemMiniGame").equals("true");
    }
}
