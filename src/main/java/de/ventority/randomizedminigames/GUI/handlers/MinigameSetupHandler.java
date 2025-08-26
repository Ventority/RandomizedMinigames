package de.ventority.randomizedminigames.GUI.handlers;

import de.ventority.randomizedminigames.GUI.MinigameSetups.*;
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

        if (action.equals("addTeamCount")) {
            playerSettings.addTeamCount();
            new TeamCountSelection((Player)event.getWhoClicked()).buildWindow();
        }

        if (action.equals("subTeamCount")) {
            playerSettings.subTeamCount();
            new TeamCountSelection((Player)event.getWhoClicked()).buildWindow();
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

        if (action.equals("toggleTimer")) {
            playerSettings.isTimed = !playerSettings.isTimed;
            new MinigameSetup((Player) event.getWhoClicked()).buildWindow();
        }

        if (action.equals("startGame")) {
            MinigameHandler.createMinigame(playerSettings.getSelectedMinigame(), (Player) event.getWhoClicked());
            event.getWhoClicked().closeInventory();
        }

        if (action.equals("TeamSetupSelection")) {
            new TeamSetupSelection((Player)event.getWhoClicked()).buildWindow();
        }

        if (action.equals("selectTeam")) {
            MinigameHandler.getSettings(((Player) event.getWhoClicked()).getPlayer()).setSelectedTeamIndex(Integer.parseInt(getNBT(event.getCurrentItem(), "selectedTeam")));
            new TeamPlayerSelection((Player)event.getWhoClicked()).buildWindow();
        }

        if (action.equals("clickedPlayerInTeams")) {
            return;
        }

        if (action.equals("startTimerSetup")) {
            new SetTimeLimit((Player)event.getWhoClicked()).buildWindow();
        }

        if (action.contains("FromTimeLimit") || action.contains("ToTimeLimit")) {
            switch (action) {
                case "add30ToTimeLimit": playerSettings.timerStop = playerSettings.timerStop + 30*60; break;
                case "add10ToTimeLimit": playerSettings.timerStop = playerSettings.timerStop + 10*60; break;
                case "add1ToTimeLimit": playerSettings.timerStop = playerSettings.timerStop + 60; break;
                case "sub30FromTimeLimit": playerSettings.timerStop = playerSettings.timerStop - 30*60; break;
                case "sub10FromTimeLimit": playerSettings.timerStop = playerSettings.timerStop - 10*60; break;
                case "sub1FromTimeLimit": playerSettings.timerStop = playerSettings.timerStop - 60; break;
            }
            new SetTimeLimit((Player)event.getWhoClicked()).buildWindow();
        }
    }
}
