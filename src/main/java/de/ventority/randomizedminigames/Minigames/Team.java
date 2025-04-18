package de.ventority.randomizedminigames.Minigames;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Team {
    private HashMap<String, List<Player>> teams;
    private HashMap<String, ChatColor> colors;
    public Team(HashMap<String, List<Player>> teams) {
        this.teams = teams;
    }

    public List<Player> getPlayersOf(String team) {
        return teams.get(team);
    }

    public void addPlayer(String team, Player p) {
        teams.get(team).add(p);
    }

    public void createTeam(String team) {
        teams.put(team, new ArrayList<>());
        ChatColor[] colors = ChatColor.values();
        ChatColor color;
        Random rnd = new Random();
        do color = colors[rnd.nextInt(colors.length)];
        while (!color.isColor() || colorExists(color));
    }

    private boolean colorExists(ChatColor color) {
        for (ChatColor c : colors.values())
            if (c.equals(color)) return true;
        return false;
    }

    public ChatColor getColor(String team) {
        return colors.get(team);
    }
}
