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
        tryToRemoveAllDeadAnimals();
        makeAllAnimalsOlder();
        moveAllAnimals();
        tryToBreedAllAnimals();
        placeGrasses(globalOptions.plantsPerDay());
    }

    @Override
    public Vector2d validatePosition(Vector2d newPosition , Vector2d oldPosition){
        //default implementation, withing in Rectangle?
        if (rectangleBox.contains(newPosition)){
            return newPosition;
        }
        return oldPosition;
    }

    @Override
    public void initAllAnimals() {
        for (int i = 0; i < globalOptions.initAnimalsNo(); i++) {
            //animals can stack on the same position, position within rectangle
            Animal animal = new Animal(Random.randPosition(rectangleBox) , globalOptions , globalVariables);
            animalsDict.putInside(animal.getPosition() , animal);
        }
    }

    @Override
    public void tryToRemoveAllDeadAnimals() {
        for (Animal animal : getAllAnimals()) {
            if (animal.checkIfDead()){
                animalsDict.removeFrom(animal.getPosition() , animal); //remove from map
            }
        }
    }

    @Override
    public void moveAllAnimals() {
        for (Animal animal : getAllAnimals()) {
            animalsDict.removeFrom(animal.getPosition(), animal); //temp removal
            animal.move(this);
            animalsDict.putInside(animal.getPosition(), animal);
        }
    }

    @Override
    public void makeAllAnimalsOlder(){
        for (Animal animal : getAllAnimals()) {
            animal.incrementAge();
            animal.subtractEnergy(1);
        }
    }

    @Override
    public void placeGrasses(int grassNo) { //init map , run
        //TODO: Simon algorithm
        //TODO: use shuffle
    }

    @Override
    public void tryToBreedAllAnimals() {
        //TODO: implement this Simon!
    }

    @Override
    public WorldElement_able getOccupantFrom(Vector2d position) {
        List<Animal> res = animalsDict.getListFrom(position);
        if (res != null){ //return first best animal
            return res.get(0);
        }
        return grasses.get(position); //return grass or null if nothing there
    }

    @Override
    public List<WorldElement_able> getAllOccupantsFrom(Vector2d position) { // in sorted order
        List<WorldElement_able> res = new ArrayList<>();
        List<Animal> animals = animalsDict.getListFrom(position);
        Grass grass = grasses.get(position);

        if (animals != null){res.addAll(animals);}
        if (grass != null){res.add(grass);}
        return res;
    }

    @Override
    public List<Animal> getAllAnimals(){
        List<Animal> res = new ArrayList<>();
        for (Vector2d position : animalsDict.keySet()) {
            List<Animal> animalList = animalsDict.getListFrom(position);
            res.addAll(animalList);
        }
        return res;
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
