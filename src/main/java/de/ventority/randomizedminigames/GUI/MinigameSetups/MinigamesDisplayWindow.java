package de.ventority.randomizedminigames.GUI.MinigameSetups;

import de.ventority.randomizedminigames.GUI.BaseWindow;
import de.ventority.randomizedminigames.Minigames.Minigame;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MinigamesDisplayWindow extends BaseWindow {

    public MinigamesDisplayWindow(Player p, String status) {
        super(p, status);
    }

    @Override
    protected void fillGUI() {
        for (Minigame minigame : Minigame.values()) {
            ItemStack item = new ItemStack(minigame.getMaterial(), 1);
            setItemName(item, minigame.getName());
            addNBT(item, "Type", "MinigameSelect");
            addNBT(item, "Action", "openMinigameSetup");
            addNBT(item, "selectedMinigame", Integer.toString(minigame.getNumber()));
            addItemToGUI(item);
        }
        //addSettings();
    }

    private void addSettings() {
        ItemStack settings = new ItemStack(Material.REDSTONE, 1);
        ItemMeta settingsMeta = settings.getItemMeta();
        setItemName(settings, "Settings");
        addNBT(settings, "Type", "Misc");
        addNBT(settings, "Action", "selectSettings");
        settings.setItemMeta(settingsMeta);
        addItemToGUI(45, settings);
    }
}
