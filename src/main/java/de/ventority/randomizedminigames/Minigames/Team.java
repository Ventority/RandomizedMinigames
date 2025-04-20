package de.ventority.randomizedminigames.Minigames;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
}
