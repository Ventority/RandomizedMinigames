package de.ventority.randomizedminigames.Minigames;

import org.bukkit.entity.Player;

import java.util.List;

public class ForceItemBattleTeams extends ForceItemBattle{
    private List<Team> teams;
    public ForceItemBattleTeams(List<Player> players, Player owner, List<Team> teams) {
        super(players, owner);
        this.teams = teams;
    }
}
