package agh.ics.oop.model;

import agh.ics.oop.exceptions.PositionAlreadyOccupiedException;
import project.backend.model.maps.mapsUtil.RectangleBoundary;
import agh.ics.oop.observers.MapChangeListener;
import project.backend.model.sprites.Animal;
import project.backend.model.models.Vector2d;
import project.backend.model.sprites.WorldElementable;
import agh.ics.oop.util.MapVisualizer;

import java.util.*;

abstract class AbstractWorldMap implements WorldMap_able {

    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer mapVis = new MapVisualizer(this);
    private final List<MapChangeListener> observersList = new ArrayList<>();

    protected final UUID id;

    public AbstractWorldMap(UUID id){
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void place(Animal animal) throws PositionAlreadyOccupiedException { //as animal
        if (canMoveTo(animal.getPosition())){  //popraw przy zmianie canMoveTo
            this.animals.put(animal.getPosition() , animal);
            mapChanged(String.format("Animal: '%s' has been placed on '%s'",animal,animal.getPosition()));
        }
        else { //animal position out of map for example
            throw new PositionAlreadyOccupiedException(animal.getPosition());
        }
    }

    @Override
    public void move(Animal animal , MoveDirection direction) { //as animal
        if (!animals.containsKey(animal.getPosition())){
            return;
        }
        animals.remove(animal.getPosition());
        try {
            Vector2d oldAnimalPos = animal.getPosition();
            animal.move(direction, this);
            animals.put(animal.getPosition() , animal);
            mapChanged(String.format("Animal: '%s' has been moved from '%s' to '%s'",animal,oldAnimalPos,animal.getPosition()));


        } catch (PositionAlreadyOccupiedException e){
            // nothing to do!
            animals.put(animal.getPosition() , animal);
            System.out.println("WARNING (Animal move was skipped and position not modified): When moving Animal: " + e.getMessage());
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {//as animal
        return animals.containsKey(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return !animals.containsKey(position); //colisions with other animals ON
    }

    @Override
    public WorldElementable objectAt(Vector2d position){
        if (animals.containsKey(position)){ //by animal
            return animals.get(position);
        }
        else return null;
    }

    @Override
    public Collection<WorldElementable> getElements(){
        return new ArrayList<>(animals.values());
    }

    @Override
    public abstract RectangleBoundary getCurrentBounds();

    @Override
    public String toString() {
        RectangleBoundary mapRectangleBoundaryRepr = getCurrentBounds();
        if (mapRectangleBoundaryRepr == null){ //some __repr__
            return mapVis.draw(new Vector2d(0,0),new Vector2d(1,1));
        }
        return mapVis.draw(mapRectangleBoundaryRepr.lowerLeft() , mapRectangleBoundaryRepr.upperRight() );
    }

    // observers section

    @Override
    public void addObserver(MapChangeListener observer) {observersList.add(observer);}
    @Override
    public void removeObserver(MapChangeListener observer) {observersList.remove(observer);}

    protected void mapChanged(String description) {
        //notify all observers
        for (MapChangeListener observer : observersList) {
            observer.mapChanged(this , description);
        }

    }


}
