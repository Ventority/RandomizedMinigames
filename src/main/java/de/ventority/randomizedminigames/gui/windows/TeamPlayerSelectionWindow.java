package de.ventority.randomizedminigames.gui.windows;

import de.ventority.randomizedminigames.gui.BaseWindow;
import de.ventority.randomizedminigames.gui.NBTHelper;
import de.ventority.randomizedminigames.util.MinigameHandler;
import de.ventority.randomizedminigames.util.Settings;
import de.ventority.randomizedminigames.util.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class TeamPlayerSelectionWindow extends BaseWindow {
    private static final ChatColor[] TEAM_COLORS = {
            ChatColor.GREEN, ChatColor.RED, ChatColor.BLUE,
            ChatColor.DARK_PURPLE, ChatColor.BLACK, ChatColor.DARK_GREEN
    };
    private static final Material[] TEAM_MATERIALS = {
            Material.GREEN_WOOL, Material.RED_WOOL, Material.BLUE_WOOL,
            Material.PURPLE_WOOL, Material.BLACK_WOOL, Material.GREEN_WOOL
    };

    public TeamPlayerSelectionWindow(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        Settings settings = MinigameHandler.getSettings(p);
        int teamIndex = settings.getSelectedTeamIndex();
        Team currentTeam = settings.getTeam(teamIndex);

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (MinigameHandler.getOccupiedPlayers().contains(online)) continue;

            boolean inCurrentTeam = currentTeam.getPlayers().contains(online);
            // Hide players already assigned to a different team
            boolean inOtherTeam = settings.getSelectedPlayers().contains(online) && !inCurrentTeam;
            if (inOtherTeam) continue;

            ItemStack head = makePlayerHead(online, inCurrentTeam);
            NBTHelper.set(head, "Action", "clickedPlayerInTeams");
            NBTHelper.set(head, "Player", online.getName());
            add(head);
        }

        ItemStack teamIndicator = makeItem(TEAM_MATERIALS[teamIndex],
                TEAM_COLORS[teamIndex] + "Team #" + (teamIndex + 1));
        NBTHelper.set(teamIndicator, "Action", "none");
        place(4, teamIndicator);

        ItemStack back = makeItem(Material.ARROW, "Back");
        NBTHelper.set(back, "Action", "back");
        place(49, back);
    }

    @Override
    public void handleClick(String action, InventoryClickEvent event) {
        if (action.equals("back")) {
            new TeamSetupSelectionWindow(p).buildWindow();
            return;
        }
        if (action.equals("clickedPlayerInTeams")) {
            String playerName = NBTHelper.get(event.getCurrentItem(), "Player");
            Player target = Bukkit.getPlayer(playerName);
            if (target == null) return;
            Settings settings = MinigameHandler.getSettings(p);
            Team team = settings.getTeam(settings.getSelectedTeamIndex());
            if (team.getPlayers().contains(target)) {
                team.removePlayer(target);
                settings.removePlayersFromSelection(target);
            } else {
                team.addPlayer(target);
                settings.addPlayersToSelection(target);
            }
            new TeamPlayerSelectionWindow(p).buildWindow();
        }
    }

    private ItemStack makePlayerHead(Player target, boolean inCurrentTeam) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta != null) {
            meta.setOwningPlayer(target);
            meta.setDisplayName((inCurrentTeam ? ChatColor.GREEN : ChatColor.RED) + target.getName());
            head.setItemMeta(meta);
        }
        return head;
    }
}
