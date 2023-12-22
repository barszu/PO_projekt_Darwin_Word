package project.backend.model.maps;

import project.backend.model.sprites.Animal;
import agh.ics.oop.model.MoveDirection;
import project.backend.model.models.Vector2d;
import project.backend.model.exceptions.PositionAlreadyOccupiedException;
import project.backend.model.observers.MapChangeListener;
import project.backend.model.sprites.WorldElementable;

import java.util.Collection;
import java.util.UUID;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMapable extends MoveValidatorable {
    /**
     * Place a animal on the map.
     *
     * @param_animal The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the move is not valid.
     */
    void place(Animal animal) throws PositionAlreadyOccupiedException;
//    boolean place(WorldElement element);

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal animal, MoveDirection direction);
//    void move(WorldElement element, MoveDirection direction);
    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);
//    boolean isOccupied(P position);
    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */
    WorldElementable objectAt(Vector2d position);
//    WorldElement objectAt(P position);
    @Override
    String toString();

    Collection<WorldElementable> getElements();

    RectangleBoundary getCurrentBounds();

    //observers
    void addObserver(MapChangeListener observer);
    void removeObserver(MapChangeListener observer);

    UUID getId();

}
