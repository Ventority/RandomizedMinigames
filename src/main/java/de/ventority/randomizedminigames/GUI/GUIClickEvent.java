package de.ventority.randomizedminigames.GUI;

import de.ventority.randomizedminigames.GUI.MinigameSetups.MinigameSetup;
import de.ventority.randomizedminigames.GUI.MinigameSetups.MinigamesDisplayWindow;
import de.ventority.randomizedminigames.GUI.MinigameSetups.PlayerSelection;
import de.ventority.randomizedminigames.GUI.MinigameSetups.SetLimit;
import de.ventority.randomizedminigames.SelectMinigame;
import de.ventority.randomizedminigames.misc.DataInputHandler;
import de.ventority.randomizedminigames.misc.MinigameHandler;
import de.ventority.randomizedminigames.Minigames.Minigame;
import de.ventority.randomizedminigames.RandomizedMinigames;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
        if (event.getCurrentItem() == null) return;
        if (inventory == null) return;
        if (inventory.getItem(0) == null) return;
        if (isMinigamePlugin(inventory.getItem(0))) {
            event.setCancelled(true);
            if (getNBT(event.getCurrentItem(), "Action").equals("none"))
                return;
            if (getNBT(event.getCurrentItem(), "Type").equals("MinigameSelect")) {
                handleMinigame(event, getNBT(event.getCurrentItem(), "Action"));
            }
            if (getNBT(event.getCurrentItem(), "Type").equals("Misc")) {
                handleSettings(event, getNBT(event.getCurrentItem(), "Action"));
            }
            if (getNBT(event.getCurrentItem(), "Type").equals("MinigameSetup")) {
                handleMinigameSetup(event, getNBT(event.getCurrentItem(), "Action"));
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

    private void handleMinigame(InventoryClickEvent event, String action) {
        event.getWhoClicked().closeInventory();
        if (action.equals("openMinigameSetup")) {
            RandomizedMinigames.dataInputHandler.setSelectedMinigame((Player) event.getWhoClicked(), Integer.parseInt(getNBT(event.getCurrentItem(), "selectedMinigame")));
            new MinigameSetup((Player) event.getWhoClicked(), action).buildWindow();
        }
        if (action.equals("homeMenu")) {
            new MinigameSetup((Player) event.getWhoClicked(), action).buildWindow();
        }
    }

    private void handleSettings(InventoryClickEvent event, String action) {
        event.getWhoClicked().closeInventory();
        if (action.equals("selectSettings")) {
            new SettingsWindow((Player)event.getWhoClicked(), "Settings");
        }

    }

    private void handleMinigameSetup(InventoryClickEvent event, String action) {
        if (action.equals("homeMenu")) {
            event.getWhoClicked().closeInventory();
            new MinigameSetup((Player) event.getWhoClicked(), "Minigame").buildWindow();
        }

        DataInputHandler data = RandomizedMinigames.dataInputHandler;
        if (action.equals("clickedPlayer")) {
            Player toWork = Bukkit.getPlayer(getNBT(event.getCurrentItem(), "Player"));
            if (data.getSelectedPlayers((Player) event.getWhoClicked()).contains(toWork)) {
                data.removePlayersFromSelection((Player) event.getWhoClicked(), toWork);
            } else {
                data.addPlayersToSelection((Player) event.getWhoClicked(), toWork);
            }
            new PlayerSelection((Player)event.getWhoClicked(), getNBT(event.getCurrentItem(), "selectedMinigame")).buildWindow();
        }

        if (action.equals("subFromLimit")) {
            data.subFromSelectedLimit((Player) event.getWhoClicked());
            new SetLimit((Player)event.getWhoClicked(), getNBT(event.getCurrentItem(), "selectedMinigame")).buildWindow();
        }

        if (action.equals("addToLimit")) {
            data.addToSelectedLimit((Player) event.getWhoClicked());
            new SetLimit((Player)event.getWhoClicked(), getNBT(event.getCurrentItem(), "selectedMinigame")).buildWindow();
        }

        if (action.equals("startPlayerSelection")) {
            event.getWhoClicked().closeInventory();
            new PlayerSelection((Player)event.getWhoClicked(), getNBT(event.getCurrentItem(), "selectedMinigame")).buildWindow();
        }

        if (action.equals("startLimitSelection")) {
            event.getWhoClicked().closeInventory();
            new SetLimit((Player)event.getWhoClicked(), getNBT(event.getCurrentItem(), "selectedMinigame")).buildWindow();
        }

        if (action.equals("switchScoreboard")) {
            data.switchScoreboard();
            new MinigameSetup((Player) event.getWhoClicked(), "Minigame").buildWindow();
        }

        if (action.equals("startGame")) {
            event.getWhoClicked().closeInventory();
            MinigameHandler.createMinigame(RandomizedMinigames.dataInputHandler.getSelectedMinigame((Player) event.getWhoClicked()), (Player) event.getWhoClicked());
        }
    }
}
