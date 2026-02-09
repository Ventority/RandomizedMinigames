package de.ventority.randomizedminigames.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class Team {
    private final List<Player> players;
    private final ChatColor color;

    public Team(List<Player> members, ChatColor color) {
        players = members;
        this.color = color;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public ChatColor getColor() {
        return color;
    }

    public void removePlayer(Player p) {
        players.remove(p);
    }
}
