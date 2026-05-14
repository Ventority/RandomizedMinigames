package de.ventority.randomizedminigames.Minigames;

import de.ventority.randomizedminigames.RandomizedMinigames;
import de.ventority.randomizedminigames.gui.InGame.GamesScoreboardManager;
import de.ventority.randomizedminigames.misc.ColorCycler;
import de.ventority.randomizedminigames.misc.Timer.Timer;
import de.ventority.randomizedminigames.util.MinigameHandler;
import de.ventority.randomizedminigames.util.PlayerBackupHandler;
import de.ventority.randomizedminigames.util.Settings;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.UUID;

public class ForceItemBattle implements MinigameBase, Listener {
    protected final HashMap<Player, ItemStack> currentItems;
    protected final HashMap<Player, Integer> currentScores;
    protected final HashMap<Player, BossBar> itemDisplays;
    private final int id;
    private GamesScoreboardManager scoreboardManager;
    protected final List<Player> contestants;
    private final PlayerBackupHandler backups;
    private final Player owner;
    protected Settings settings;
    private final Timer timer;
    private final HashMap<UUID, DisconnectedState> disconnectedStates = new HashMap<>();
    private final HashMap<Player, Integer> skips = new HashMap<>();
    private boolean isActive = true;

    private static class DisconnectedState {
        final ItemStack currentItem;
        final int score;
        final int skipsLeft;
        DisconnectedState(ItemStack currentItem, int score, int skipsLeft) {
            this.currentItem = currentItem;
            this.score = score;
            this.skipsLeft = skipsLeft;
        }
    }
    private final ColorCycler bossBarColors = new ColorCycler(
            new Color(130, 50, 170), new Color(160, 50, 190), 0.2);
    private int bossBarTick = 0;
    private org.bukkit.scheduler.BukkitTask bossBarTask;

    private final List<Material> SURVIVAL_ITEMS = loadMaterials();


    public ForceItemBattle(List<Player> players, Player owner) {
        settings = MinigameHandler.getSettings(owner);
        timer = new Timer(players, this, "");
        timer.setStopTime(settings.getTimeLimit());
        id = new Random().nextInt(1024);
        currentItems = new HashMap<>();
        currentScores = new HashMap<>();
        itemDisplays = new HashMap<>();
        backups = new PlayerBackupHandler(players);
        contestants = players;
        this.owner = owner;
        if (MinigameHandler.getSettings(owner).getScoreboardStatus())
            scoreboardManager = new GamesScoreboardManager(players, getName());
        for (Player player : players) {
            currentItems.put(player, null);
            currentScores.put(player, 0);
            skips.put(player, 3);

            BossBar bar = Bukkit.createBossBar("Null", BarColor.PURPLE, BarStyle.SOLID);
            bar.addPlayer(player);
            bar.setVisible(true);
            itemDisplays.put(player, bar);

            updatePlayerItem(player, getRandomItem());
            player.getInventory().clear();

            ItemStack skip = new ItemStack(Material.BARRIER, 3);
            ItemMeta meta = skip.getItemMeta();
            assert meta != null;
            meta.setDisplayName(ChatColor.RED + "Skip");
            skip.setItemMeta(meta);
            player.getInventory().addItem(skip);
        }
        timer.startCounter();
        bossBarTask = Bukkit.getScheduler().runTaskTimer(
                RandomizedMinigames.serverSettingsHandler.getPlugin(),
                () -> {
                    String color = ChatColor.of(bossBarColors.getColor(bossBarTick++)).toString();
                    for (Map.Entry<Player, BossBar> entry : new HashMap<>(itemDisplays).entrySet()) {
                        ItemStack item = currentItems.get(entry.getKey());
                        if (item == null) continue;
                        String name = item.getType().getTranslationKey()
                                .replace("block.minecraft.", "")
                                .replace("_", " ")
                                .replace("item.minecraft.", "");
                        entry.getValue().setTitle(color + ChatColor.BOLD + name);
                    }
                }, 0L, 1L);
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
    public void addPlayers(List<Player> players) {

    }

    protected ItemStack getRandomItem() {
        return new ItemStack(SURVIVAL_ITEMS.get(new Random().nextInt(0, SURVIVAL_ITEMS.size())));
    }

    protected void checkItem(Player p, ItemStack i) {
        if (i == null) return;
        if (currentItems.get(p).getType() == i.getType()) {
            currentScores.replace(p, currentScores.get(p) + 1);
            p.sendMessage("Du hast " + i.getTranslationKey().replace("block.minecraft.", "")
                    .replace("_", " ")
                    .replace("item.minecraft.", "") + " gefunden!");
            if (settings.getScoreboardStatus())
                scoreboardManager.setPunkte(p.getName(), scoreboardManager.getPunkte(p.getName()) + 1);
            if (!settings.isTimed && checkWin(p)) {
                stopGame(p);
                return;
            }
            updatePlayerItem(p, getRandomItem());
        }
    }

    protected void updatePlayerItem(Player player, ItemStack item) {
        currentItems.replace(player, item);
        ItemStack[] contents = player.getInventory().getContents();
        for (ItemStack i : contents)
            checkItem(player, i);
    }

    protected boolean checkWin(Player p) {
        return currentScores.get(p) == settings.getSelectedLimit();
    }

    protected void showEndMessage(Player winner) {
        for (Player player : contestants) {
            if (player != winner) {
                player.teleport(winner);
                player.setGameMode(GameMode.SPECTATOR);
                player.sendTitle(winner.getDisplayName() + " won!", "Resetting game...", 10, 70, 20);
            } else {
                player.sendTitle("You won. Congratulations!", "Your score was: " + currentScores.get(winner), 10, 70, 20);
            }
            if (settings.getScoreboardStatus())
                player.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard());
        }
    }

