package de.ventority.randomizedminigames.util;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    private final List<Player> selectedPlayers = new ArrayList<>();
    int selectedMinigame;
    int selectedWinLimit = 10;
    List<Team> teams = new ArrayList<>();
    boolean showScoreboard = false;
    private int selectedTeamCount = 2;
    private int selectedTeamIndex = 0;
    public boolean isTimed = true;
    private int timeLimit = 1800;

    public Settings() {}

    public void switchScoreboard() {
        showScoreboard = !showScoreboard;
    }

    public boolean getScoreboardStatus() {
        return showScoreboard;
    }

    public List<Player> getSelectedPlayers() {
        return selectedPlayers;
    }

    public void addPlayersToSelection(Player toAdd) {
        selectedPlayers.add(toAdd);
    }

    public void removePlayersFromSelection(Player toRemove) {
        selectedPlayers.remove(toRemove);
    }

    public int getSelectedMinigame() {
        return selectedMinigame;
    }

    public void selectMinigame(int minigame) {
        selectedMinigame = minigame;
    }

    public int getSelectedLimit() {
        return selectedWinLimit;
    }

    public void addToWinLimit() {
        selectedWinLimit++;
    }

    public void subFromWinLimit() {
        selectedWinLimit--;
    }

    public void addTeamCount() {
        if (selectedTeamCount < 6)
            selectedTeamCount++;
    }

    public void subTeamCount() {
        if (selectedTeamCount > 2)
            selectedTeamCount--;
    }

    public int getTeamCount() {
        return selectedTeamCount;
    }

    public void setSelectedTeamIndex(int selectedTeamIndex) {
        this.selectedTeamIndex = selectedTeamIndex;
    }

    public int getSelectedTeamIndex() {
        return selectedTeamIndex;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Team getTeam(int i) {
        return teams.get(i);
    }

    public void addPlayerToTeam(int i, Player p) {
        teams.get(i).addPlayer(p);
    }

    public void removePlayerFromTeam(int i, Player p) {
        teams.get(i).removePlayer(p);
    }
}
