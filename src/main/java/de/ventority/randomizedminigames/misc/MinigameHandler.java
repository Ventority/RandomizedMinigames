package de.ventority.randomizedminigames.misc;

import de.ventority.randomizedminigames.Minigames.ForceItemBattle;
import de.ventority.randomizedminigames.Minigames.ForceItemBattleSameItems;
import de.ventority.randomizedminigames.Minigames.MinigameBase;
import de.ventority.randomizedminigames.RandomizedMinigames;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static org.bukkit.Bukkit.getServer;

public class MinigameHandler {
    private static final List<MinigameBase> minigames = new ArrayList<>();

    private static final HashMap<Player, Settings> settings = new HashMap<>();

    public static void createMinigame(int gameNumber, Player caller) {
        List<Player> players = settings.get(caller).getSelectedPlayers();
        switch (gameNumber) {
            case 0: minigames.add(new ForceItemBattle(players, caller)); break;
            case 1: minigames.add(new ForceItemBattleSameItems(players, caller)); break;
        }
        getServer().getPluginManager().registerEvents(minigames.getFirst(), RandomizedMinigames.serverSettingsHandler.getPlugin());
    }

    public static List<MinigameBase> getMinigames() {
        if (minigames.isEmpty()) {
            return null;
        }
        return minigames;
    }

    public static void deleteGame(MinigameBase minigame) {
        minigames.remove(minigame);
    }

    public static List<Player> getOccupiedPlayers() {
        List<Player> lst = new ArrayList<>();
        for (MinigameBase minigame : minigames) {
            lst.addAll(minigame.getPlayers());
        }
        return lst;
    }

    public static void killAll() {
        for (MinigameBase minigame : minigames)
            minigame.killGame();
    }

    public static Settings getSettings(Player player) {
        if (!settings.containsKey(player))
            settings.put(player, new Settings(player));
        return settings.get(player);
    }

    public static void resetSettings(Player player) {
        settings.put(player, new Settings(player));
    }
}
