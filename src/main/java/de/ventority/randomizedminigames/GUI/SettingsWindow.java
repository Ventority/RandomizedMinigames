package de.ventority.randomizedminigames.GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SettingsWindow extends BaseWindow{

    public SettingsWindow(Player p, String status) {
        super(p, status);
    }

    @Override
    public void fillGUI() {
        Inventory gui = getGUI();
        ItemStack setContestants = new ItemStack(Material.ZOMBIE_HEAD, 1);
        ItemMeta setContestantsMeta = setContestants.getItemMeta();
        setContestantsMeta.setDisplayName("Contestants");
        setContestantsMeta = addNBT(setContestantsMeta, "Status", "addContestants");
        setContestants.setItemMeta(setContestantsMeta);
        gui.addItem(setContestants);
    }


}
