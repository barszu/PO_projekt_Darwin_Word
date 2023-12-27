package agh.ics.oop.exceptions;

import project.backend.model.models.Vector2d;

public class PositionAlreadyOccupiedException extends Exception{

    public PositionAlreadyOccupiedException(Vector2d position) {
        super("Position " + position + " is already occupied.");
    }
}
