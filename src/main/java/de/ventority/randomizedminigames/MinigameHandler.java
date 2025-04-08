package de.ventority.randomizedminigames;

import de.ventority.randomizedminigames.GUI.GUIClickEvent;
import de.ventority.randomizedminigames.Minigames.ItemForceBattle;
import de.ventority.randomizedminigames.Minigames.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class MinigameHandler {
    private static List<Minigame> minigames = new ArrayList<>();

    public static void createMinigame(int gameNumber) {
        List<Player> players = Bukkit.getOnlinePlayers().stream().map(p -> ((Player) p)).toList();
        minigames.add(new ItemForceBattle(players));
        getServer().getPluginManager().registerEvents(minigames.getFirst(), RandomizedMinigames.serverSettingsHandler.getPlugin());
    }

    public static void deleteGame(Minigame minigame) {
        minigames.remove(minigame);
    }
}
