package de.ventority.randomizedminigames;

import de.ventority.randomizedminigames.GUI.GUIClickEvent;
import de.ventority.randomizedminigames.misc.DataInputHandler;
import de.ventority.randomizedminigames.misc.MinigameHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomizedMinigames extends JavaPlugin {
    public static final ServerSettingsHandler serverSettingsHandler = new ServerSettingsHandler();
    public static DataInputHandler dataInputHandler = new DataInputHandler();
    @Override
    public void onEnable() {
        init();
        getServer().getPluginManager().registerEvents(new GUIClickEvent(), this);
        this.getCommand("minigames").setExecutor(new SelectMinigame());
    }

    @Override
    public void onDisable() {
        MinigameHandler.killAll();
    }

    private void init() {
        serverSettingsHandler.setPlugin(this);
    }
}
