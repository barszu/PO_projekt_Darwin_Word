package project.backend.backend.model.maps;

import project.backend.backend.extras.Vector2d;

//TODO: mapy beda to spelniac
public interface MoveValidator_able {
    /**
     * This method checks if the given position is valid on the map
     * @param newPosition , oldPosition
     * @return
     *  -> newPosition if it is valid
     *  -> other position (corrects the given one using oldPosition)
     * */
    Vector2d validatePosition(Vector2d newPosition , Vector2d oldPosition);
}
