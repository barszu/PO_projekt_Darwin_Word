package project.backend.backend.model.enums;

import project.backend.backend.extras.Vector2d;

/**
 * This enum represents the different directions on a map.
 * Each direction is associated with an id and can be converted to a unit vector.
 */
public enum MapDirection {
    NORTH(0),
    NORTH_EAST(1),
    EAST(2),
    SOUTH_EAST(3),
    SOUTH(4),
    SOUTH_WEST(5),
    WEST(6),
    NORTH_WEST(7);

    // The id of the direction, starting from 0
    private final int id; // enum ma metodę ordinal

    /**
     * The constructor for the MapDirection enum.
     * @param id The id of the direction.
     */
    private MapDirection(int id) {
        this.id = id;
    }

    /**
     * This method returns the string representation of the MapDirection.
     * @return A string representing the MapDirection.
     */
    public String toString(){
        return switch(this) {
            case NORTH      -> "N ";
            case NORTH_EAST -> "NE";
            case EAST       -> "E ";
            case SOUTH_EAST -> "SE";
            case SOUTH      -> "S ";
            case SOUTH_WEST -> "SW";
            case WEST       -> "W ";
            case NORTH_WEST -> "NW";
        };
    }

    /**
     * This method returns the MapDirection associated with a given id.
     * @param id The id of the direction.
     * @return The MapDirection associated with the id.
     * @throws IllegalArgumentException if the id does not correspond to a MapDirection.
     */
    public static MapDirection getById(int id) {
        for (MapDirection direction : values()) {
            if (direction.id == id) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Invalid direction id: " + id);
    }

    /**
     * This method returns the next MapDirection in the sequence.
     * @return The next MapDirection.
     */
    public MapDirection next(){
        return getById((this.id + 1)%8);
    }

    /**
     * This method returns the previous MapDirection in the sequence.
     * @return The previous MapDirection.
     */
    public MapDirection previous(){
        int newId = this.id - 1;
        if (newId < 0) { newId += 8;}
        return getById(Math.abs(newId)); // abs?
    }

    /**
     * This method converts the MapDirection to a unit vector.
     * @return A Vector2d representing the unit vector of the MapDirection.
     */
    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH      -> new Vector2d(0, 1);  // to by było warto załatwić parametrem konstruktora
            case NORTH_EAST -> new Vector2d(1, 1);
            case EAST       -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(1, -1);
            case SOUTH      -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1, -1);
            case WEST       -> new Vector2d(-1, 0);
            case NORTH_WEST -> new Vector2d(-1, 1);
        };
    };
}
