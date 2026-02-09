package de.ventority.randomizedminigames.util;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class PlayerBackupHandler {
    private HashMap<Player, PlayerBackup> playerBackups = new HashMap<>();

    public PlayerBackupHandler(List<Player> players) {
        for (Player player : players) {
            PlayerBackup backup = new PlayerBackup(player.getInventory().getContents(), player.getInventory().getArmorContents(), player.getExp(), player.getLocation(), player.getGameMode());
            playerBackups.put(player, backup);
        }
    }

    public void restorePlayerBackup(Player player) {
        PlayerBackup backup = playerBackups.get(player);
        player.getInventory().setContents(backup.getInventory());
        player.getInventory().setArmorContents(backup.getArmor());
        player.setExp(backup.getExp());
        player.teleport(backup.getLocation());
        player.setGameMode(backup.getGamemode());
    }

    public void restoreAll() {
        for (Player player : playerBackups.keySet()) {
            restorePlayerBackup(player);
        }
    }
}
