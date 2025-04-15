package de.ventority.randomizedminigames.Minigames;

import org.bukkit.Material;

public enum Minigame {
    ForceItemBattle(Material.DIAMOND_SWORD, "Force Item Battle", 0);

    Material material;
    String name;
    int number;
    Minigame(Material m, String s, int i) {
        material = m;
        name = s;
        number = i;
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
}
