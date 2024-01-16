package project.backend.backend.model.maps.mapsUtil;

import project.backend.backend.extras.Vector2d;

/**
 * This class represents a rectangle boundary defined by two points: lower left and upper right.
 * It provides methods to get the height, width, and area of the rectangle,
 * and to check if a position is within the rectangle.
 */
public record RectangleBoundary(Vector2d lowerLeft , Vector2d upperRight) {

    /**
     * This method returns the height of the rectangle.
     * @return An integer representing the height of the rectangle (exclusive).
     */
    public int height() {
        return upperRight.getY() - lowerLeft().getY() + 1;
    }

    /**
     * This method returns the width of the rectangle.
     * @return An integer representing the width of the rectangle (exclusive).
     */
    public int width() {
        return upperRight.getX() - lowerLeft().getX() + 1;
    }

    /**
     * This method checks if a position is within the rectangle.
     * @param position The position to check.
     * @return A boolean indicating whether the position is within the rectangle (inclusive).
     */
    public boolean contains(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }

    /**
     * This method returns the area of the rectangle.
     * @return An integer representing the area of the rectangle (exclusive).
     */
    public int area() {
        return height() * width();
    }

    /**
     * This method returns a string representation of the rectangle.
     * @return A string representing the rectangle.
     */
    @Override
    public String toString() {
        return lowerLeft+" x "+upperRight;
    }
}
