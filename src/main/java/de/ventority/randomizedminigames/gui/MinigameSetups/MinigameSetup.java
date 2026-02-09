package de.ventority.randomizedminigames.gui.MinigameSetups;

import de.ventority.randomizedminigames.gui.BaseWindow;
import de.ventority.randomizedminigames.util.MinigameHandler;
import de.ventority.randomizedminigames.util.Settings;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MinigameSetup extends BaseWindow {

    public MinigameSetup(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        ItemStack playerSelect = new ItemStack(Material.PLAYER_HEAD);
        addNBT(playerSelect, "Type", "MinigameSetup");
        addNBT(playerSelect, "Action", "startPlayerSelection");
        setItemName(playerSelect, "Select Players");
        addItemToGUI(playerSelect);

        ItemStack setLimit = new ItemStack(Material.BOOK);
        addNBT(setLimit, "Type", "MinigameSetup");
        addNBT(setLimit, "Action", "startLimitSelection");
        setItemName(setLimit, "Set win limit");
        addItemToGUI(setLimit);

        Settings playerSettings = MinigameHandler.getSettings(p);
        ItemStack scoreboard = new ItemStack(Material.EMERALD);
        addNBT(scoreboard, "Type", "MinigameSetup");
        addNBT(scoreboard, "Action", "switchScoreboard");
        setItemName(scoreboard, "Scoreboard: " + (playerSettings.getScoreboardStatus() ? (ChatColor.GREEN + "On") : ChatColor.RED + "Off"));
        addItemToGUI(scoreboard);

        ItemStack start = new ItemStack(Material.GREEN_DYE);
        addNBT(start, "Type", "MinigameSetup");
        addNBT(start, "Action", "startGame");
        setItemName(start, "Start");
        addItemToGUI(50, start);

        ItemStack timer = new ItemStack(Material.CLOCK);
        addNBT(timer, "Type", "MinigameSetup");
        addNBT(timer, "Action", "startTimerSetup");
        setItemName(timer, "Set Timer");
        addItemToGUI(timer);

        ItemStack enableTimer = new ItemStack(Material.TARGET);
        addNBT(enableTimer, "Type", "MinigameSetup");
        addNBT(enableTimer, "Action", "toggleTimer");
        setItemName(enableTimer, "Timer: " + (playerSettings.isTimed ? (ChatColor.GREEN + "On") : ChatColor.RED + "Off"));
        addItemToGUI(enableTimer);

        ItemStack back = new ItemStack(Material.ARROW);
        addNBT(back, "Type", "MinigameSelect");
        addNBT(back, "Action", "homeMenu");
        setItemName(back, "Back");
        addItemToGUI(49, back);
    }
}
