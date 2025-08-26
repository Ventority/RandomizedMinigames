package de.ventority.randomizedminigames.GUI.MinigameSetups;

import de.ventority.randomizedminigames.GUI.BaseWindow;
import de.ventority.randomizedminigames.misc.MinigameHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class TeamSetupSelection extends BaseWindow {
    private final ChatColor[] colors = new ChatColor[]{ChatColor.GREEN, ChatColor.RED, ChatColor.BLUE, ChatColor.DARK_PURPLE, ChatColor.BLACK, ChatColor.DARK_GREEN};
    private final Material[] material = new Material[]{Material.GREEN_WOOL, Material.RED_WOOL, Material.BLUE_WOOL, Material.PURPLE_WOOL, Material.BLACK_WOOL, Material.GREEN_WOOL};

    public TeamSetupSelection(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        for (int i = 0; i < MinigameHandler.getSettings(p).getTeamCount(); i++) {
            ItemStack item = new ItemStack(material[i], 1);
            setItemName(item, colors[i] + "Team #" + (i + 1));
            addNBT(item, "Type", "MinigameSetup");
            addNBT(item, "Action", "selectTeam");
            addNBT(item, "selectedTeam", Integer.toString(i));
            addItemToGUI(item);
        }
    }
}
