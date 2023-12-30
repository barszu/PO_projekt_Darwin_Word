package project.backend.backend.listeners;

import project.backend.backend.model.maps.WorldMap_able;
import project.backend.backend.model.sprites.Animal;

import java.util.List;

public class MapConsoleLogger implements MapChangeListener{
    @Override
    public void mapChanged(WorldMap_able worldMap_able, String message) {
        System.out.println(message);
        List<Animal> animals = worldMap_able.getAllAnimals();
//        animals.sort(Animal::compareTo);
        System.out.println(animals);
    }
}
