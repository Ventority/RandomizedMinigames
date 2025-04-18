package de.ventority.randomizedminigames.Minigames;

import de.ventority.randomizedminigames.GUI.InGame.GamesScoreboardManager;
import de.ventority.randomizedminigames.RandomizedMinigames;
import de.ventority.randomizedminigames.misc.MinigameHandler;
import de.ventority.randomizedminigames.misc.PlayerBackup;
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
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ForceItemBattle implements MinigameBase, Listener {
    protected final HashMap<Player, ItemStack> currentItems;
    protected final HashMap<Player, Integer> currentScores;
    protected final HashMap<Player, BossBar> itemDisplays;
    private final int id;
    private GamesScoreboardManager scoreboardManager;
    private final List<Player> contestants;
    private final HashMap<Player, PlayerBackup> backups;
    private final Player owner;


    public ForceItemBattle(List<Player> players, Player owner) {
        id = new Random().nextInt(1024);
        currentItems = new HashMap<>();
        currentScores = new HashMap<>();
        itemDisplays = new HashMap<>();
        backups = new HashMap<>();
        contestants = players;
        this.owner = owner;
        for (Player player : players) {
            currentItems.put(player, null);
            currentScores.put(player, 0);
            BossBar bar = Bukkit.createBossBar("Hallo", BarColor.PURPLE, BarStyle.SOLID);
            bar.addPlayer(player);
            bar.setVisible(true);
            itemDisplays.put(player, bar);
            updatePlayerItem(player);
            backups.put(player, new PlayerBackup(player.getInventory().getContents(), player.getInventory().getArmorContents(), player.getExp(), player.getLocation()));
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
    public ItemStack getSymbol() {
        return new ItemStack(Material.DIAMOND_SWORD, 1);
    }

    @Override
    public void addPlayers(List<Player> players) {

    }

    protected ItemStack getRandomItem() {
        Material m;
        do {
            int i = new Random().nextInt(0, Material.values().length);
            m = Material.values()[i];
        } while (!m.isItem());
        return new ItemStack(m);
    }

    protected void checkItem(Player p, ItemStack i) {
        if (i == null) return;
        if (currentItems.get(p).getType() == i.getType()) {
            currentScores.replace(p, currentScores.get(p) + 1);
            scoreboardManager.setPunkte(p.getName(), scoreboardManager.getPunkte(p.getName()) + 1);
            if (checkWin(p)) {
                stopGame(p);
                return;
            }
            updatePlayerItem(p);
        }
    }

    protected void updatePlayerItem(Player player) {
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
            return true;
        }
        return false;
    }

    private void stopGame(Player winner) {
        for (Player player : contestants) {
            if (player != winner) {
                player.teleport(winner);
                player.setGameMode(GameMode.SPECTATOR);
                player.sendTitle(winner.getDisplayName() + " won!", "Resetting players...", 10, 70, 20);
                //restorePlayer(player);
            }
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
        for (Player p : itemDisplays.keySet()) {
            itemDisplays.get(p).setVisible(false);
            itemDisplays.get(p).removePlayer(p);
        }
        itemDisplays.clear();

        scoreboardManager.removeScoreboard();
        scoreboardManager = null;
        RandomizedMinigames.dataInputHandler.removeEntry(owner);
        MinigameHandler.deleteGame(this);
    }

    private void removePlayer(Player p) {
        itemDisplays.get(p).setVisible(false);
        itemDisplays.get(p).removePlayer(p);
        p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        if (contestants.isEmpty())
            stopGame(owner);
    }

    public void killGame() {
        for (Player player : contestants) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
        itemDisplays.clear();

        scoreboardManager.removeScoreboard();
        scoreboardManager = null;
        RandomizedMinigames.dataInputHandler.removeEntry(owner);
        MinigameHandler.deleteGame(this);
    }

    private void restorePlayer(Player p) {
        PlayerBackup backup = backups.get(p);
        p.teleport(backup.getLocation());
        p.setExp(backup.getExp());
        p.getInventory().clear();
        p.getInventory().setContents(backup.getInventory());
        p.getInventory().setArmorContents(backup.getArmor());
    }

    public List<Player> getPlayers() {
        return contestants;
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        Player eventPlayer;
        if (e.getEntity() instanceof Player)
            eventPlayer = (Player) e.getEntity();
        else
            return;
        if (currentItems.containsKey(eventPlayer))
            checkItem(eventPlayer, e.getItem().getItemStack());
    }

    @EventHandler
    public void onItemCraft(CraftItemEvent e) {
        if (e.getCurrentItem() == null)
            return;
        Player p = (Player)e.getWhoClicked();
        if (currentItems.containsKey(p))
            checkItem(p, e.getCurrentItem());
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if (contestants.contains(e.getPlayer())) {
            removePlayer(e.getPlayer());
            restorePlayer(e.getPlayer());
        }
    }
}
