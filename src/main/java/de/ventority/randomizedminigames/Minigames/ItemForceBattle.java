package de.ventority.randomizedminigames.Minigames;

import de.ventority.randomizedminigames.GUI.GamesScoreboardManager;
import de.ventority.randomizedminigames.MinigameHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ItemForceBattle implements Minigame, Listener {
    private final HashMap<Player, ItemStack> currentItems;
    private final HashMap<Player, Integer> currentScores;
    private final HashMap<Player, BossBar> itemDisplays;
    private final int id;
    private GamesScoreboardManager scoreboardManager;
    private final List<Player> contestants;


    public ItemForceBattle(List<Player> players) {
        id = new Random().nextInt(1024);
        currentItems = new HashMap<>();
        currentScores = new HashMap<>();
        itemDisplays = new HashMap<>();
        contestants = players;
        for (Player player : players) {
            currentItems.put(player, null);
            currentScores.put(player, 0);
            BossBar bar = Bukkit.createBossBar("Hallo", BarColor.PURPLE, BarStyle.SOLID);
            bar.addPlayer(player);
            bar.setVisible(true);
            bar.setTitle("Das ist ein Test");
            itemDisplays.put(player, bar);
            System.out.println(itemDisplays.get(player).getTitle());
            updatePlayerItem(player);
        }
        scoreboardManager = new GamesScoreboardManager(players, getName());
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getName() {
        return "Force Item Battle";
    }

    @Override
    public int getPlayerIntervals() {
        return 0;
    }

    @Override
    public int getMinPlayers() {
        return 2;
    }

    @Override
    public int getMaxPlayers() {
        return -1;
    }

    @Override
    public ItemStack getSymbol() {
        return new ItemStack(Material.DIAMOND_SWORD, 1);
    }

    @Override
    public void addPlayers(List<Player> players) {

    }

    private ItemStack getRandomItem() {
        Material m;
        do {
            int i = new Random().nextInt(0, Material.values().length);
            m = Material.values()[i];
        } while (!m.isItem());
        return new ItemStack(m);
    }

    private void checkItem(Player p, ItemStack i) {
        if (i == null) return;
        if (currentItems.get(p).getType() == i.getType()) {
            currentScores.replace(p, currentScores.get(p) + 1);
            scoreboardManager.setPunkte(p.getName(), scoreboardManager.getPunkte(p.getName()) + 1);
            if (checkWin(p)) {
                stopGame(p);
            }
            updatePlayerItem(p);
        }
    }

    private void updatePlayerItem(Player player) {
        currentItems.replace(player, getRandomItem());
        String key = currentItems.get(player).getType().getTranslationKey();
        String displayName = key.replace("block.minecraft.", "")
                .replace("_", " ")
                .replace("item.minecraft.", "");
        itemDisplays.get(player).setTitle(displayName);
        ItemStack[] contents = player.getInventory().getContents();
        for (ItemStack item : contents)
            checkItem(player, item);
    }

    private boolean checkWin(Player p) {
        if (currentScores.get(p) == 3) {
            System.out.println(currentScores.get(p) + ", " + p.getName() + " wins");
            return true;
        }
        return false;
    }

    private void stopGame(Player winner) {
        for (Player player : contestants) {
            if (player != winner) {
                player.teleport(winner);
                player.setGameMode(GameMode.SPECTATOR);
                
            }
        }
        for (Player p : itemDisplays.keySet()) {
            itemDisplays.get(p).setVisible(false);
            itemDisplays.get(p).removePlayer(p);
        }
        itemDisplays.clear();

        scoreboardManager.removeScoreboard();
        scoreboardManager = null;
        MinigameHandler.deleteGame(this);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        Player eventPlayer;
        if (e.getEntity() instanceof Player)
            eventPlayer = (Player) e.getEntity();
        else
            return;
        if (currentItems.containsKey(eventPlayer))
            if (e.getItem().getItemStack().getType() == currentItems.get(eventPlayer).getType())
                checkItem(eventPlayer, e.getItem().getItemStack());
    }
}
