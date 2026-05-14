package de.ventority.randomizedminigames.gui.windows;

import de.ventority.randomizedminigames.gui.BaseWindow;
import de.ventority.randomizedminigames.gui.NBTHelper;
import de.ventority.randomizedminigames.util.MinigameHandler;
import de.ventority.randomizedminigames.util.Settings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class TeamCountSelectionWindow extends BaseWindow {

    public TeamCountSelectionWindow(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        Settings settings = MinigameHandler.getSettings(p);

        ItemStack sub = makeItem(Material.GREEN_DYE, "Sub");
        NBTHelper.set(sub, "Action", "subTeamCount");
        place(12, sub);

        ItemStack display = makeItem(Material.PAPER, String.valueOf(settings.getTeamCount()));
        NBTHelper.set(display, "Action", "none");
        place(13, display);

        ItemStack add = makeItem(Material.RED_DYE, "Add");
        NBTHelper.set(add, "Action", "addTeamCount");
        place(14, add);

        ItemStack next = makeItem(Material.ARROW, "Next");
        NBTHelper.set(next, "Action", "next");
        place(49, next);
    }

    @Override
    public void handleClick(String action, InventoryClickEvent event) {
        Settings settings = MinigameHandler.getSettings(p);
        switch (action) {
            case "addTeamCount":
                settings.addTeamCount();
                new TeamCountSelectionWindow(p).buildWindow();
                break;
            case "subTeamCount":
                settings.subTeamCount();
                new TeamCountSelectionWindow(p).buildWindow();
                break;
            case "next":
                MinigameHandler.getSettings(p).initTeams();
                new TeamSetupSelectionWindow(p).buildWindow();
                break;
        }
    }
}
