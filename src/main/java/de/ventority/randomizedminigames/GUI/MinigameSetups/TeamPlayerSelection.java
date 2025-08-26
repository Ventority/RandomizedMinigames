package de.ventority.randomizedminigames.GUI.MinigameSetups;

import de.ventority.randomizedminigames.GUI.BaseWindow;
import de.ventority.randomizedminigames.misc.MinigameHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class TeamPlayerSelection extends BaseWindow {
    public TeamPlayerSelection(Player p) {
        super(p);
    }


    @Override
    protected void fillGUI() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!MinigameHandler.getOccupiedPlayers().contains(player)) {
                ItemStack item = getPlayerHead(player);
                addNBT(item, "Type", "MinigameSetup");
                addNBT(item, "Action", "clickedPlayerInTeams");
                addNBT(item, "Player", player.getDisplayName());
                addItemToGUI(item);
            }
        }
        final org.bukkit.ChatColor[] colors = new org.bukkit.ChatColor[]{org.bukkit.ChatColor.GREEN,
                org.bukkit.ChatColor.RED, org.bukkit.ChatColor.BLUE, org.bukkit.ChatColor.DARK_PURPLE,
                org.bukkit.ChatColor.BLACK, org.bukkit.ChatColor.DARK_GREEN};
        final Material[] material = new Material[]{Material.GREEN_WOOL, Material.RED_WOOL,
                Material.BLUE_WOOL, Material.PURPLE_WOOL, Material.BLACK_WOOL, Material.GREEN_WOOL};

        ItemStack selectedTeam = new ItemStack(material[MinigameHandler.getSettings(p).getSelectedTeamIndex()], 1);
        setItemName(selectedTeam, colors[MinigameHandler.getSettings(p).getSelectedTeamIndex()] + "Team #" + (MinigameHandler.getSettings(p).getSelectedTeamIndex() + 1));
        addItemToGUI(4, selectedTeam);

        ItemStack back = new ItemStack(Material.ARROW);
        addNBT(back, "Type", "MinigameSetup");
        addNBT(back, "Action", "homeMenu");
        setItemName(back, "Back");
        addItemToGUI(49, back);
    }

    private ItemStack getPlayerHead(Player player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta != null) {
            meta.setOwningPlayer(player);
            meta.setDisplayName((MinigameHandler.getSettings(p).getSelectedPlayers().contains(player) ?
                    ChatColor.GREEN : ChatColor.RED) + player.getName());
            head.setItemMeta(meta);
        }
        return head;
    }
}
