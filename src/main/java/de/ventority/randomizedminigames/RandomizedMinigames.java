package de.ventority.randomizedminigames;

import de.ventority.randomizedminigames.GUI.GUIClickEvent;
import de.ventority.randomizedminigames.Minigames.ForceItemBattle;
import de.ventority.randomizedminigames.Minigames.Minigame;
import de.ventority.randomizedminigames.Minigames.MinigameBase;
import de.ventority.randomizedminigames.misc.DataInputHandler;
import de.ventority.randomizedminigames.misc.MinigameHandler;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomizedMinigames extends JavaPlugin implements Listener {
    public static final ServerSettingsHandler serverSettingsHandler = new ServerSettingsHandler();
    public static DataInputHandler dataInputHandler = new DataInputHandler();

    @Override
    public void onEnable() {
        init();
        getServer().getPluginManager().registerEvents(new GUIClickEvent(), this);
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("minigames").setExecutor(new SelectMinigame());
        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        }
    }

    @Override
    public void onDisable() {
        MinigameHandler.killAll();
    }

    private void init() {
        serverSettingsHandler.setPlugin(this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR &&
                event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.BARRIER) return;
        Player p = event.getPlayer();
        try {
            if (MinigameHandler.getMinigames().isEmpty()) return;
            for (MinigameBase minigame : MinigameHandler.getMinigames()) {
                if ((minigame instanceof ForceItemBattle) && minigame.getPlayers().contains(p)) {
                    ((ForceItemBattle) minigame).skipItem(p);
                }
            }
        } catch (Exception ignored) {
        }
        p.sendMessage(ChatColor.GREEN + "Skipped Item.");
        if (item.getAmount() > 1)
            item.setAmount(item.getAmount() - 1);
        else
            p.getInventory().remove(item);
        event.setCancelled(true);
    }
}
