package de.ventority.randomizedminigames.Minigames;

import org.bukkit.Material;

public enum Minigame {
    ForceItemBattle(Material.DIAMOND_SWORD, "Force Item Battle", 0, "startForceItem"),
    ForceItemBattleSameItems(Material.NETHERITE_SWORD, "Force Item Battle (Same Items)", 1, "startForceItemSameItem"),;

    final Material material;
    final String name;
    final int number;
    final String action;

    Minigame(Material m, String s, int i, String action) {
        material = m;
        name = s;
        number = i;
        this.action = action;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getAction() {
        return action;
    }
}
