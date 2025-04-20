package de.ventority.randomizedminigames;

import de.ventority.randomizedminigames.GUI.MinigameSetups.MinigamesDisplayWindow;
import de.ventority.randomizedminigames.misc.MinigameHandler;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class SelectMinigame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return true;
        }

        Player player = (Player) commandSender;
        if (args.length > 0 && args[0].equalsIgnoreCase("killall")) {
            player.sendMessage("Alle Minigame-Instanzen werden beendet!");
            MinigameHandler.killAll();
            return true;
        }

        if (MinigameHandler.getOccupiedPlayers().contains(player)) {
            return true;
        }

        new MinigamesDisplayWindow(player, "MinigameSelect").buildWindow();
        return true;
    }

}
