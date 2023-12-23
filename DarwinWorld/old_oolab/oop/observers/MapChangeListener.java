package agh.ics.oop.observers;

import agh.ics.oop.model.WorldMap_able;

public interface MapChangeListener {
    void mapChanged(WorldMap_able worldMapable, String message);
}
