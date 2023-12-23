package project.backend.model.maps;

import project.backend.model.models.Vector2d;

//TODO: mapy beda to spelniac
public interface MoveValidatorable {

    Vector2d canMoveTo(Vector2d position);
}
