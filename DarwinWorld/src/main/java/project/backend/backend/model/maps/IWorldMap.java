package project.backend.backend.model.maps;

import project.backend.backend.globalViaSimulation.GlobalVariables;
import project.backend.backend.listeners.IMapChangeListener;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.model.maps.mapsUtil.RectangleBoundary;
import project.backend.backend.model.enums.BiomeField;
import project.backend.backend.model.sprites.Animal;
import project.backend.backend.model.sprites.IWorldElement;

import java.util.List;

/**
 * The IWorldMap interface should be implemented by any class whose instances are intended to represent a world map.
 * The class that implements this interface needs to define several methods related to the management of animals and grasses on the map,
 * as well as the retrieval of occupants from a certain position.
 */
public interface IWorldMap extends IMoveValidator {

    /**
     * This method is used to update all elements on the map.
     * Move Animals, puts Grass etc.
     * It should be called once per tick (Game Day).
    */
    void updateEverything();

    /**
     * This method is used to initialize and place all animals on the map.
     * The number of animals to be initialized is determined by GlobalOptions.initAnimalsNo().
     * The special Animal constructor is used for the initial animals.
     */
    void initAllAnimals();

    /**
     * This method is used to place a certain number of grasses on the map.
     * @param grassNo The number of grasses to place on the map.
     */
    void placeGrasses(int grassNo);

    /**
     * This method is used to get the biome representation of a certain position.
     * @param position The position to get the biome representation for.
     * @return The BiomeField representing the biome of the position.
     */
    BiomeField getBiomeRepresentation(Vector2d position);

    /**
     * This method is used to get the strongest occupant from a certain position.
     * In first place Animal, then Grass.
     * @param position The position to get the occupant from.
     * @return The strongest occupant from the given position, or null if there are no occupants.
     */
    IWorldElement getOccupantFrom(Vector2d position);

    /**
     * This method is used to get all occupants from a certain position.
     * In first place Animals, then Grasses.
     * @param position The position to get the occupants from.
     * @return A list of all occupants from the given position, or an empty list if there are no occupants.
     */
    List<IWorldElement> getAllOccupantsFrom(Vector2d position);

    /**
     * This method is used to get the boundary of the map.
     * @return The RectangleBoundary representing the boundary of the map.
     */
    RectangleBoundary getBoundary();

    /**
     * This method is used to get the top gene from all Animals in the map.
     * @return The top gene in the map.
     */
    int getTopGene();

    /**
     * This method is used to get the number of animals in the map.
     * @return The number of animals in the map.
     */
    int getAnimalsNo();

    /**
     * This method is used to get the number of grasses in the map.
     * @return The number of grasses in the map.
     */
    int getGrassesNo();

    /**
     * This method is used to get the current day in the simulation (day on World).
     * @return The current day in the simulation.
     */
    int getDay();

    /**
     * This method is used to get the number of fields without grass in the map.
     * @return The number of fields without grass in the map.
     */
    int getFieldsWithoutGrassNo();

    /**
     * This method is used to get the number of fields without animals in the map.
     * @return The number of fields without animals in the map.
     */
    int getFieldsWithoutAnimalsNo();

    /**
     * This method is used to get the number of empty fields in the map.
     * @return The number of empty fields in the map.
     */
    int getEmptyFieldsNo();

    /**
     * This method is used to get the average energy of all animals in the map.
     * @return The average energy of animals in the map.
     */
    double getAvgEnergy();

    /**
     * This method is used to get the average number of children of all animals in the map.
     * @return The average number of children of animals in the map.
     */
    double getAvgNumberOfChildren();

    /**
     * This method is used to get the average number of successors of all animals in the map.
     * @return The average number of successors of animals in the map.
     */
    double getAvgNumberOfSuccessors();

    /**
     * This method is used to get a list of recently slain animals in the map.
     * @return A list of recently slain animals in the map.
     */
    List<Animal> getRecentlySlainedAnimals();

    /**
     * This method is used to get the global variables of the simulation.
     * @return The GlobalVariables of the simulation.
     */
    GlobalVariables getGlobalVariables();

    /**
     * This method is used to retrieve all animals present on the map.
     * It returns a list of Animal objects, which represent all the animals currently alive on the map.
     * @return A list of all Animal objects present on the map.
     */
    List<Animal> getAllAnimals();

    /**
     * This method is used to add an observer to the map.
     * @param observer The observer to add.
     */
    void addObserver(IMapChangeListener observer);

    /**
     * This method is used to remove an observer from the map.
     * @param observer The observer to remove.
     */
    void removeObserver(IMapChangeListener observer);

    /**
     * This method is used to notify all observers of a change in the map.
     * @param description A description of the change.
     */
    void notifyAllObservers(String description);
}