package de.ventority.randomizedminigames.misc;

import java.awt.Color;

public class ColorCycler {
    private final Color from;
    private final Color to;
    private final double cyclesPerSecond;

    /**
     * @param from           start color of the pulse
     * @param to             end color of the pulse
     * @param cyclesPerSecond how many full dark→light→dark cycles per second
     */
    public ColorCycler(Color from, Color to, double cyclesPerSecond) {
        this.from = from;
        this.to = to;
        this.cyclesPerSecond = cyclesPerSecond;
    }

    /**
     * Returns the interpolated color for a given tick (20 ticks = 1 second).
     * Uses a sine wave so the transition is smooth in both directions.
     */
    public Color getColor(int tick) {
        double t = (Math.sin(tick * (2 * Math.PI * cyclesPerSecond / 20.0)) + 1.0) / 2.0;
        return new Color(
                lerp(from.getRed(),   to.getRed(),   t),
                lerp(from.getGreen(), to.getGreen(), t),
                lerp(from.getBlue(),  to.getBlue(),  t)
        );
    }

    private static int lerp(int a, int b, double t) {
        return (int) Math.round(a + (b - a) * t);
    }
}
