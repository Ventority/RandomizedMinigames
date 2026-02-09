package de.ventority.randomizedminigames.Minigames;

import de.ventority.randomizedminigames.util.Team;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ForceItemBattleTeams extends ForceItemBattle{
    private final List<Team> teams;
    public ForceItemBattleTeams(List<Player> players, Player owner, List<Team> teams) {
        super(players, owner);
        this.teams = teams;
    }

    @Override
    protected void updatePlayerItem(Player p, ItemStack item) {
        for (Team team : teams) {
            for (Player curPlayer : team.getPlayers()) {
                super.updatePlayerItem(curPlayer, item);
            }
        }
    }

    @Override
    protected void showEndMessage(Player winner) {
        for (Player player : contestants) {
            player.teleport(winner);
            if (player != winner)
                player.setGameMode(GameMode.SPECTATOR);
            Team winningTeam = new Team(null, null);
            for (Team team : teams)
                if (team.getPlayers().contains(player)) winningTeam = team;
            player.sendTitle(winningTeam.getColor() + winningTeam.getColor().name() + "Team " + ChatColor.RESET + " won!",
                    "Resetting players...", 10, 70, 20);
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }


}
