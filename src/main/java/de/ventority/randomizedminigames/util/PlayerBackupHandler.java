package de.ventority.randomizedminigames.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerBackupHandler {
    // Backups for players currently in a minigame, keyed by UUID so they survive reconnects.
    private final HashMap<UUID, PlayerBackup> playerBackups = new HashMap<>();

    // Players who were offline when killGame() ran — restored the next time they join.
    private static final HashMap<UUID, PlayerBackup> pendingRestores = new HashMap<>();

    public PlayerBackupHandler(List<Player> players) {
        for (Player player : players) {
            playerBackups.put(player.getUniqueId(), new PlayerBackup(
                    clone(player.getInventory().getContents()),
                    clone(player.getInventory().getArmorContents()),
                    cloneItem(player.getInventory().getItemInOffHand()),
                    player.getExp(),
                    player.getLevel(),
                    player.getLocation(),
                    player.getGameMode(),
                    player.getHealth(),
                    player.getFoodLevel()
            ));
        }
    }

    public void restorePlayerBackup(Player player) {
        PlayerBackup backup = playerBackups.get(player.getUniqueId());
        if (backup == null) return;
        applyBackup(player, backup);
    }

    /**
     * Restores online players immediately.
     * Players who are currently offline are queued in pendingRestores
     * and will be restored the next time they join.
     */
    public void restoreAll() {
        for (UUID uuid : playerBackups.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                applyBackup(player, playerBackups.get(uuid));
            } else {
                pendingRestores.put(uuid, playerBackups.get(uuid));
            }
        }
    }

    /** Called on every PlayerJoinEvent — restores any backup queued while the player was offline. */
    public static void restoreIfPending(Player player) {
        PlayerBackup backup = pendingRestores.remove(player.getUniqueId());
        if (backup != null) applyBackup(player, backup);
    }

    private static void applyBackup(Player player, PlayerBackup backup) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        Collection<PotionEffect> effects = player.getActivePotionEffects();
        for (PotionEffect effect : effects)
            player.removePotionEffect(effect.getType());

        player.getInventory().setContents(backup.getInventory());
        player.getInventory().setArmorContents(backup.getArmor());
        player.getInventory().setItemInOffHand(backup.getOffhand());
        player.setExp(backup.getExp());
        player.setLevel(backup.getLevel());
        player.setHealth(backup.getHealth());
        player.setFoodLevel(backup.getFoodLevel());
        player.teleport(backup.getLocation());
        player.setGameMode(backup.getGamemode());
    }

    private static org.bukkit.inventory.ItemStack[] clone(org.bukkit.inventory.ItemStack[] source) {
        org.bukkit.inventory.ItemStack[] copy = new org.bukkit.inventory.ItemStack[source.length];
        for (int i = 0; i < source.length; i++)
            copy[i] = source[i] != null ? source[i].clone() : null;
        return copy;
    }

    private static org.bukkit.inventory.ItemStack cloneItem(org.bukkit.inventory.ItemStack item) {
        return item != null ? item.clone() : null;
    }
}
