package de.ventority.randomizedminigames.GUI.handlers;

import de.ventority.randomizedminigames.GUI.SettingsWindow;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SettingsHandler extends GUIHandler {
    public SettingsHandler(InventoryClickEvent event, String action) {
        super(event, action);
    }

    @Override
    public void handle() {
        if (action.equals("selectSettings")) {
            new SettingsWindow((Player)event.getWhoClicked());
        }
    }
}
