package project.backend.model.maps;

import project.backend.model.models.Vector2d;

public interface MoveValidatorable {

    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2d position);
}
