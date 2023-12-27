package project.backend.backend.model.maps;

import project.backend.backend.extras.ListHashMap;
import project.backend.backend.global.GlobalOptions;
import project.backend.backend.global.GlobalVariables;
import project.backend.backend.listeners.MapChangeListener;
import project.backend.backend.model.maps.mapsUtil.RectangleBoundary;
import project.backend.backend.extras.Random;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.model.sprites.Animal;
import project.backend.backend.model.sprites.Grass;
import project.backend.backend.model.sprites.WorldElement_able;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractWorldMap implements WorldMap_able{
//    TODO: init grasses!!!
    private final List<MapChangeListener> observersList = new ArrayList<>();


    protected final RectangleBoundary rectangleBox;
    protected final GlobalOptions globalOptions;
    protected final GlobalVariables globalVariables;

    //{ (x,y) : ordered?[animal1, animal2, ...]
    //TODO: ordrered list?
    protected final ListHashMap<Vector2d, Animal> animalsDict = new ListHashMap<>();

    //{ (x,y) : grass }
    protected final HashMap<Vector2d, Grass> grasses = new HashMap<>();

    //TODO: biomes?
    protected final List<Vector2d> freeOfGrassPositions = new ArrayList<>();

    public AbstractWorldMap(GlobalOptions globalOptions , GlobalVariables globalVariables) {
        this.globalOptions = globalOptions;
        this.globalVariables = globalVariables;
        // -1 for solf boundaries on (x,y) squares ~ [mapWidth-1, mapHeight-1]
        //  (x,y) is a bottomLeft point of the square!
        // X , Y axis from normal quarter (1st)
        this.rectangleBox = new RectangleBoundary(new Vector2d(0,0),
                new Vector2d(globalOptions.mapWidth()-1,globalOptions.mapHeight()-1));

        initAllAnimals();
        placeGrasses(globalOptions.plantsPerDay()); //TODO: initPlantsNo ? in global options
    }

    @Override
    public void updateEverything(){
        //TODO: implement this!
        moveAllAnimals();
    }

    @Override
    //default implementation, withing in Rectangle?
    public Vector2d validatePosition(Vector2d newPosition , Vector2d oldPosition){
        if (rectangleBox.contains(newPosition)){
            return newPosition;
        }
        return oldPosition;
    }

    @Override
    public void initAllAnimals() {
        for (int i = 0; i < globalOptions.initAnimalsNo(); i++) {
            //TODO: init animals! spawn on free positions
            Animal animal = new Animal(Random.randPosition(rectangleBox) , globalOptions , globalVariables);

            animalsDict.putInside(animal.getPosition() , animal);
        }
    }

    @Override
    public void moveAllAnimals() {
        for (Vector2d position : animalsDict.keySet()) {
            List<Animal> animalList = animalsDict.getListFrom(position);
            for (Animal animal : animalList) {
                moveAnimal(animal);
                //TODO: breed animals, problem mnogosciowy
            }
        }
    }

    @Override
    public void moveAnimal(Animal animal) {
        animalsDict.removeFrom(animal.getPosition() , animal);
        animal.move(this);
        animalsDict.putInside(animal.getPosition() , animal);

        //TODO: breed animals, problem mnogosciowy
    }

    @Override
    public void placeGrasses(int grassNo) { //init map , run
        //TODO: Simon algorithm
        //TODO: use shuffle
    }

    @Override
    public void tryToBreedAnimals(Animal animal1, Animal animal2) {
        //they are on the same position!
        if (animal1.getPosition().equals(animal2.getPosition())){
            throw new IllegalArgumentException("Tried to breed animals on the different position!");
        }


    }

    @Override
    public WorldElement_able getOccupantFrom(Vector2d position) {
        return null;
    }

    @Override
    public List<WorldElement_able> getAllOccupantsFrom(Vector2d position) { // in sorted order
        //TODO: moze zwracac liste?
        //zwroc pusta liste jesli nie ma
        if (!animalsDict.containsKey(position)){
            return null;
        }

        List<Animal> res = animalsDict.getListFrom(position);

        return null;
    }

    @Override
    public void addObserver(MapChangeListener observer) {observersList.add(observer);}
    @Override
    public void removeObserver(MapChangeListener observer) {observersList.remove(observer);}

    @Override
    public void notifyAllObservers(String description) {
        for (MapChangeListener observer : observersList) {
            observer.mapChanged(this , description);
        }

    }
}
