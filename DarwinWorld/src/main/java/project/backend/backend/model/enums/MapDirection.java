package project.backend.backend.model.enums;

import project.backend.backend.extras.Vector2d;

public enum MapDirection {
    NORTH(0),
    NORTH_EAST(1),
    EAST(2),
    SOUTH_EAST(3),
    SOUTH(4),
    SOUTH_WEST(5),
    WEST(6),
    NORTH_WEST(7);
    private final int id; //starting from 0

    private MapDirection(int id) {
        this.id = id;
    }

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

    public static MapDirection getById(int id) {
        for (MapDirection direction : values()) {
            if (direction.id == id) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Invalid direction id: " + id);
    }

    public MapDirection next(){
        return getById((this.id + 1)%8);
    }

    public MapDirection previous(){
        int newId = this.id - 1;
        if (newId < 0) { newId += 8;}
        return getById(Math.abs(newId));
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH      -> new Vector2d(0, 1);
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
