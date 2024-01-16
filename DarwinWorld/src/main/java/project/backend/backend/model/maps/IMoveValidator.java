package project.backend.backend.model.maps;

import project.backend.backend.extras.Vector2d;


/**
 * This interface defines a validator for moves on the map.
 * Classes that implement this interface can validate and correct positions on the map by implementing the validatePosition method.
 */
public interface IMoveValidator {
    /**
     * This method checks if the given position is valid on the map
     * @param newPosition , oldPosition
     * @return
     *  -> newPosition if it is valid
     *  -> other position (corrects the given one using oldPosition)
     * */
    Vector2d validatePosition(Vector2d newPosition , Vector2d oldPosition);
}
