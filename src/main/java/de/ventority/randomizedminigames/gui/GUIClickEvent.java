package de.ventority.randomizedminigames.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIClickEvent implements Listener {
    private static final Map<UUID, BaseWindow> openWindows = new HashMap<>();

    public static void register(Player p, BaseWindow window) {
        openWindows.put(p.getUniqueId(), window);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player p = (Player) event.getPlayer();
        BaseWindow window = openWindows.get(p.getUniqueId());
        // Only unregister if the closing inventory is the one we track.
        // When a new window opens, register() is called before p.openInventory() fires this
        // event, so the map already holds the new window and won't be removed here.
        if (window != null && window.getGUI().equals(event.getInventory())) {
            openWindows.remove(p.getUniqueId());
        }
    }

    @EventHandler
    public void onClickEvent(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player p = (Player) event.getWhoClicked();
        BaseWindow window = openWindows.get(p.getUniqueId());
        if (window == null) return;
        if (!window.getGUI().equals(event.getClickedInventory())) return;

        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) return;
        if (item.getType() == Material.BLACK_STAINED_GLASS_PANE) return;

        String action = NBTHelper.get(item, "Action");
        if (action == null || action.equals("none")) return;

        window.handleClick(action, event);
    }
}
