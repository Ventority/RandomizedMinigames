package de.ventority.randomizedminigames.Minigames;

import de.ventority.randomizedminigames.util.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ForceItemBattleTeams extends ForceItemBattle {
    private final List<Team> teams;

    public ForceItemBattleTeams(List<Player> players, Player owner, List<Team> teams) {
        super(players, owner);
        this.teams = teams;
    }

    @Override
    protected void updatePlayerItem(Player p, ItemStack item) {
        // teams is null while super() is still running, fall back to per-player assignment
        if (teams == null) {
            super.updatePlayerItem(p, item);
            return;
        }
        for (Team team : teams) {
            if (team.getPlayers().contains(p)) {
                for (Player teammate : team.getPlayers()) {
                    super.updatePlayerItem(teammate, item);
                }
                return;
            }
        }
    }

    @Override
    protected void showEndMessage(Player winner) {
        Team winningTeam = null;
        for (Team team : teams) {
            if (team.getPlayers().contains(winner)) {
                winningTeam = team;
                break;
            }
        }
        String title = winningTeam != null
                ? winningTeam.getColor() + winningTeam.getColor().name() + " Team" + ChatColor.RESET + " won!"
                : winner.getName() + " won!";

        for (Player player : contestants) {
            player.teleport(winner);
            if (player != winner)
                player.setGameMode(GameMode.SPECTATOR);
            player.sendTitle(title, "Resetting players...", 10, 70, 20);
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }
}
