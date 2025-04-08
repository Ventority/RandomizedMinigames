package de.ventority.randomizedminigames.GUI;


import de.ventority.randomizedminigames.MinigameHandler;
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
        Inventory inventory = event.getInventory();
        if (inventory.getItem(0) == null) return;
        if (hasNBTData(inventory.getItem(0))) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
                MinigameHandler.createMinigame(0);
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
}
