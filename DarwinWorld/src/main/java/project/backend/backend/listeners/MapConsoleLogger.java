package project.backend.backend.listeners;

import project.backend.backend.model.maps.IWorldMap;
import project.backend.backend.model.sprites.Animal;

import java.util.List;

public class MapConsoleLogger implements IMapChangeListener {
    @Override
    public void mapChanged(IWorldMap worldMap_able, String message) {
        System.out.println(message);
        List<Animal> animals = worldMap_able.getAllAnimals();
//        animals.sort(Animal::compareTo);
        System.out.println(animals);
    }
}
