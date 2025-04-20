package de.ventority.randomizedminigames.GUI.MinigameSetups;

import de.ventority.randomizedminigames.GUI.BaseWindow;
import de.ventority.randomizedminigames.RandomizedMinigames;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetLimit extends BaseWindow {
    public SetLimit(Player p, String status) {
        super(p, status);
    }

    @Override
    protected void fillGUI() {
        ItemStack add = new ItemStack(Material.RED_DYE);
        addNBT(add, "Type", "MinigameSetup");
        addNBT(add, "Action", "addToLimit");
        addNBT(add, "selectedMinigame", status);
        setItemName(add, "Add");
        addItemToGUI(14, add);

        ItemStack sub = new ItemStack(Material.GREEN_DYE);
        addNBT(sub, "Type", "MinigameSetup");
        addNBT(sub, "Action", "subFromLimit");
        addNBT(sub, "selectedMinigame", status);
        setItemName(sub, "Sub");
        addItemToGUI(12, sub);

        ItemStack display = new ItemStack(Material.PAPER);
        addNBT(display, "Type", "MinigameSetup");
        addNBT(display, "Action", "none");
        addNBT(display, "selectedMinigame", status);
        setItemName(display, RandomizedMinigames.dataInputHandler.getSelectedLimit(p).toString());
        addItemToGUI(13, display);

        ItemStack back = new ItemStack(Material.ARROW);
        addNBT(back, "Type", "MinigameSetup");
        addNBT(back, "Action", "homeMenu");
        addNBT(back, "selectedMinigame", status);
        setItemName(back, "Back");
        addItemToGUI(49, back);
    }
}
