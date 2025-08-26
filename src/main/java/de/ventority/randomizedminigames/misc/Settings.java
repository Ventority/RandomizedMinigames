package de.ventority.randomizedminigames.misc;

import de.ventority.randomizedminigames.Minigames.Team;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    private Player owner;
    private List<Player> selectedPlayers = new ArrayList<>();
    int selectedMinigame;
    int selectedLimit = 10;
    List<Team> teams = new ArrayList<>();
    boolean showScoreboard = true;
    private int selectedTeamCount = 2;
    private int selectedTeamIndex = 0;
    public int timerStop = 1800;

    public Settings(Player owner) {
        this.owner = owner;
    }

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
        return selectedLimit;
    }

    public void addToSelectedLimit() {
        selectedLimit++;
    }

    public void subFromSelectedLimit() {
        selectedLimit--;
    }

    public void addTeamCount() {
        if (selectedTeamCount < 6) {}
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

    public boolean isTimed;

}
