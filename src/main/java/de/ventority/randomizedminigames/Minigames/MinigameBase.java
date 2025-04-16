package de.ventority.randomizedminigames.Minigames;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface MinigameBase extends Listener {
    int getID();
    String getName();
    ItemStack getSymbol();
    void addPlayers(List<Player> players);
}
