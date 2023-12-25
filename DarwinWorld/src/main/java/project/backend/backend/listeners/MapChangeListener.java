package project.backend.backend.listeners;


import project.backend.backend.model.maps.WorldMap_able;

public interface MapChangeListener {
    void mapChanged(WorldMap_able worldMap_able, String message);
}
