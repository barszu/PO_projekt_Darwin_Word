package project.backend.backend.listeners;

import project.backend.backend.model.maps.IWorldMap;

/**
 * This interface defines a listener for changes in the map.
 * Classes that implement this interface can react to changes in the map by implementing the mapChanged method.
 */
public interface IMapChangeListener {

    /**
     * This method is called when the map changes.
     * Implementing classes should define what actions to take when the map changes.
     *
     * @param worldMap_able The map that has changed.
     * @param message A message describing the change.
     */
    void mapChanged(IWorldMap worldMap_able, String message);
}