package de.ventority.randomizedminigames.gui.MinigameSetups;

import de.ventority.randomizedminigames.gui.BaseWindow;
import de.ventority.randomizedminigames.util.MinigameHandler;
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
        final ChatColor[] colors = new ChatColor[]{ChatColor.GREEN,
                ChatColor.RED, ChatColor.BLUE, ChatColor.DARK_PURPLE,
                ChatColor.BLACK, ChatColor.DARK_GREEN};
        final Material[] material = new Material[]{Material.GREEN_WOOL, Material.RED_WOOL,
                Material.BLUE_WOOL, Material.PURPLE_WOOL, Material.BLACK_WOOL, Material.GREEN_WOOL};

        ItemStack selectedTeam = new ItemStack(material[MinigameHandler.getSettings(p).getSelectedTeamIndex()], 1);
        setItemName(selectedTeam, colors[MinigameHandler.getSettings(p).getSelectedTeamIndex()] + "Team #" + (MinigameHandler.getSettings(p).getSelectedTeamIndex() + 1));
        addNBT(selectedTeam, "Team", Integer.toString(MinigameHandler.getSettings(p).getSelectedTeamIndex()));
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
