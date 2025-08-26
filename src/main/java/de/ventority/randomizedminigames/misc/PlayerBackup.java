package de.ventority.randomizedminigames.misc;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class PlayerBackup {
    private final ItemStack[] inventory;
    private final ItemStack[] armor;
    private final float exp;
    private final Location location;
    private GameMode gamemode;

    public PlayerBackup(ItemStack[] inventory, ItemStack[] armor, float exp, Location location, GameMode gamemode) {
        this.inventory = inventory;
        this.armor = armor;
        this.exp = exp;
        this.location = location;
        this.gamemode = gamemode;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public float getExp() {
        return exp;
    }

    public Location getLocation() {
        return location;
    }

    public GameMode getGamemode() {
        return gamemode;
    }

}
