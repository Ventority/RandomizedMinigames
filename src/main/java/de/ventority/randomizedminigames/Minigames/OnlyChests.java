package de.ventority.randomizedminigames.Minigames;

import de.ventority.randomizedminigames.misc.Timer.Timer;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.List;

public class OnlyChests implements MinigameBase{
    private Timer timer;
    private List<Player> contestants;
    private HashMap<Player, HashMap<Material, Material>> drops = new HashMap<>();
    private Player owner;

    public OnlyChests(List<Player> players, Player owner) {
        this.owner = owner;
        this.contestants = players;
        this.timer = new Timer(contestants, this, "");
        timer.startCounter();
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public String getName() {
        return "Only Chest Loot";
    }

    @Override
    public void addPlayers(List<Player> players) {

    }

    @Override
    public List<Player> getPlayers() {
        return List.of();
    }

    @Override
    public void killGame() {

    }

    @Override
    public void stopGame() {

    }

    @Override
    public Player getOwner() {
        return null;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (contestants.contains(event.getPlayer())) {
            event.setDropItems(false);
        }
    }

    @EventHandler
    public void onMobKilled(EntityDeathEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            timer.pauseCounter();
        }
        if (event.getDamageSource().getCausingEntity() instanceof Player && contestants.contains((Player) event.getDamageSource().getCausingEntity()))
            if (event.getEntity().getType() != EntityType.BLAZE) {
                event.getDrops().clear();
            }
    }
}
