package de.ventority.randomizedminigames.misc;

import de.ventority.randomizedminigames.Minigames.MinigameBase;
import de.ventority.randomizedminigames.RandomizedMinigames;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;


public class Timer {
    private int counter = 0;
    private BukkitTask counterTask;
    private int maxValue = 10;
    private List<Player> players;
    private MinigameBase minigame;
    private boolean isPaused = false;
    private Runnable methodToCall;
    private boolean isReversed = false;
    private String outputText;

    public Timer(int maxValue, List<Player> players, MinigameBase minigame, String text) {
        this.maxValue = maxValue;
        this.players = players;
        this.minigame = minigame;
        this.outputText = text;
    }

    public Timer(int maxValue, List<Player> players, MinigameBase minigame, Runnable method, String text) {
        this.maxValue = maxValue;
        this.players = players;
        this.minigame = minigame;
        this.methodToCall = method;
        this.outputText = text;
    }
    public Timer(int maxValue, List<Player> players, MinigameBase minigame, Runnable method, boolean isReversed, String text) {
        this.maxValue = maxValue;
        this.players = players;
        this.minigame = minigame;
        this.methodToCall = method;
        this.isReversed = isReversed;
        this.outputText = text;
    }

    public void startCounter() {
        if (counterTask != null && !counterTask.isCancelled()) {
            return;
        }

        counter = 0;

        counterTask = Bukkit.getScheduler().runTaskTimer(RandomizedMinigames.serverSettingsHandler.getPlugin(), () -> {
            updatePlayers();
            if (!isPaused) {
                counter++;
                if (counter >= maxValue && MinigameHandler.getSettings(minigame.getOwner()).isTimed) {
                    if (methodToCall != null)
                        methodToCall.run();
                    else
                        minigame.stopGame();
                    stopCounter();
                }
            }
        }, 20L, 20L);
    }

    public void pauseCounter() {
        isPaused = true;
    }

    public void resumeCounter() {
        isPaused = false;
    }

    public void stopCounter() {
        if (counterTask != null)
            counterTask.cancel();
    }

    public void resetCounter() {
        counter = 0;
    }

    private void updatePlayers() {
        if (isPaused) {
            for (Player player : players)
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "The Timer is paused..."));
            return;
        }

        int x = isReversed ? maxValue - counter : counter;
        int minutes = (int)Math.floor((double) x / 60);
        int seconds = x % 60;
        StringBuilder  sb = new StringBuilder();
        if (minutes != 0) {
            sb.append(minutes).append(":");
        }
        sb.append(seconds < 10 ? "0" + seconds : seconds);
        String output = sb.toString();
        for (Player player : players) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + outputText + output));
        }
    }
}