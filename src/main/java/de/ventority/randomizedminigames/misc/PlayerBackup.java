package de.ventority.randomizedminigames.misc;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class PlayerBackup {
    private final ItemStack[] inventory;
    private final ItemStack[] armor;
    private final float exp;
    private final Location location;

    public PlayerBackup(ItemStack[] inventory, ItemStack[] armor, float exp, Location location) {
        this.inventory = inventory;
        this.armor = armor;
        this.exp = exp;
        this.location = location;
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

}
