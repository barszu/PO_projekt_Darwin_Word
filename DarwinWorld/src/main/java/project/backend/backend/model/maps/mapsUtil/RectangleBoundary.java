package project.backend.backend.model.maps.mapsUtil;

import project.backend.backend.extras.Vector2d;

public record RectangleBoundary(Vector2d lowerLeft , Vector2d upperRight) {
    // for toString map visualization
    //all getters and setters inside
    public int height() {
        return upperRight.getY() - lowerLeft().getY() + 1;
    }

    public int width() {
        return upperRight.getX() - lowerLeft().getX() + 1;
    }

    public boolean contains(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }

    @Override
    public String toString() {
        return lowerLeft+" x "+upperRight;
    }
}
