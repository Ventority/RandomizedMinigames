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

public class MinigameSetupWindow extends BaseWindow {

    public MinigameSetupWindow(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        Settings settings = MinigameHandler.getSettings(p);

        ItemStack playerSelect = makeItem(Material.PLAYER_HEAD, "Select Players");
        NBTHelper.set(playerSelect, "Action", "startPlayerSelection");
        add(playerSelect);

        String limitLabel = !settings.isTimed
                ? ChatColor.GREEN + String.valueOf(settings.getSelectedLimit())
                : ChatColor.RED + "Off";
        ItemStack setLimit = makeItem(Material.BOOK, "Win Limit: " + limitLabel);
        NBTHelper.set(setLimit, "Action", "startLimitSelection");
        add(setLimit);

        ItemStack scoreboard = makeItem(Material.EMERALD,
                "Scoreboard: " + (settings.getScoreboardStatus() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off"));
        NBTHelper.set(scoreboard, "Action", "switchScoreboard");
        add(scoreboard);

        String timerLabel = settings.isTimed
                ? ChatColor.GREEN + formatTime(settings.getTimeLimit())
                : ChatColor.RED + "Off";
        ItemStack timer = makeItem(Material.CLOCK, "Timer: " + timerLabel);
        NBTHelper.set(timer, "Action", "startTimerSetup");
        add(timer);

        ItemStack start = makeItem(Material.GREEN_DYE, "Start");
        NBTHelper.set(start, "Action", "startGame");
        place(50, start);

        ItemStack back = makeItem(Material.ARROW, "Back");
        NBTHelper.set(back, "Action", "back");
        place(49, back);
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        if (minutes == 0) return String.valueOf(seconds);
        return minutes + ":" + String.format("%02d", seconds);
    }

    @Override
    public void handleClick(String action, InventoryClickEvent event) {
        Settings settings = MinigameHandler.getSettings(p);
        switch (action) {
            case "startPlayerSelection":
                new PlayerSelectionWindow(p).buildWindow();
                break;
            case "startLimitSelection":
                new SetLimitWindow(p).buildWindow();
                break;
            case "switchScoreboard":
                settings.switchScoreboard();
                new MinigameSetupWindow(p).buildWindow();
                break;
            case "startTimerSetup":
                new SetTimeLimitWindow(p).buildWindow();
                break;
            case "startGame":
                MinigameHandler.createMinigame(settings.getSelectedMinigame(), p);
                p.closeInventory();
                break;
            case "back":
                new MinigamesDisplayWindow(p).buildWindow();
                break;
        }
    }
}
