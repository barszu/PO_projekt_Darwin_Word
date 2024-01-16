package project.backend.backend.model.sprites;

import project.backend.backend.extras.Vector2d;

/**
 * This interface represents a world element in the @IWorldMap (simulation).
 * World elements are objects that exist in the worldMap and have a position.
 * Classes that implement this interface should provide methods to get the position of the element,
 * check if the element is at a specific position, and convert the element to a string.
 */
public interface IWorldElement {

    /**
     * This method returns the position of the world element.
     * Implementing classes should return a Vector2d object representing the position of the element.
     * @return A Vector2d object representing the position of the world element.
     */
    Vector2d getPosition();

    /**
     * This method checks if the world element is at a specific position.
     * Implementing classes should return a boolean indicating whether the element is at the specified position.
     * @param position The position to check.
     * @return A boolean indicating whether the world element is at the specified position.
     */
    boolean isAt(Vector2d position);

    /**
     * This method returns a string representation of the world element.
     * Implementing classes should return a string that represents the world element.
     * @return A string representing the world element.
     */
    String toString();
}
