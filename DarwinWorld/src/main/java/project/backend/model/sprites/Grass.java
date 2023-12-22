package project.backend.model.sprites;

import project.backend.model.models.Vector2d;

public class Grass implements WorldElementable {

    private final Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    @Override
    public String toString() {
        return "*";
    }
}
