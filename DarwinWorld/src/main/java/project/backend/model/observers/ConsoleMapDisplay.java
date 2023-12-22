package project.backend.model.observers;

import project.backend.model.maps.WorldMapable;

public class ConsoleMapDisplay implements MapChangeListener {

    private int updateCounter = 0;

    @Override
    public synchronized void mapChanged(WorldMapable worldMapable, String message) {

        System.out.println(message);
        System.out.println("UUID: " + worldMapable.getId());
        System.out.println("Update no: " + updateCounter);

        //only for better visualization
        String mapTypeName = worldMapable.getClass().getSimpleName();
        System.out.println("Map Type: " + mapTypeName);

        System.out.print(worldMapable);
        updateCounter += 1;
    }
}
