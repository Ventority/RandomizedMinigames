package de.ventority.randomizedminigames.gui.windows;

import de.ventority.randomizedminigames.gui.BaseWindow;
import de.ventority.randomizedminigames.gui.NBTHelper;
import de.ventority.randomizedminigames.util.MinigameHandler;
import de.ventority.randomizedminigames.util.Settings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SetLimitWindow extends BaseWindow {

    public SetLimitWindow(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        Settings settings = MinigameHandler.getSettings(p);

        ItemStack sub = makeItem(Material.GREEN_DYE, "Sub");
        NBTHelper.set(sub, "Action", "subFromLimit");
        place(12, sub);

        ItemStack display = makeItem(Material.PAPER, String.valueOf(settings.getSelectedLimit()));
        NBTHelper.set(display, "Action", "none");
        place(13, display);

        ItemStack add = makeItem(Material.RED_DYE, "Add");
        NBTHelper.set(add, "Action", "addToLimit");
        place(14, add);

        ItemStack toggle = !settings.isTimed
                ? makeItem(Material.LIME_DYE, ChatColor.GREEN + "Enabled - click to disable")
                : makeItem(Material.GRAY_DYE, ChatColor.RED + "Disabled - click to enable");
        NBTHelper.set(toggle, "Action", "toggleWinLimit");
        place(22, toggle);

        ItemStack back = makeItem(Material.ARROW, "Back");
        NBTHelper.set(back, "Action", "back");
        place(49, back);
    }

    @Override
    public void handleClick(String action, InventoryClickEvent event) {
        Settings settings = MinigameHandler.getSettings(p);
        switch (action) {
            case "addToLimit":
                settings.addToWinLimit();
                new SetLimitWindow(p).buildWindow();
                break;
            case "subFromLimit":
                settings.subFromWinLimit();
                new SetLimitWindow(p).buildWindow();
                break;
            case "toggleWinLimit":
                settings.isTimed = !settings.isTimed;
                new SetLimitWindow(p).buildWindow();
                break;
            case "back":
                new MinigameSetupWindow(p).buildWindow();
                break;
        }
    }
}
