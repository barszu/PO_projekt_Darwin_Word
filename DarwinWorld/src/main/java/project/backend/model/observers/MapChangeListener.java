package project.backend.model.observers;

import project.backend.model.maps.WorldMapable;

public interface MapChangeListener {
    void mapChanged(WorldMapable worldMapable, String message);
}
