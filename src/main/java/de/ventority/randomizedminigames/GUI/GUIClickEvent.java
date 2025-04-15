package de.ventority.randomizedminigames.GUI;


import de.ventority.randomizedminigames.MinigameHandler;
import de.ventority.randomizedminigames.Minigames.Minigame;
import de.ventority.randomizedminigames.RandomizedMinigames;
import org.bukkit.Bukkit;
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
        if (inventory == null) return;
        if (inventory.getItem(0) == null) return;
        ItemStack firstItem = inventory.getItem(0);
        if (hasNBTData(firstItem)) {
            event.setCancelled(true);
            if (getStatus(firstItem).equals("selectMinigame")) {
                handleMinigame(event);
            }
            if (getStatus(firstItem).equals("selectSettings")) {
                handleSettings(event);
            }
        }
    }

    private boolean hasNBTData(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(RandomizedMinigames.serverSettingsHandler.getPlugin(), "IsMinigamePlugin");
        if (meta == null) return false;
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return "1".equals(data.get(key, PersistentDataType.STRING));
    }

    private String getStatus(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return "";
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(RandomizedMinigames.serverSettingsHandler.getPlugin(), "IsMinigamePlugin");
        if (meta == null) return "";
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.get(key, PersistentDataType.STRING);
    }

    private void handleMinigame(InventoryClickEvent event) {
        for (Minigame minigame : Minigame.values()) {
            if (event.getCurrentItem().getType() == minigame.getMaterial()) {
                MinigameHandler.createMinigame(minigame.getNumber());
            }
        }
    }

    private void handleSettings(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        PersistentDataContainer data = event.getCurrentItem().getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(RandomizedMinigames.serverSettingsHandler.getPlugin(), "State");
        if (data.get(key, PersistentDataType.STRING).equals("addContestants")) {
            
        }
    }
}
