package de.ventority.randomizedminigames.GUI.InGame;

import org.bukkit.Bukkit;
import org.bukkit.boss.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BossBarCreator {

    private final Player player;
    private final BossBar bossBar;

    public BossBarCreator(Player player, String initialTitle) {
        this.player = player;
        this.bossBar = Bukkit.createBossBar(
                initialTitle,
                BarColor.PURPLE,
                BarStyle.SOLID
        );

        bossBar.setProgress(1.0);
        bossBar.setVisible(true);
        bossBar.addPlayer(player);
    }

    /**
     * Setzt den Titel der BossBar.
     *
     * @param title Neuer Titel (Farbcodes mit & erlaubt)
     */
    public void setTitle(String title) {
        bossBar.setTitle(ChatColor.translateAlternateColorCodes('&', title));
    }

    /**
     * Setzt den Fortschritt der BossBar (0.0 bis 1.0).
     */
    public void setProgress(double progress) {
        bossBar.setProgress(Math.max(0.0, Math.min(1.0, progress)));
    }

    /**
     * Blendet die BossBar ein.
     */
    public void show() {
        bossBar.setVisible(true);
        if (!bossBar.getPlayers().contains(player)) {
            bossBar.addPlayer(player);
        }
    }

    /**
     * Blendet die BossBar aus.
     */
    public void hide() {
        bossBar.setVisible(false);
        bossBar.removePlayer(player);
    }

    /**
     * Entfernt die BossBar vollständig.
     */
    public void destroy() {
        hide();
        bossBar.removeAll();
    }

    /**
     * Ändert die Farbe der BossBar.
     */
    public void setColor(BarColor color) {
        bossBar.setColor(color);
    }

    /**
     * Ändert den Stil der BossBar.
     */
    public void setStyle(BarStyle style) {
        bossBar.setStyle(style);
    }
}