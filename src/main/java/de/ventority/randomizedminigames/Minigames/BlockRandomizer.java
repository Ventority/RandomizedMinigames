package de.ventority.randomizedminigames.Minigames;

import de.ventority.randomizedminigames.misc.Timer.Timer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BlockRandomizer implements MinigameBase {
    private Timer timer;
    private List<Player> contestants;
    private HashMap<Player, HashMap<Material, Material>> drops = new HashMap<>();
    private Player owner;

    public BlockRandomizer(List<Player> players, Player owner) {
        this.owner = owner;
        this.contestants = players;
        this.timer = new Timer(contestants, this, "");
        for (Player p : contestants) drops.put(p, new HashMap<>());
        timer.startCounter();
    }

    private Material getAndAddRandomMaterial(Player p, Block block) {
        List<Material> validMaterials = Arrays.stream(Material.values())
                .filter(Material::isItem)
                .toList();
        Material m = validMaterials.get(new Random().nextInt(validMaterials.size()));
        if (!hasEyeOrEPandBlaze(p))
            m = new Random().nextDouble() < 0.05 ? Material.ENDER_EYE : m;
        drops.get(p).put(block.getType(), m);
        return m;
    }

    private ItemStack getItems(Player p, Block block, int amount) {
        Material material = drops.get(p).containsKey(block.getType()) ? drops.get(p).get(block.getType()) : getAndAddRandomMaterial(p, block);
        return new ItemStack(material, amount);
    }

    private boolean hasEyeOrEPandBlaze(Player p) {
        HashMap<Material, Material> map = drops.get(p);
        return map.containsValue(Material.ENDER_EYE) || (map.containsValue(Material.ENDER_PEARL) && (map.containsValue(Material.BLAZE_POWDER) || map.containsValue(Material.BLAZE_ROD)));
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void addPlayers(List<Player> players) {

    }

    @Override
    public List<Player> getPlayers() {
        return contestants;
    }

    @Override
    public void killGame() {

    }

    @Override
    public void stopGame() {

    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (contestants.contains(e.getPlayer())) {
            e.setDropItems(false);
            Collection<ItemStack> vanillaDrops = e.getBlock().getDrops(e.getPlayer().getItemInUse());
            int totalAmount = vanillaDrops.stream()
                    .mapToInt(ItemStack::getAmount)
                    .sum();

            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), getItems(e.getPlayer(), e.getBlock(), Math.max(totalAmount, 1)));
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof EnderDragon) {
            timer.pauseCounter();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        timer.pauseCounter();
    }
}
