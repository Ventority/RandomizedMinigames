package de.ventority.randomizedminigames.GUI.handlers;

import de.ventority.randomizedminigames.GUI.MinigameSetups.MinigameSetup;
import de.ventority.randomizedminigames.GUI.MinigameSetups.TeamCountSelection;
import de.ventority.randomizedminigames.RandomizedMinigames;
import de.ventority.randomizedminigames.misc.MinigameHandler;
import de.ventority.randomizedminigames.misc.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MinigameSelectHandler extends GUIHandler{
    public MinigameSelectHandler(InventoryClickEvent event, String action) {
        super(event, action);
    }

    @Override
    public void handle() {
        Settings settings = MinigameHandler.getSettings((Player) event.getWhoClicked());
        if (action.equals("startForceItem") || action.equals("startForceItemSameItem")) {
            settings.selectMinigame(Integer.parseInt(getNBT(event.getCurrentItem(), "selectedMinigame")));
            new MinigameSetup((Player) event.getWhoClicked()).buildWindow();
        }
        if (action.equals("startForceItemTeams")) {
            settings.selectMinigame(Integer.parseInt(getNBT(event.getCurrentItem(), "selectedMinigame")));
            new TeamCountSelection((Player)event.getWhoClicked()).buildWindow();
        }
        if (action.equals("homeMenu")) {
            new MinigameSetup((Player) event.getWhoClicked()).buildWindow();
        }
    }
}
