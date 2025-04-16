package de.ventority.randomizedminigames.GUI;

import de.ventority.randomizedminigames.MinigameHandler;
import de.ventority.randomizedminigames.Minigames.Minigame;
import de.ventority.randomizedminigames.RandomizedMinigames;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
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
        if (inventory == null) return;
        if (inventory.getItem(0) == null) return;
        if (isMinigamePlugin(inventory.getItem(0))) {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            if (getNBT(event.getCurrentItem(), "Type").equals("Minigame")) {
                handleMinigame(event, getNBT(event.getCurrentItem(), "Action"));
            }
            if (getNBT(event.getCurrentItem(), "Type").equals("Misc")) {
                handleSettings(event, getNBT(event.getCurrentItem(), "Action"));
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
        String s = getNBT(item, "ItemMiniGame");
        System.out.println(s);
        System.out.println(item.getItemMeta().getDisplayName());
        System.out.println(s != null && s.equals("true"));
        return s != null && s.equals("true");
    }

    private void handleMinigame(InventoryClickEvent event, String action) {
        for (Minigame minigame : Minigame.values()) {
            if (action.equals(minigame.getAction())) {
                MinigameHandler.createMinigame(minigame.getNumber());
            }
        }
    }

    private void handleSettings(InventoryClickEvent event, String action) {
        if (action.equals("selectSettings")) {
            new SettingsWindow((Player)event.getWhoClicked(), "Settings");
        }

    }
}
