/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.awt.Color;

/**
 * Enumeration of turtle pen colors.
 * 
 * You may not modify this enumeration.
 */
public enum PenColor {

    BLACK(Color.BLACK),
    GRAY(Color.GRAY),
    RED(Color.RED),
    PINK(Color.PINK),
    ORANGE(Color.ORANGE),
    YELLOW(new Color(228, 228, 0)),
    GREEN(Color.GREEN),
    CYAN(Color.CYAN),
    BLUE(Color.BLUE),
    MAGENTA(Color.MAGENTA);

    /**
     * Java standard library color for this pen color.
     */
    public final Color color;

    PenColor(Color color) {
        this.color = color;
    }
}
