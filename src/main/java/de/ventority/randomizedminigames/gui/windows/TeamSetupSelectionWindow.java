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

public class TeamSetupSelectionWindow extends BaseWindow {
    private static final ChatColor[] TEAM_COLORS = {
            ChatColor.GREEN, ChatColor.RED, ChatColor.BLUE,
            ChatColor.DARK_PURPLE, ChatColor.BLACK, ChatColor.DARK_GREEN
    };
    private static final Material[] TEAM_MATERIALS = {
            Material.GREEN_WOOL, Material.RED_WOOL, Material.BLUE_WOOL,
            Material.PURPLE_WOOL, Material.BLACK_WOOL, Material.GREEN_WOOL
    };

    public TeamSetupSelectionWindow(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        Settings settings = MinigameHandler.getSettings(p);
        for (int i = 0; i < settings.getTeamCount(); i++) {
            int playerCount = settings.getTeam(i).getPlayers().size();
            String name = TEAM_COLORS[i] + "Team #" + (i + 1) + ChatColor.GRAY + " (" + playerCount + " players)";
            ItemStack item = makeItem(TEAM_MATERIALS[i], name);
            NBTHelper.set(item, "Action", "selectTeam");
            NBTHelper.set(item, "selectedTeam", Integer.toString(i));
            add(item);
        }

        ItemStack start = makeItem(Material.GREEN_DYE, "Start Game");
        NBTHelper.set(start, "Action", "startGame");
        place(50, start);
    }

    @Override
    public void handleClick(String action, InventoryClickEvent event) {
        if (action.equals("selectTeam")) {
            String teamIndex = NBTHelper.get(event.getCurrentItem(), "selectedTeam");
            if (teamIndex == null) return;
            MinigameHandler.getSettings(p).setSelectedTeamIndex(Integer.parseInt(teamIndex));
            new TeamPlayerSelectionWindow(p).buildWindow();
        } else if (action.equals("startGame")) {
            MinigameHandler.createMinigame(3, p);
            p.closeInventory();
        }
    }
}
