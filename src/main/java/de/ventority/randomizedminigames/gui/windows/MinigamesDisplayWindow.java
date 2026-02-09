package de.ventority.randomizedminigames.gui.windows;

import de.ventority.randomizedminigames.gui.BaseWindow;
import de.ventority.randomizedminigames.Minigames.Minigame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MinigamesDisplayWindow extends BaseWindow {

    public MinigamesDisplayWindow(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        for (Minigame minigame : Minigame.values()) {
            ItemStack item = new ItemStack(minigame.getMaterial(), 1);
            setItemName(item, minigame.getName());
            addNBT(item, "Type", "MinigameSelect");
            addNBT(item, "Action", minigame.getAction());
            addNBT(item, "selectedMinigame", Integer.toString(minigame.getNumber()));
            addItemToGUI(item);
        }
    }
}
