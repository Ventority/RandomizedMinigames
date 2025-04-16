package de.ventority.randomizedminigames;

import de.ventority.randomizedminigames.GUI.GUIClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomizedMinigames extends JavaPlugin {
    public static final ServerSettingsHandler serverSettingsHandler = new ServerSettingsHandler();
    public static String keyword = "";
    @Override
    public void onEnable() {
        init();
        getServer().getPluginManager().registerEvents(new GUIClickEvent(), this);
        this.getCommand("minigames").setExecutor(new SelectMinigame());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void init() {
        serverSettingsHandler.setPlugin(this);
    }
}
