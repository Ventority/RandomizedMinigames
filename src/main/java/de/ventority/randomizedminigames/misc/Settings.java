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
        System.out.println(toAdd.getName());
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
}
