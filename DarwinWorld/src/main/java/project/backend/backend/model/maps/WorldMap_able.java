package project.backend.backend.model.maps;

import project.backend.backend.listeners.MapChangeListener;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.model.sprites.Animal;
import project.backend.backend.model.sprites.WorldElement_able;

import java.util.Collection;

/**
 * The WorldMap_able interface should be implemented by any class whose instances are intended to represent a world map.
 * The class that implements this interface needs to define several methods related to the management of animals and grasses on the map,
 * as well as the retrieval of occupants from a certain position.
 */
public interface WorldMap_able extends MoveValidator_able{
    /**
     * This method is used to initialize and place all animals on the map.
     * The number of animals to be initialized is determined by GlobalOptions.initAnimalsNo().
     * The Animal constructor is used for the initial animals.
     */
    void initAllAnimals();

//    ------------------------------------------
    /**
     * This method is used to move all animals on the map, excluding the dead ones.
     */
    void moveAllAnimals();

    //    ------------------------------------------
    /**
     * This method is used move certain Animal on the map, excluding the dead ones.
     * @param animal This is the Animal object that is to be moved.
     * tries to Breed animals when they are on the same position
     */
    void moveAnimal(Animal animal);

    //    ------------------------------------------
    /**
     * This method TRIES to place a certain number of grasses on the map.
     * @param GrassNo This is the number of grasses to be placed on the map.
     */
    void placeGrasses(int GrassNo);

    //    ------------------------------------------
    /**
    * This method TRIES to breed two animals on the map if they are on same position.
    * @param animal1 This is the first Animal object that is to be bred.
    * @param animal2 This is the second Animal object that is to be bred.
    */
    void tryToBreedAnimals(Animal animal1, Animal animal2);

    //    ------------------------------------------
    /**
     * This method is used to get the strongest occupant from a certain position.
     * @param position This is a Vector2d object that represents the position from which the occupant is to be retrieved.
     * @return WorldElement_able This returns the strongest occupant from the given position, or null if there are no occupants.
     */

    WorldElement_able getOccupantFrom(Vector2d position);

    //    ------------------------------------------
    /**
     * This method is used to get all occupants from a certain position.
     * @param position This is a Vector2d object that represents the position from which the occupants are to be retrieved.
     * @return Collection<WorldElement_able> This returns a collection of all occupants from the given position, or an empty collection if there are no occupants.
     */
    Collection<WorldElement_able> getAllOccupantsFrom(Vector2d position);

    //    ------------------------------------------

    @Override
    String toString();

    void addObserver(MapChangeListener observer);
    void removeObserver(MapChangeListener observer);
    void notifyAllObservers(String description);
}
