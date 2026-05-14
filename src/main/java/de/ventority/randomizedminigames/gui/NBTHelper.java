package de.ventority.randomizedminigames.gui;

import de.ventority.randomizedminigames.RandomizedMinigames;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class NBTHelper {
    private NBTHelper() {}

    public static void set(ItemStack item, String key, String value) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        NamespacedKey nsKey = new NamespacedKey(RandomizedMinigames.serverSettingsHandler.getPlugin(), key);
        meta.getPersistentDataContainer().set(nsKey, PersistentDataType.STRING, value);
        item.setItemMeta(meta);
    }

    public static String get(ItemStack item, String key) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        NamespacedKey nsKey = new NamespacedKey(RandomizedMinigames.serverSettingsHandler.getPlugin(), key);
        return meta.getPersistentDataContainer().get(nsKey, PersistentDataType.STRING);
    }
}
