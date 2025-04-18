package de.ventority.randomizedminigames.misc;

import de.ventority.randomizedminigames.Minigames.ForceItemBattle;
import de.ventority.randomizedminigames.Minigames.ForceItemBattleSameItems;
import de.ventority.randomizedminigames.Minigames.MinigameBase;
import de.ventority.randomizedminigames.RandomizedMinigames;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;


import static org.bukkit.Bukkit.getServer;

public class MinigameHandler {
    private static final List<MinigameBase> minigames = new ArrayList<>();

    public static void createMinigame(int gameNumber, Player caller) {
        List<Player> players = RandomizedMinigames.dataInputHandler.getSelectedPlayers(caller);
        switch (gameNumber) {
            case 0: minigames.add(new ForceItemBattle(players, caller)); break;
            case 1: minigames.add(new ForceItemBattleSameItems(players, caller)); break;
        }
        getServer().getPluginManager().registerEvents(minigames.getFirst(), RandomizedMinigames.serverSettingsHandler.getPlugin());
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
}
