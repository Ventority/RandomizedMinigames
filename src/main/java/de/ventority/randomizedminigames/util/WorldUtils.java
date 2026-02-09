package de.ventority.randomizedminigames.util;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Random;

public class WorldUtils {

    public static void resetDefaultWorlds() {
        String[] worlds = {"world", "world_nether", "world_the_end"};

        // Spieler vorher in Sicherheit bringen
        World safeWorld = Bukkit.getWorld("world"); // falls schon geladen
        if (safeWorld == null && !Bukkit.getWorlds().isEmpty()) {
            safeWorld = Bukkit.getWorlds().get(0);
        }

        if (safeWorld != null) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.teleport(safeWorld.getSpawnLocation());
            }
        }

        for (String worldName : worlds) {
            World w = Bukkit.getWorld(worldName);
            if (w != null) {
                Bukkit.unloadWorld(w, false);
            }
            deleteWorldFolder(new File(Bukkit.getWorldContainer(), worldName));
        }

        long newSeed = new Random().nextLong();

        WorldCreator overworld = new WorldCreator("world");
        overworld.environment(Environment.NORMAL);
        overworld.seed(newSeed);
        Bukkit.createWorld(overworld);

        WorldCreator nether = new WorldCreator("world_nether");
        nether.environment(Environment.NETHER);
        nether.seed(newSeed);
        Bukkit.createWorld(nether);

        WorldCreator theEnd = new WorldCreator("world_the_end");
        theEnd.environment(Environment.THE_END);
        theEnd.seed(newSeed);
        Bukkit.createWorld(theEnd);

        Bukkit.getLogger().info("Alle Standardwelten wurden neu generiert mit Seed: " + newSeed);
    }

    private static void deleteWorldFolder(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteWorldFolder(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
        path.delete();
    }
}


