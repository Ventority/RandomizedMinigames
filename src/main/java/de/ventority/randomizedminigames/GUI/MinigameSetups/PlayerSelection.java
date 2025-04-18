package de.ventority.randomizedminigames.GUI.MinigameSetups;

import de.ventority.randomizedminigames.GUI.BaseWindow;
import de.ventority.randomizedminigames.RandomizedMinigames;
import de.ventority.randomizedminigames.misc.MinigameHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerSelection extends BaseWindow {
    public PlayerSelection(Player p, String status) {
        super(p, status);
    }

    @Override
    protected void fillGUI() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!MinigameHandler.getOccupiedPlayers().contains(player)) {
                ItemStack item = getPlayerHead(player, RandomizedMinigames.dataInputHandler.getSelectedPlayers(p).contains(player));
                addNBT(item, "Type", "MinigameSetup");
                addNBT(item, "Action", "clickedPlayer");
                addNBT(item, "selectedMinigame", status);
                addNBT(item, "Player", player.getDisplayName());
                addItemToGUI(item);
            }
        }

        ItemStack back = new ItemStack(Material.ARROW);
        addNBT(back, "Type", "MinigameSetup");
        addNBT(back, "Action", "homeMenu");
        addNBT(back, "selectedMinigame", status);
        setItemName(back, "Back");
        addItemToGUI(49, back);
    }

    private ItemStack getPlayerHead(Player player, boolean selected) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta != null) {
            meta.setOwningPlayer(player);
            meta.setDisplayName((selected ? ChatColor.GREEN : ChatColor.RED) + player.getName());
            head.setItemMeta(meta);
        }
        return head;
    }
}
