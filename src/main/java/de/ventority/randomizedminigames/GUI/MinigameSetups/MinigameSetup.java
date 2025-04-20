package de.ventority.randomizedminigames.GUI.MinigameSetups;

import de.ventority.randomizedminigames.GUI.BaseWindow;
import de.ventority.randomizedminigames.RandomizedMinigames;
import de.ventority.randomizedminigames.misc.DataInputHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MinigameSetup extends BaseWindow {

    public MinigameSetup(Player p, String status) {
        super(p, status);
    }

    @Override
    protected void fillGUI() {
        ItemStack playerSelect = new ItemStack(Material.PLAYER_HEAD);
        addNBT(playerSelect, "Type", "MinigameSetup");
        addNBT(playerSelect, "Action", "startPlayerSelection");
        addNBT(playerSelect, "selectedMinigame", status);
        setItemName(playerSelect, "Select Players");
        addItemToGUI(playerSelect);

        ItemStack setLimit = new ItemStack(Material.BOOK);
        addNBT(setLimit, "Type", "MinigameSetup");
        addNBT(setLimit, "Action", "startLimitSelection");
        addNBT(setLimit, "selectedMinigame", status);
        setItemName(setLimit, "Set win limit");
        addItemToGUI(setLimit);

        DataInputHandler data = RandomizedMinigames.dataInputHandler;
        ItemStack scoreboard = new ItemStack(Material.EMERALD);
        addNBT(scoreboard, "Type", "MinigameSetup");
        addNBT(scoreboard, "Action", "switchScoreboard");
        addNBT(scoreboard, "selectedMinigame", status);
        setItemName(scoreboard, "Scoreboard: " + (data.getScoreboardStatus() ? (ChatColor.GREEN + "On") : ChatColor.RED + "Off"));
        addItemToGUI(scoreboard);

        ItemStack start = new ItemStack(Material.GREEN_DYE);
        addNBT(start, "Type", "MinigameSetup");
        addNBT(start, "Action", "startGame");
        addNBT(start, "selectedMinigame", status);
        setItemName(start, "Start");
        addItemToGUI(50, start);


        ItemStack back = new ItemStack(Material.ARROW);
        addNBT(back, "Type", "MinigameSelect");
        addNBT(back, "Action", "homeMenu");
        setItemName(back, "Back");
        addItemToGUI(49, back);
    }
}
