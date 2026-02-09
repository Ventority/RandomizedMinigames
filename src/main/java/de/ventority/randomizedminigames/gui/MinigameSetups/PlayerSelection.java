package de.ventority.randomizedminigames.gui.MinigameSetups;

import de.ventority.randomizedminigames.gui.BaseWindow;
import de.ventority.randomizedminigames.util.MinigameHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerSelection extends BaseWindow {
    public PlayerSelection(Player p) {
        super(p);
    }


    @Override
    protected void fillGUI() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!MinigameHandler.getOccupiedPlayers().contains(player)) {
                ItemStack item = getPlayerHead(player);
                addNBT(item, "Type", "MinigameSetup");
                addNBT(item, "Action", "clickedPlayer");
                addNBT(item, "Player", player.getDisplayName());
                addItemToGUI(item);
            }
        }

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
