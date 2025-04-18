package de.ventority.randomizedminigames.GUI.InGame;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamesScoreboardManager {

    private final Map<String, Integer> punkteMap = new HashMap<>();
    private final Scoreboard scoreboard;
    private final Objective objective;

    public GamesScoreboardManager(List<Player> spielerListe, String gameName) {
        // Neues Scoreboard erstellen
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();

        // Objective erstellen (Sidebar)
        objective = scoreboard.registerNewObjective("punkteboard", Criteria.DUMMY, "§6" + gameName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Alle Spieler hinzufügen und Punktzahl setzen
        for (Player spieler : spielerListe) {
            punkteMap.put(spieler.getName(), 0);
            aktualisiereScore(spieler.getName());
            spieler.setScoreboard(scoreboard);
        }
    }

    /**
     * Setzt die Punktzahl eines Spielers neu.
     * Wenn der Spieler noch nicht vorhanden ist, wird er hinzugefügt.
     */
    public void setPunkte(String spielerName, int punkte) {
        punkteMap.put(spielerName, punkte);
        aktualisiereScore(spielerName);
    }

    /**
     * Holt die aktuelle Punktzahl eines Spielers.
     */
    public int getPunkte(String spielerName) {
        return punkteMap.getOrDefault(spielerName, 0);
    }

    /**
     * Interne Methode zum Aktualisieren des Scores im Scoreboard.
     */
    private void aktualisiereScore(String spielerName) {
        Score score = objective.getScore(spielerName);
        score.setScore(punkteMap.get(spielerName));
    }

    /**
     * Gibt Zugriff auf das Scoreboard.
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
    public void removeScoreboard() {
        org.bukkit.scoreboard.Scoreboard leeresScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        for (String spielerName : punkteMap.keySet()) {
            Player spieler = Bukkit.getPlayerExact(spielerName);
            if (spieler != null && spieler.isOnline()) {
                spieler.setScoreboard(leeresScoreboard);
            }
        }
        objective.unregister();
        punkteMap.clear();
    }
}