package project.backend.backend.model.maps;

import project.backend.backend.listeners.MapChangeListener;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.model.sprites.Animal;
import project.backend.backend.model.sprites.WorldElement_able;

import java.util.List;

/**
 * The WorldMap_able interface should be implemented by any class whose instances are intended to represent a world map.
 * The class that implements this interface needs to define several methods related to the management of animals and grasses on the map,
 * as well as the retrieval of occupants from a certain position.
 */
public interface WorldMap_able extends MoveValidator_able{

    /**
     * This method is used to update all elements on the map.
     * Move Animals, puts Grass etc.
     * It should be called once per tick (Game Day).
    */
    void updateEverything();

    //    ------------------------------------------
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
     * This method TRIES to place a certain number of grasses on the map.
     * @param grassNo This is the number of grasses to be placed on the map.
     */
    void placeGrasses(int grassNo);

    //    ------------------------------------------
    /**
    * This method TRIES to breed All Animals on map,
     * in every position dividing them into pairs and try to breed them.
    */
    void tryToBreedAllAnimals();
    //   ------------------------------------------

    /**
     * This method is used to remove all dead animals from the map.
     * It should be called once per tick (Game Day).
     */
    void tryToRemoveAllDeadAnimals();

    //    ------------------------------------------
     /**
     * This method is used to increment the age of all animals on the map, and take some of theirs Energy.
     * It should be called once per tick (Game Day).
     * The specific implementation of this method should ensure that the age of each Animal object on the map is increased.
     */
    void makeAllAnimalsOlder();
    //    ------------------------------------------

    /**
     * This method is used to get the strongest occupant from a certain position.
     * In first place Animal, then Grass.
     * @param position This is a Vector2d object that represents the position from which the occupant is to be retrieved.
     * @return WorldElement_able This returns the strongest occupant from the given position, or null if there are no occupants.
     * -> if there are no occupants, then null is returned
     */

    WorldElement_able getOccupantFrom(Vector2d position);

    //    ------------------------------------------
    /**
     * This method is used to get all occupants from a certain position.
     * In first place Animals, then Grasses.
     * @param position This is a Vector2d object that represents the position from which the occupants are to be retrieved.
     * @return List<WorldElement_able> This returns a collection of all occupants from the given position, or an empty collection if there are no occupants.
     * -> if there are no occupants, then an empty List is returned
     */
    List<WorldElement_able> getAllOccupantsFrom(Vector2d position);

    //    ------------------------------------------

    /**
     * This method is used to retrieve all animals present on the map.
     * It returns a list of Animal objects, which represent all the animals currently alive on the map.
     * @return List<Animal> This returns a list of all Animal objects present on the map.
     */
    List<Animal> getAllAnimals();
    //   ------------------------------------------

    @Override
    String toString();

    void addObserver(MapChangeListener observer);
    void removeObserver(MapChangeListener observer);
    void notifyAllObservers(String description);
}
