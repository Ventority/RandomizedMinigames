package de.ventority.randomizedminigames;

import de.ventority.randomizedminigames.Minigames.ForceItemBattle;
import de.ventority.randomizedminigames.Minigames.MinigameBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class MinigameHandler {
    private static List<MinigameBase> minigames = new ArrayList<>();

    public static void createMinigame(int gameNumber) {
        List<Player> players = Bukkit.getOnlinePlayers().stream().map(p -> ((Player) p)).toList();
        minigames.add(new ForceItemBattle(players));
        getServer().getPluginManager().registerEvents(minigames.getFirst(), RandomizedMinigames.serverSettingsHandler.getPlugin());
    }

    public static void deleteGame(MinigameBase minigame) {
        minigames.remove(minigame);
    }
}
