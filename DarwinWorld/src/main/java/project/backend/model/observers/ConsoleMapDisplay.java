package project.backend.model.observers;

import project.backend.model.maps.WorldMap;

public class ConsoleMapDisplay implements MapChangeListener {

    private int updateCounter = 0;

    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {

        System.out.println(message);
        System.out.println("UUID: " + worldMap.getId());
        System.out.println("Update no: " + updateCounter);

        //only for better visualization
        String mapTypeName = worldMap.getClass().getSimpleName();
        System.out.println("Map Type: " + mapTypeName);

        System.out.print(worldMap);
        updateCounter += 1;
    }
}
