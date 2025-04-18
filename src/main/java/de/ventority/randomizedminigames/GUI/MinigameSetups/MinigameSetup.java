package de.ventority.randomizedminigames.GUI.MinigameSetups;

import de.ventority.randomizedminigames.GUI.BaseWindow;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MinigameSetup extends BaseWindow {

    public MinigameSetup(Player p, String status) {
        super(p, status);
    }

    @Override
    protected void fillGUI() {
        ItemStack playerSelect = new ItemStack(Material.GOLD_INGOT);
        addNBT(playerSelect, "Type", "MinigameSetup");
        addNBT(playerSelect, "Action", "startPlayerSelection");
        addNBT(playerSelect, "selectedMinigame", status);
        setItemName(playerSelect, "Select Players");
        addItemToGUI(playerSelect);

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
