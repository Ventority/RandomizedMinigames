package de.ventority.randomizedminigames;

import de.ventority.randomizedminigames.Minigames.ForceItemBattle;
import de.ventority.randomizedminigames.Minigames.ForceItemBattleSameItems;
import de.ventority.randomizedminigames.Minigames.MinigameBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;


import static org.bukkit.Bukkit.getServer;

public class MinigameHandler {
    private static final List<MinigameBase> minigames = new ArrayList<>();

    public static void createMinigame(int gameNumber) {
        List<Player> players = Bukkit.getOnlinePlayers().stream().map(p -> ((Player) p)).toList();
        switch (gameNumber) {
            case 0: minigames.add(new ForceItemBattle(players));
            case 1: minigames.add(new ForceItemBattleSameItems(players));
        }
        getServer().getPluginManager().registerEvents(minigames.getFirst(), RandomizedMinigames.serverSettingsHandler.getPlugin());
    }

    public static void deleteGame(MinigameBase minigame) {
        minigames.remove(minigame);
    }
}
