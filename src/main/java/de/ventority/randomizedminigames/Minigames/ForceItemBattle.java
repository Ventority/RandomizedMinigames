package de.ventority.randomizedminigames.Minigames;

import de.ventority.randomizedminigames.GUI.InGame.GamesScoreboardManager;
import de.ventority.randomizedminigames.RandomizedMinigames;
import de.ventority.randomizedminigames.misc.MinigameHandler;
import de.ventority.randomizedminigames.misc.PlayerBackup;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ForceItemBattle implements MinigameBase, Listener {
    protected final HashMap<Player, ItemStack> currentItems;
    protected final HashMap<Player, Integer> currentScores;
    protected final HashMap<Player, BossBar> itemDisplays;
    private final int id;
    private GamesScoreboardManager scoreboardManager;
    protected final List<Player> contestants;
    private final HashMap<Player, PlayerBackup> backups;
    private final Player owner;
    protected final int limit;
    private final List<Material> SURVIVAL_ITEMS = Arrays.stream(Material.values())
            .filter(Material::isItem)
            .filter(m -> {
                String name = m.name();
                return !name.contains("SPAWN_EGG") &&
                        !name.contains("COMMAND") &&
                        !name.contains("STRUCTURE") &&
                        !name.contains("JIGSAW") &&
                        !name.contains("DEBUG") &&
                        !name.contains("BARRIER") &&
                        !name.contains("KNOWLEDGE_BOOK") &&
                        !name.contains("LIGHT") &&
                        !name.contains("INFESTED") &&
                        !name.contains("BEDROCK") &&
                        !name.contains("VOID") &&
                        !name.contains("REINFORCED_DEEPSLATE") &&
                        !name.contains("END_PORTAL") &&
                        !name.contains("END_GATEWAY") &&
                        !name.contains("NETHER_PORTAL") &&
                        !name.contains("POTTED_") &&
                        !name.contains("MUSIC");
            })
            .toList();


    public ForceItemBattle(List<Player> players, Player owner) {
        limit = RandomizedMinigames.dataInputHandler.getSelectedLimit(owner);
        id = new Random().nextInt(1024);
        currentItems = new HashMap<>();
        currentScores = new HashMap<>();
        itemDisplays = new HashMap<>();
        backups = new HashMap<>();
        contestants = players;
        this.owner = owner;
        if (RandomizedMinigames.dataInputHandler.getScoreboardStatus())
            scoreboardManager = new GamesScoreboardManager(players, getName());
        for (Player player : players) {
            currentItems.put(player, null);
            currentScores.put(player, 0);
            BossBar bar = Bukkit.createBossBar("Hallo", BarColor.PURPLE, BarStyle.SOLID);
            bar.addPlayer(player);
            bar.setVisible(true);
            itemDisplays.put(player, bar);
            updatePlayerItem(player, getRandomItem());
            backups.put(player, new PlayerBackup(player.getInventory().getContents(), player.getInventory().getArmorContents(), player.getExp(), player.getLocation()));
            ItemStack skip = new ItemStack(Material.BARRIER, 3);
            ItemMeta meta = skip.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Skip");
            skip.setItemMeta(meta);
            player.getInventory().addItem(skip);
        }
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
        return new ItemStack(SURVIVAL_ITEMS.get(new Random().nextInt(0, SURVIVAL_ITEMS.size())));
    }

    protected void checkItem(Player p, ItemStack i) {
        if (i == null) return;
        if (currentItems.get(p).getType() == i.getType()) {
            currentScores.replace(p, currentScores.get(p) + 1);
            if (RandomizedMinigames.dataInputHandler.getScoreboardStatus())
                scoreboardManager.setPunkte(p.getName(), scoreboardManager.getPunkte(p.getName()) + 1);
            if (checkWin(p)) {
                stopGame(p);
                return;
            }
            updatePlayerItem(p, getRandomItem());
        }
    }

    protected void updatePlayerItem(Player player, ItemStack item) {
        currentItems.replace(player, item);
        String key = currentItems.get(player).getType().getTranslationKey();
        String displayName = key.replace("block.minecraft.", "")
                .replace("_", " ")
                .replace("item.minecraft.", "");
        itemDisplays.get(player).setTitle(displayName);
        ItemStack[] contents = player.getInventory().getContents();
        for (ItemStack i : contents)
            checkItem(player, i);
    }

    protected boolean checkWin(Player p) {
        return currentScores.get(p) == limit;
    }

    protected void showEndMessage(Player winner) {
        for (Player player : contestants) {
            if (player != winner) {
                player.teleport(winner);
                player.setGameMode(GameMode.SPECTATOR);
                player.sendTitle(winner.getDisplayName() + " won!", "Resetting players...", 10, 70, 20);
                //restorePlayer(player);
            }
            if (RandomizedMinigames.dataInputHandler.getScoreboardStatus())
                player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }

    private void stopGame(Player winner) {
        showEndMessage(winner);
        for (Player p : itemDisplays.keySet()) {
            itemDisplays.get(p).setVisible(false);
            itemDisplays.get(p).removePlayer(p);
        }
        itemDisplays.clear();
        if (RandomizedMinigames.dataInputHandler.getScoreboardStatus()) {
            scoreboardManager.removeScoreboard();
            scoreboardManager = null;
        }
        RandomizedMinigames.dataInputHandler.removeSelectionEntry(owner);
        MinigameHandler.deleteGame(this);
    }

    private void removePlayer(Player p) {
        itemDisplays.get(p).setVisible(false);
        itemDisplays.get(p).removePlayer(p);
        if (RandomizedMinigames.dataInputHandler.getScoreboardStatus())
            p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        if (contestants.isEmpty())
            stopGame(owner);
    }

    public void killGame() {
        for (Player player : contestants) {
            if (RandomizedMinigames.dataInputHandler.getScoreboardStatus())
                player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
        itemDisplays.clear();

        if (RandomizedMinigames.dataInputHandler.getScoreboardStatus()) {
            scoreboardManager.removeScoreboard();
            scoreboardManager = null;
        }
        RandomizedMinigames.dataInputHandler.removeSelectionEntry(owner);
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

    public void skipItem(Player p) {
        ItemStack curItem = currentItems.get(p);
        p.getInventory().addItem(curItem);
        checkItem(p, curItem);
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
        Player p = (Player) e.getWhoClicked();
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
