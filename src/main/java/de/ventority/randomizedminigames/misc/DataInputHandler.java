package de.ventority.randomizedminigames.misc;

import de.ventority.randomizedminigames.Minigames.Team;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataInputHandler {
    private final HashMap<Player, List<Player>> selectedPlayers = new HashMap<>();
    private final HashMap<Player, Integer> selectedMinigame = new HashMap<>();
    private final HashMap<Player, Integer> selectedLimit = new HashMap<>();
    private final HashMap<Player, List<Team>> teams = new HashMap<>();
    private boolean showScoreboard = true;

    public void switchScoreboard() {
        showScoreboard = !showScoreboard;
    }

    public boolean getScoreboardStatus() {
        return showScoreboard;
    }

    public List<Player> getSelectedPlayers(Player p) {
        if (!selectedPlayers.containsKey(p)) {
            selectedPlayers.put(p, new ArrayList<>());
            selectedPlayers.get(p).add(p);
        }
        return selectedPlayers.get(p);
    }

    public void addPlayersToSelection(Player owner, Player toAdd) {
        selectedPlayers.get(owner).add(toAdd);
    }

    public void removePlayersFromSelection(Player owner, Player toRemove) {
        selectedPlayers.get(owner).remove(toRemove);
    }

    public void removeSelectionEntry(Player player) {
        selectedPlayers.remove(player);
    }

    public int getSelectedMinigame(Player player) {
        return selectedMinigame.get(player);
    }

    public void setSelectedMinigame(Player player, int minigame) {
        selectedMinigame.put(player, minigame);
    }

    public Integer getSelectedLimit(Player p) {
        if (!selectedLimit.containsKey(p)) {
            selectedLimit.put(p, 10);
        }
        return selectedLimit.get(p);
    }

    public void addToSelectedLimit(Player p) {
        selectedLimit.put(p, selectedLimit.get(p) + 1);
    }

    public void subFromSelectedLimit(Player p) {
        selectedLimit.put(p, selectedLimit.get(p) - 1);
    }


}
