package de.ventority.randomizedminigames;

import de.ventority.randomizedminigames.gui.GUIClickEvent;
import de.ventority.randomizedminigames.util.MinigameHandler;
import de.ventority.randomizedminigames.util.PlayerBackupHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomizedMinigames extends JavaPlugin implements Listener {
    public static final ServerSettingsHandler serverSettingsHandler = new ServerSettingsHandler();

    @Override
    public void onEnable() {
        init();
        getServer().getPluginManager().registerEvents(new GUIClickEvent(), this);
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("minigames").setExecutor(new executeMinigame());
    }

    @Override
    public void onDisable() {
        MinigameHandler.killAll();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        PlayerBackupHandler.restoreIfPending(e.getPlayer());
    }

    private void init() {
        serverSettingsHandler.setPlugin(this);
    }

}
