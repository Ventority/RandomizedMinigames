package de.ventority.randomizedminigames.GUI.MinigameSetups;

import de.ventority.randomizedminigames.GUI.BaseWindow;
import de.ventority.randomizedminigames.misc.MinigameHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TeamCountSelection extends BaseWindow {
    public TeamCountSelection(Player p) {
        super(p);
    }


    @Override
    protected void fillGUI() {
        ItemStack add = new ItemStack(Material.RED_DYE);
        addNBT(add, "Type", "MinigameSetup");
        addNBT(add, "Action", "addTeamCount");
        setItemName(add, "Add");
        addItemToGUI(14, add);

        ItemStack sub = new ItemStack(Material.GREEN_DYE);
        addNBT(sub, "Type", "MinigameSetup");
        addNBT(sub, "Action", "subTeamCount");
        setItemName(sub, "Sub");
        addItemToGUI(12, sub);

        ItemStack display = new ItemStack(Material.PAPER);
        addNBT(display, "Type", "MinigameSetup");
        addNBT(display, "Action", "none");
        setItemName(display, "" + MinigameHandler.getSettings(p).getTeamCount());
        addItemToGUI(13, display);

        ItemStack next = new ItemStack(Material.ARROW);
        addNBT(next, "Type", "MinigameSetup");
        addNBT(next, "Action", "TeamSetupSelection");
        setItemName(next, "Next");
        addItemToGUI(49, next);
    }
}
