package de.ventority.randomizedminigames.gui.windows;

import de.ventority.randomizedminigames.gui.BaseWindow;
import de.ventority.randomizedminigames.gui.NBTHelper;
import de.ventority.randomizedminigames.util.MinigameHandler;
import de.ventority.randomizedminigames.util.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerSelectionWindow extends BaseWindow {

    public PlayerSelectionWindow(Player p) {
        super(p);
    }

    @Override
    protected void fillGUI() {
        Settings settings = MinigameHandler.getSettings(p);
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!MinigameHandler.getOccupiedPlayers().contains(online)) {
                boolean selected = settings.getSelectedPlayers().contains(online);
                ItemStack head = makePlayerHead(online, selected);
                NBTHelper.set(head, "Action", "clickedPlayer");
                NBTHelper.set(head, "Player", online.getName());
                add(head);
            }
        }

        ItemStack back = makeItem(Material.ARROW, "Back");
        NBTHelper.set(back, "Action", "back");
        place(49, back);
    }

    @Override
    public void handleClick(String action, InventoryClickEvent event) {
        if (action.equals("back")) {
            new MinigameSetupWindow(p).buildWindow();
            return;
        }
        if (action.equals("clickedPlayer")) {
            String playerName = NBTHelper.get(event.getCurrentItem(), "Player");
            Player target = Bukkit.getPlayer(playerName);
            if (target == null) return;
            Settings settings = MinigameHandler.getSettings(p);
            if (settings.getSelectedPlayers().contains(target)) {
                settings.removePlayersFromSelection(target);
            } else {
                settings.addPlayersToSelection(target);
            }
            new PlayerSelectionWindow(p).buildWindow();
        }
    }

    private ItemStack makePlayerHead(Player target, boolean selected) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta != null) {
            meta.setOwningPlayer(target);
            meta.setDisplayName((selected ? ChatColor.GREEN : ChatColor.RED) + target.getName());
            head.setItemMeta(meta);
        }
        return head;
    }
}
