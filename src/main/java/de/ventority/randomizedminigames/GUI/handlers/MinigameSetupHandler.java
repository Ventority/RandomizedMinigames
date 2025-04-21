package de.ventority.randomizedminigames.GUI.handlers;

import de.ventority.randomizedminigames.GUI.MinigameSetups.MinigameSetup;
import de.ventority.randomizedminigames.GUI.MinigameSetups.PlayerSelection;
import de.ventority.randomizedminigames.GUI.MinigameSetups.SetLimit;
import de.ventority.randomizedminigames.misc.MinigameHandler;
import de.ventority.randomizedminigames.misc.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MinigameSetupHandler extends GUIHandler {

    public MinigameSetupHandler(InventoryClickEvent event, String action) {
        super(event, action);
    }

    @Override
    public void handle() {
        if (action.equals("homeMenu")) {
            new MinigameSetup((Player) event.getWhoClicked()).buildWindow();
        }

        Settings playerSettings = MinigameHandler.getSettings(((Player) event.getWhoClicked()).getPlayer());
        if (action.equals("clickedPlayer")) {
            Player toWork = Bukkit.getPlayer(getNBT(event.getCurrentItem(), "Player"));
            if (playerSettings.getSelectedPlayers().contains(toWork)) {
                playerSettings.removePlayersFromSelection(toWork);
            } else {
                playerSettings.addPlayersToSelection(toWork);
            }
            new PlayerSelection((Player)event.getWhoClicked()).buildWindow();
        }

        if (action.equals("subFromLimit")) {
            playerSettings.subFromSelectedLimit();
            new SetLimit((Player)event.getWhoClicked()).buildWindow();
        }

        if (action.equals("addToLimit")) {
            playerSettings.addToSelectedLimit();
            new SetLimit((Player)event.getWhoClicked()).buildWindow();
        }

        if (action.equals("startPlayerSelection")) {
            new PlayerSelection((Player)event.getWhoClicked()).buildWindow();
        }

        if (action.equals("startLimitSelection")) {
            new SetLimit((Player)event.getWhoClicked()).buildWindow();
        }

        if (action.equals("switchScoreboard")) {
            playerSettings.switchScoreboard();
            new MinigameSetup((Player) event.getWhoClicked()).buildWindow();
        }

        if (action.equals("startGame")) {
            MinigameHandler.createMinigame(playerSettings.getSelectedMinigame(), (Player) event.getWhoClicked());
        }
    }
}
