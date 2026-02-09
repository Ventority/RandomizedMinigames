package de.ventority.randomizedminigames.misc.Timer;

import de.ventority.randomizedminigames.Minigames.MinigameBase;
import de.ventority.randomizedminigames.RandomizedMinigames;
import de.ventority.randomizedminigames.util.MinigameHandler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;


public class Timer {
    private int counter = 0;
    private BukkitTask counterTask;
    private int stopTime;
    private List<Player> players;
    private MinigameBase minigame;
    private boolean isPaused = false;
    private Runnable methodToCall;
    private boolean isReversed = false;
    private String customText;
    private int countedSeconds = 0;
    private String defaultText;

    public Timer(List<Player> players, MinigameBase minigame, String text) {
        this.players = players;
        this.minigame = minigame;
        this.customText = text;
        this.stopTime = MinigameHandler.getSettings(minigame.getOwner()).getTimeLimit();
    }

    public Timer(List<Player> players, MinigameBase minigame, Runnable method, String text) {
        this.players = players;
        this.minigame = minigame;
        this.methodToCall = method;
        this.customText = text;
        this.stopTime = MinigameHandler.getSettings(minigame.getOwner()).getTimeLimit();
    }

    public void startCounter() {
        if (counterTask != null && !counterTask.isCancelled()) {
            return;
        }
        counter = 0;
        countedSeconds = 0;

        counterTask = Bukkit.getScheduler().runTaskTimer(RandomizedMinigames.serverSettingsHandler.getPlugin(), () -> {
            countedSeconds = counter / 20;
            updateText();
            updatePlayers();
            if (!isPaused) {
                counter++;
                if (countedSeconds == stopTime && MinigameHandler.getSettings(minigame.getOwner()).isTimed) {
                    if (methodToCall != null)
                        methodToCall.run();
                    else
                        minigame.stopGame();
                    stopCounter();
                }
            }
        }, 0L, 1L);
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

    private String updateText() {
        int x = isReversed ? stopTime - countedSeconds : countedSeconds;

        int days = x / 86400;
        int hours = (x % 86400) / 3600;
        int minutes = (x % 3600) / 60;
        int seconds = x % 60;
        StringBuilder sb = new StringBuilder();
        if (days > 0)
            sb.append(days).append("d ");
        if (hours > 0 || days > 0)
            sb.append(hours).append(":");
        if (minutes < 10 && (hours > 0 || days > 0))
            sb.append("0");
        sb.append(minutes).append(":");
        if (seconds < 10)
            sb.append("0");
        sb.append(seconds);
        String output = sb.toString();
        return isPaused ? "The Timer is paused...  " : customText + output;
    }

    public void resetCounter() {
        counter = 0;
    }

    private void updatePlayers() {
//        int amplitude = 40;
//
//        int step = counter % (amplitude * 2);
//        int brightness = step <= amplitude ? step : (amplitude * 2 - step);
//
//        int baseRed = 125;
//        int baseGreen = 0;
//        int baseBlue = 130;
//
//
//        Color c = new Color(
//                baseRed + 2*brightness,
//                baseGreen,
//                baseBlue + 2*brightness
//        );
//
        TextComponent msg = new TextComponent(defaultText);
//        msg.setColor(ChatColor.of(c));
        msg.setBold(true);

        for (Player player : players) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, msg);
        }

    }

    public void setStopTime(int seconds) {
        this.stopTime = seconds;
    }

    public void setReversed(boolean reversed) {
        this.isReversed = reversed;
    }
}