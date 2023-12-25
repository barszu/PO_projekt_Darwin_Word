package project.backend.backend.model.sprites;

import project.backend.backend.extras.Vector2d;

public interface WorldElement_able {
    Vector2d getPosition();

    boolean isAt(Vector2d position);
    String toString();
}
