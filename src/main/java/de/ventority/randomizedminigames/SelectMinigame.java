package de.ventority.randomizedminigames;

import de.ventority.randomizedminigames.GUI.MinigameSetups.MinigamesDisplayWindow;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class SelectMinigame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        MinigamesDisplayWindow window = new MinigamesDisplayWindow((Player) commandSender, "MinigameSelect");
        window.buildWindow();
        return true;
    }

}
