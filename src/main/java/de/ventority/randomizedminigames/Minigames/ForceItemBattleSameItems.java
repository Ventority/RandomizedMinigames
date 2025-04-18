package de.ventority.randomizedminigames.Minigames;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForceItemBattleSameItems extends ForceItemBattle {
    private  List<ItemStack> items;


    public ForceItemBattleSameItems(List<Player> players, Player owner) {
        super(players, owner);
    }

    @Override
    protected void updatePlayerItem(Player player) {
        if (this.items == null || this.items.isEmpty()) {
            items = new ArrayList<>();
            items.add(getRandomItem());
        }
        if (currentScores.get(player) == items.size()) {
            items.add(getRandomItem());
        }
        currentItems.replace(player, items.get(currentScores.get(player)));
        String key = currentItems.get(player).getType().getTranslationKey();
        itemDisplays.get(player).setTitle(key.replace("block.minecraft.", "")
                                                .replace("_", " ")
                                                .replace("item.minecraft.", ""));
        for (ItemStack item : player.getInventory().getContents())
            checkItem(player, item);
    }
}
