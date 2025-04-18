package de.ventority.randomizedminigames.misc;

import de.ventority.randomizedminigames.Minigames.MinigameBase;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataInputHandler {
    private final HashMap<Player, List<Player>> selectedPlayers = new HashMap<>();
    private final HashMap<Player, Integer> selectedMinigame = new HashMap<>();

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

    public void removeEntry(Player player) {
        selectedPlayers.remove(player);
    }

    public int getSelectedMinigame(Player player) {
        return selectedMinigame.get(player);
    }

    public void setSelectedMinigame(Player player, int minigame) {
        selectedMinigame.put(player, minigame);
    }
}
