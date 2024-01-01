package project.backend.backend.listeners;

import project.backend.backend.model.maps.WorldMap_able;

public class ConsoleMapDisplay implements MapChangeListener {

    private int updateCounter = 0;

//    @Override
//    public synchronized void mapChanged(WorldMap_able worldMapable, String message) {
//
//        System.out.println(message);
//        System.out.println("UUID: " + worldMapable.getId());
//        System.out.println("Update no: " + updateCounter);
//
//        //only for better visualization
//        String mapTypeName = worldMapable.getClass().getSimpleName();
//        System.out.println("Map Type: " + mapTypeName);
//
//        System.out.print(worldMapable);
//        updateCounter += 1;
//    }

    @Override
    public void mapChanged(WorldMap_able worldMap_able, String message) {
        System.out.println(message);
        System.out.println("Update no: " + updateCounter);
        System.out.print(worldMap_able);
        updateCounter += 1;
    }
}
