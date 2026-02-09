package de.ventority.randomizedminigames.gui.handlers;

import de.ventority.randomizedminigames.gui.MinigameSetups.*;
import de.ventority.randomizedminigames.util.Team;
import de.ventority.randomizedminigames.util.MinigameHandler;
import de.ventority.randomizedminigames.util.Settings;
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
            playerSettings.subFromWinLimit();
            new SetLimit((Player)event.getWhoClicked()).buildWindow();
        }

        if (action.equals("addToLimit")) {
            playerSettings.addToWinLimit();
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
            Player toWork = Bukkit.getPlayer(getNBT(event.getCurrentItem(), "Player"));
            int teamNumber = Integer.parseInt(getNBT(event.getClickedInventory().getItem(4), "Team"));
            Team team = playerSettings.getTeam(teamNumber - 1);
            if (playerSettings.getSelectedPlayers().contains(toWork)) {
                team.removePlayer(toWork);
                playerSettings.removePlayersFromSelection(toWork);
            } else {
                team.addPlayer(toWork);
                playerSettings.addPlayersToSelection(toWork);
            }
            return;
        }

        if (action.equals("startTimerSetup")) {
            new SetTimeLimit((Player)event.getWhoClicked()).buildWindow();
        }

        if (action.contains("FromTimeLimit") || action.contains("ToTimeLimit")) {
            switch (action) {
                case "add30ToTimeLimit": playerSettings.setTimeLimit(playerSettings.getTimeLimit() + 30*60); break;
                case "add10ToTimeLimit": playerSettings.setTimeLimit(playerSettings.getTimeLimit() + 10*60); break;
                case "add1ToTimeLimit": playerSettings.setTimeLimit(playerSettings.getTimeLimit() + 60); break;
                case "sub30FromTimeLimit": playerSettings.setTimeLimit(playerSettings.getTimeLimit() - 30*60); break;
                case "sub10FromTimeLimit": playerSettings.setTimeLimit(playerSettings.getTimeLimit() - 10*60); break;
                case "sub1FromTimeLimit": playerSettings.setTimeLimit(playerSettings.getTimeLimit() - 60); break;
            }
            new SetTimeLimit((Player)event.getWhoClicked()).buildWindow();
        }
    }
}
