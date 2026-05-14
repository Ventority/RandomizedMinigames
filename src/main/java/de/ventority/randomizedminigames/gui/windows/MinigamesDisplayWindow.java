package de.ventority.randomizedminigames.gui.windows;

import de.ventority.randomizedminigames.Minigames.Minigame;
import de.ventority.randomizedminigames.gui.BaseWindow;
import de.ventority.randomizedminigames.gui.NBTHelper;
import de.ventority.randomizedminigames.util.MinigameHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MinigamesDisplayWindow extends BaseWindow {

    public MinigamesDisplayWindow(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        for (Minigame minigame : Minigame.values()) {
            ItemStack item = makeItem(minigame.getMaterial(), minigame.getName());
            NBTHelper.set(item, "Action", minigame.getAction());
            NBTHelper.set(item, "selectedMinigame", Integer.toString(minigame.getNumber()));
            add(item);
        }
    }

    @Override
    public void handleClick(String action, InventoryClickEvent event) {
        String selectedMinigame = NBTHelper.get(event.getCurrentItem(), "selectedMinigame");
        if (selectedMinigame != null) {
            MinigameHandler.getSettings(p).selectMinigame(Integer.parseInt(selectedMinigame));
        }
        if (action.equals("startForceItemTeams")) {
            new TeamCountSelectionWindow(p).buildWindow();
        } else {
            new MinigameSetupWindow(p).buildWindow();
        }
    }
}
