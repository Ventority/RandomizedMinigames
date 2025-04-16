package de.ventority.randomizedminigames.GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SettingsWindow extends BaseWindow{

    public SettingsWindow(Player p, String status) {
        super(p, status);
    }

    @Override
    public void fillGUI() {
        ItemStack setContestants = new ItemStack(Material.ZOMBIE_HEAD, 1);
        setItemName(setContestants, "Add Contestants");
        addNBT(setContestants, "Status", "addContestants");
        gui.addItem(setContestants);
    }
}
