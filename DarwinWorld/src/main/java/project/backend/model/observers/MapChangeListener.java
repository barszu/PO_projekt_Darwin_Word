package project.backend.model.observers;

import project.backend.model.maps.WorldMap;

public interface MapChangeListener {
    void mapChanged(WorldMap worldMap, String message);
}
