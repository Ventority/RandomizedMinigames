package de.ventority.randomizedminigames.util;

import de.ventority.randomizedminigames.Minigames.*;
import de.ventority.randomizedminigames.RandomizedMinigames;
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
        MinigameBase newGame = null;
        switch (gameNumber) {
            case 0: newGame = new ForceItemBattle(players, caller); break;
            case 1: newGame = new ForceItemBattleSameItems(players, caller); break;
            case 2: newGame = new BlockRandomizer(players, caller); break;
            case 3: newGame = new ForceItemBattleTeams(players, caller, settings.get(caller).getTeams()); break;
            case 4: newGame = new OnlyChests(players, caller); break;
        }
        if (newGame != null) {
            minigames.add(newGame);
            getServer().getPluginManager().registerEvents(newGame, RandomizedMinigames.serverSettingsHandler.getPlugin());
        }
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
        for (MinigameBase minigame : new ArrayList<>(minigames))
            minigame.killGame();
    }

    public static Settings getSettings(Player player) {
        if (!settings.containsKey(player))
            settings.put(player, new Settings());
        return settings.get(player);
    }

    public static void resetSettings(Player player) {
        settings.put(player, new Settings());
    }
}
