package de.ventority.randomizedminigames.util;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class PlayerBackup {
    private final ItemStack[] inventory;
    private final ItemStack[] armor;
    private final ItemStack offhand;
    private final float exp;
    private final int level;
    private final Location location;
    private final GameMode gamemode;
    private final double health;
    private final int foodLevel;

    public PlayerBackup(ItemStack[] inventory, ItemStack[] armor, ItemStack offhand,
                        float exp, int level, Location location, GameMode gamemode,
                        double health, int foodLevel) {
        this.inventory = inventory;
        this.armor = armor;
        this.offhand = offhand;
        this.exp = exp;
        this.level = level;
        this.location = location;
        this.gamemode = gamemode;
        this.health = health;
        this.foodLevel = foodLevel;
    }

    public ItemStack[] getInventory() { return inventory; }
    public ItemStack[] getArmor()     { return armor; }
    public ItemStack getOffhand()     { return offhand; }
    public float getExp()             { return exp; }
    public int getLevel()             { return level; }
    public Location getLocation()     { return location; }
    public GameMode getGamemode()     { return gamemode; }
    public double getHealth()         { return health; }
    public int getFoodLevel()         { return foodLevel; }
}