    public void stopGame() {
        Map.Entry<Player, Integer> bestEntry = currentScores.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
        assert bestEntry != null;
        stopGame(bestEntry.getKey());
    }

    private void stopGame(Player winner) {
        showEndMessage(winner);
        timer.pauseCounter();
        Timer t = new Timer(contestants, this, this::killGame, "Resetting game in: ");
        t.setStopTime(10);
        t.setReversed(true);
        t.startCounter();
    }

    private void removePlayer(Player p) {
        contestants.remove(p);
        if (itemDisplays.get(p) != null) {
            itemDisplays.get(p).setVisible(false);
            itemDisplays.get(p).removePlayer(p);
            itemDisplays.remove(p);
        }

        if (settings.getScoreboardStatus())
            p.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard());
        if (contestants.isEmpty())
            stopGame(owner);
    }

    public void killGame() {
        isActive = false;
        timer.stopCounter();
        if (bossBarTask != null) bossBarTask.cancel();

        for (BossBar bar : itemDisplays.values()) {
            bar.setVisible(false);
            bar.removeAll();
        }
        itemDisplays.clear();

        if (scoreboardManager != null) {
            scoreboardManager.removeScoreboard();
            scoreboardManager = null;
        }

        Scoreboard main = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        for (Player player : contestants) {
            if (player.isOnline())
                player.setScoreboard(main);
        }

        backups.restoreAll();
        MinigameHandler.resetSettings(owner);
        MinigameHandler.deleteGame(this);
    }

    public List<Player> getPlayers() {
        return contestants;
    }

    public void skipItem(Player p) {
        ItemStack curItem = currentItems.get(p);
        p.getInventory().addItem(curItem);
        checkItem(p, curItem);
        skips.put(p, skips.get(p) - 1);
    }

    public Player getOwner() {
        return owner;
    }

    private List<Material> loadMaterials() {
        List<Material> mats = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("obtainableItems.txt"))))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                Material mat = Material.getMaterial(line);
                if (mat != null) {
                    mats.add(mat);
                } else {
                    System.out.println("Unbekanntes Material: " + line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return mats;
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
        Player p = e.getPlayer();
        if (!contestants.contains(p)) return;

        disconnectedStates.put(p.getUniqueId(), new DisconnectedState(
                currentItems.get(p),
                currentScores.getOrDefault(p, 0),
                skips.getOrDefault(p, 0)
        ));
        currentItems.remove(p);
        currentScores.remove(p);
        skips.remove(p);
        removePlayer(p);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        DisconnectedState state = disconnectedStates.remove(uuid);
        if (state == null) return;

        if (isActive) {
            // Re-add player to the running game
            contestants.add(p);
            currentItems.put(p, state.currentItem);
            currentScores.put(p, state.score);
            skips.put(p, state.skipsLeft);

            BossBar bar = Bukkit.createBossBar("", BarColor.PURPLE, BarStyle.SOLID);
            bar.addPlayer(p);
            bar.setVisible(true);
            itemDisplays.put(p, bar);

            if (settings.getScoreboardStatus() && scoreboardManager != null)
                p.setScoreboard(scoreboardManager.getScoreboard());

            p.getInventory().clear();
            ItemStack skipItem = new ItemStack(Material.BARRIER, state.skipsLeft);
            org.bukkit.inventory.meta.ItemMeta meta = skipItem.getItemMeta();
            if (meta != null) { meta.setDisplayName(org.bukkit.ChatColor.RED + "Skip"); skipItem.setItemMeta(meta); }
            if (state.skipsLeft > 0) p.getInventory().addItem(skipItem);
        } else {
            // Game already ended — restore their pre-game backup
            PlayerBackupHandler.restoreIfPending(p);
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR &&
                event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.BARRIER) return;
        Player p = event.getPlayer();
        try {
            if (MinigameHandler.getMinigames().isEmpty()) return;
            for (MinigameBase minigame : MinigameHandler.getMinigames()) {
                if ((minigame instanceof ForceItemBattle) && contestants.contains(p) && skips.get(p) > 0) {
                    ((ForceItemBattle) minigame).skipItem(p);
                }
            }
        } catch (Exception ignored) {
        }
        p.sendMessage(ChatColor.GREEN + "Skipped Item.");
        if (item.getAmount() > 1)
            item.setAmount(item.getAmount() - 1);
        else
            p.getInventory().remove(item);
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        if (contestants.contains(e.getPlayer())) {
            ItemStack skip = new ItemStack(Material.BARRIER, skips.get(e.getPlayer()));
            e.getPlayer().getInventory().addItem(skip);
        }
    }
}
