package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;


import java.util.LinkedList;
import java.util.List;

public class OptionsParser {
    // changing input array as str into MoveDirections
    public static List<MoveDirection> parse(String[] args) {
        //link list because we will always iterate from begging to end of the list,
        //we will not pick el from the middle of the list
        //and maybe in the future that list may be extended
        List<MoveDirection> directions = new LinkedList<>();
        for (String arg : args) {
            switch (arg) {
                case "f" -> directions.add(MoveDirection.FORWARD);
                case "b" -> directions.add(MoveDirection.BACKWARD);
                case "l" -> directions.add(MoveDirection.LEFT);
                case "r" -> directions.add(MoveDirection.RIGHT);
                default -> throw new IllegalArgumentException("'" + arg + "'" + " is not a legal move specification");
            }
        }
        return directions;
    }
}
