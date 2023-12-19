package project.backend.model.enums;

import project.backend.model.models.Vector2d;

public enum MapDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public String toString(){
        return switch(this) {
            case NORTH -> "N";
            case SOUTH -> "S";
            case EAST -> "E";
            case WEST -> "W";
        };
    }

    public MapDirection next(){
        return switch (this){
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public MapDirection previous(){
        return switch (this){
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
    }

    public Vector2d toUnitVector() {
        int x=0, y=0;
        switch (this) {
            case NORTH -> { x = 0; y = 1; }
            case EAST -> { x = 1; y = 0; }
            case WEST -> { x = -1; y = 0; }
            case SOUTH -> { x = 0; y = -1; }
        }
        return new Vector2d(x, y);
    };
}
