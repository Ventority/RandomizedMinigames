package de.ventority.randomizedminigames;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ServerSettingsHandler {
    private PluginManager pm;
    private Plugin plugin;

    private String ServerName = "§lServerName§7";


    public void setPluginManager(PluginManager pm) {
        this.pm = pm;
    }

    public void setServerName(String ServerName) {
        this.ServerName = ServerName;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public String getServerName() {
        return ServerName;
    }

    public PluginManager getPluginManager() {
        return pm;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String keyword;
}
