package de.ventority.randomizedminigames.GUI.MinigameSetups;

import de.ventority.randomizedminigames.GUI.BaseWindow;
import de.ventority.randomizedminigames.RandomizedMinigames;
import de.ventority.randomizedminigames.misc.MinigameHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetLimit extends BaseWindow {
    public SetLimit(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        ItemStack add = new ItemStack(Material.RED_DYE);
        addNBT(add, "Type", "MinigameSetup");
        addNBT(add, "Action", "addToLimit");
        setItemName(add, "Add");
        addItemToGUI(14, add);

        ItemStack sub = new ItemStack(Material.GREEN_DYE);
        addNBT(sub, "Type", "MinigameSetup");
        addNBT(sub, "Action", "subFromLimit");
        setItemName(sub, "Sub");
        addItemToGUI(12, sub);

        ItemStack display = new ItemStack(Material.PAPER);
        addNBT(display, "Type", "MinigameSetup");
        addNBT(display, "Action", "none");
        setItemName(display, "" + MinigameHandler.getSettings(p).getSelectedLimit());
        addItemToGUI(13, display);

        ItemStack back = new ItemStack(Material.ARROW);
        addNBT(back, "Type", "MinigameSetup");
        addNBT(back, "Action", "homeMenu");
        setItemName(back, "Back");
        addItemToGUI(49, back);
    }
}
