package project.backend.model.sprites;

import project.backend.model.models.Vector2d;

public interface WorldElementable {
    Vector2d getPosition();

    boolean isAt(Vector2d position);
    String toString();
}
