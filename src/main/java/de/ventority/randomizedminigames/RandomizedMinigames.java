package de.ventority.randomizedminigames;

import de.ventority.randomizedminigames.GUI.GUIClickEvent;
import de.ventority.randomizedminigames.Minigames.ItemForceBattle;
import de.ventority.randomizedminigames.Minigames.Minigame;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class RandomizedMinigames extends JavaPlugin {
    public static final ServerSettingsHandler serverSettingsHandler = new ServerSettingsHandler();
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
