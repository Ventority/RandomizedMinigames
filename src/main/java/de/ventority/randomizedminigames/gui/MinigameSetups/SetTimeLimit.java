package de.ventority.randomizedminigames.gui.MinigameSetups;

import de.ventority.randomizedminigames.gui.BaseWindow;
import de.ventority.randomizedminigames.util.MinigameHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetTimeLimit extends BaseWindow {
    public SetTimeLimit(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        ItemStack add30 = new ItemStack(Material.RED_DYE);
        addNBT(add30, "Type", "MinigameSetup");
        addNBT(add30, "Action", "add30ToTimeLimit");
        setItemName(add30, "+30min");
        addItemToGUI(16, add30);

        ItemStack add10 = new ItemStack(Material.RED_DYE);
        addNBT(add10, "Type", "MinigameSetup");
        addNBT(add10, "Action", "add10ToTimeLimit");
        setItemName(add10, "+10min");
        addItemToGUI(15, add10);

        ItemStack add1 = new ItemStack(Material.RED_DYE);
        addNBT(add1, "Type", "MinigameSetup");
        addNBT(add1, "Action", "add1ToTimeLimit");
        setItemName(add1, "+1min");
        addItemToGUI(14, add1);

        ItemStack sub30 = new ItemStack(Material.GREEN_DYE);
        addNBT(sub30, "Type", "MinigameSetup");
        addNBT(sub30, "Action", "sub30FromTimeLimit");
        setItemName(sub30, "-30min");
        addItemToGUI(10, sub30);

        ItemStack sub10 = new ItemStack(Material.GREEN_DYE);
        addNBT(sub10, "Type", "MinigameSetup");
        addNBT(sub10, "Action", "sub10FromTimeLimit");
        setItemName(sub10, "-10min");
        addItemToGUI(11, sub10);

        ItemStack sub1 = new ItemStack(Material.GREEN_DYE);
        addNBT(sub1, "Type", "MinigameSetup");
        addNBT(sub1, "Action", "sub1FromTimeLimit");
        setItemName(sub1, "-1min");
        addItemToGUI(12, sub1);

        ItemStack display = new ItemStack(Material.PAPER);
        addNBT(display, "Type", "MinigameSetup");
        addNBT(display, "Action", "none");
        setItemName(display, formattedTime(MinigameHandler.getSettings(p).getTimeLimit()));
        addItemToGUI(13, display);

        ItemStack back = new ItemStack(Material.ARROW);
        addNBT(back, "Type", "MinigameSetup");
        addNBT(back, "Action", "homeMenu");
        setItemName(back, "Back");
        addItemToGUI(49, back);
    }

    private String formattedTime(int time) {
        int minutes = (int)Math.floor((double) time / 60);
        int seconds = time % 60;
        StringBuilder  sb = new StringBuilder();
        if (minutes != 0) {
            sb.append(minutes).append(":");
        }
        sb.append(seconds);
        return sb.toString();
    }
}
