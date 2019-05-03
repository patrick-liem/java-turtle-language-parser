/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

/**
 * Represents a drawable turtle action.
 */
public class Action {

    ActionType type;
    String displayString;
    LineSegment lineSeg;

    public Action(ActionType type, String displayString, LineSegment lineSeg) {
        this.type = type;
        this.displayString = displayString;
        this.lineSeg = lineSeg;
    }

    public String toString() {
        if (displayString == null) {
            return "";
        } else {
            return displayString;
        }
    }
}

/**
 * Enumeration of turtle action types.
 */
enum ActionType {
    FORWARD, TURN, COLOR
}
