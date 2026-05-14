package de.ventority.randomizedminigames.gui.windows;

import de.ventority.randomizedminigames.gui.BaseWindow;
import de.ventority.randomizedminigames.gui.NBTHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SettingsWindow extends BaseWindow {

    public SettingsWindow(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        ItemStack setContestants = makeItem(Material.ZOMBIE_HEAD, "Add Contestants");
        NBTHelper.set(setContestants, "Action", "addContestants");
        add(setContestants);
    }

    @Override
    public void handleClick(String action, InventoryClickEvent event) {
        // Not yet implemented
    }
}
