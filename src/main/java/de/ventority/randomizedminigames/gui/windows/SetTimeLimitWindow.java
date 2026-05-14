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

public class SetTimeLimitWindow extends BaseWindow {

    public SetTimeLimitWindow(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        Settings settings = MinigameHandler.getSettings(p);

        ItemStack sub30 = makeItem(Material.GREEN_DYE, "-30min");
        NBTHelper.set(sub30, "Action", "sub30");
        place(10, sub30);

        ItemStack sub10 = makeItem(Material.GREEN_DYE, "-10min");
        NBTHelper.set(sub10, "Action", "sub10");
        place(11, sub10);

        ItemStack sub1 = makeItem(Material.GREEN_DYE, "-1min");
        NBTHelper.set(sub1, "Action", "sub1");
        place(12, sub1);

        ItemStack display = makeItem(Material.PAPER, formatTime(settings.getTimeLimit()));
        NBTHelper.set(display, "Action", "none");
        place(13, display);

        ItemStack add1 = makeItem(Material.RED_DYE, "+1min");
        NBTHelper.set(add1, "Action", "add1");
        place(14, add1);

        ItemStack add10 = makeItem(Material.RED_DYE, "+10min");
        NBTHelper.set(add10, "Action", "add10");
        place(15, add10);

        ItemStack add30 = makeItem(Material.RED_DYE, "+30min");
        NBTHelper.set(add30, "Action", "add30");
        place(16, add30);

        ItemStack toggle = settings.isTimed
                ? makeItem(Material.LIME_DYE, ChatColor.GREEN + "Enabled - click to disable")
                : makeItem(Material.GRAY_DYE, ChatColor.RED + "Disabled - click to enable");
        NBTHelper.set(toggle, "Action", "toggleTimer");
        place(22, toggle);

        ItemStack back = makeItem(Material.ARROW, "Back");
        NBTHelper.set(back, "Action", "back");
        place(49, back);
    }

    @Override
    public void handleClick(String action, InventoryClickEvent event) {
        Settings settings = MinigameHandler.getSettings(p);
        switch (action) {
            case "add30": settings.setTimeLimit(settings.getTimeLimit() + 30 * 60); break;
            case "add10": settings.setTimeLimit(settings.getTimeLimit() + 10 * 60); break;
            case "add1":  settings.setTimeLimit(settings.getTimeLimit() + 60); break;
            case "sub30": settings.setTimeLimit(settings.getTimeLimit() - 30 * 60); break;
            case "sub10": settings.setTimeLimit(settings.getTimeLimit() - 10 * 60); break;
            case "sub1":        settings.setTimeLimit(settings.getTimeLimit() - 60); break;
            case "toggleTimer": settings.isTimed = !settings.isTimed; break;
            case "back":        new MinigameSetupWindow(p).buildWindow(); return;
        }
        new SetTimeLimitWindow(p).buildWindow();
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        if (minutes == 0) return String.valueOf(seconds);
        return minutes + ":" + String.format("%02d", seconds);
    }
}
