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

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap_able{
    private final List<MapChangeListener> observersList = new ArrayList<>();


    protected final RectangleBoundary rectangleBox;
    protected final GlobalOptions globalOptions;
    protected final GlobalVariables globalVariables;
    protected final ListHashMap<Vector2d, Animal> animalsDict = new ListHashMap<>();
    //{ (x,y) : grass }
    protected List<Animal> orderedAnimalList = new ArrayList<>();
    protected final HashMap<Vector2d, Grass> grasses = new HashMap<>();
    protected List<Vector2d> stepFreePositions = new ArrayList<>();
    protected List<Vector2d> jungleFreePositions = new ArrayList<>();
    public AbstractWorldMap(GlobalOptions globalOptions , GlobalVariables globalVariables) {
        this.globalOptions = globalOptions;
        this.globalVariables = globalVariables;
        // -1 for solf boundaries on (x,y) squares ~ [mapWidth-1, mapHeight-1]
        //  (x,y) is a bottomLeft point of the square!
        // X , Y axis from normal quarter (1st)
        this.rectangleBox = new RectangleBoundary(new Vector2d(0,0),
                new Vector2d(globalOptions.mapWidth()-1,globalOptions.mapHeight()-1));
    }

    @Override
    public void updateEverything(){
        tryToRemoveAllDeadAnimals();
        setHierarchy(orderedAnimalList);
        makeAllAnimalsOlder();
        moveAllAnimals();
        feedAllAnimals();
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
        Collections.shuffle(stepFreePositions);
        Collections.shuffle(jungleFreePositions);
        for(int i=0; i<grassNo;i++){
            int decidingNumber = Random.randInt(1,5);
            if(decidingNumber == 1 || jungleFreePositions.isEmpty()){
                if(!stepFreePositions.isEmpty()){
                    grasses.put(stepFreePositions.get(0), new Grass(stepFreePositions.get(0)));
                    stepFreePositions.remove(0);
                }
            }
            else{
                grasses.put(jungleFreePositions.get(0), new Grass(jungleFreePositions.get(0)));
                jungleFreePositions.remove(0);
            }
        }
    }

    public void addToFreeFields(Vector2d position){
        int equator = (int)(globalOptions.mapHeight()/2);
        int radius = (int)(globalOptions.mapHeight()/10);
        if(position.getY() >= equator-radius && position.getY() <= equator+radius){
            if(!jungleFreePositions.contains(position)){
                jungleFreePositions.add(position);
            }
        }
        else{
            if(!stepFreePositions.contains(position)){
                stepFreePositions.add(position);
            }
        }
    }

    @Override
    public void tryToBreedAllAnimals() {
        for(Vector2d position: animalsDict.keySet()){
            List<Animal> animalsOnPosition = animalsDict.getListFrom(position);
            animalsOnPosition.removeIf(animal -> animal.getEnergy() < globalOptions.energyToBreeding());
            setHierarchy(animalsOnPosition);
            int couplesNo = (int)(animalsOnPosition.size()/2);
            for(int i=0; i<couplesNo; i++){
                animalsDict.putInside(position, animalsOnPosition.get(2*i).reproduce(animalsOnPosition.get(2*i+1)));
            }
        }
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

    public void setHierarchy(List<Animal> animalList){
        animalList.sort(new Comparator<Animal>() {
            @Override
            public int compare(Animal animal1, Animal animal2) {
                if (animal1.getEnergy() != animal2.getEnergy()) {
                    return Integer.compare(animal2.getEnergy(), animal1.getEnergy());
                } else if (animal1.getAge() != animal2.getAge()) {
                    return Integer.compare(animal2.getAge(), animal1.getAge());
                } else if (animal1.getChildrenList().size() != animal2.getChildrenList().size()) {
                    return Integer.compare(animal2.getChildrenList().size(), animal1.getChildrenList().size());
                } else {
                    return Double.compare(Math.random(), Math.random());
                }
            }
        });
    }

    public void feedAllAnimals(){
        for(Animal animal : orderedAnimalList){
            if(grasses.remove(animal.getPosition()) != null){
                animal.incrementEatenGrassNo();
                animal.addEnergy(globalOptions.energyPerPlant());
                addToFreeFields(animal.getPosition());
            }
        }
    }

    public void removeFromFreeFields(Vector2d position){
        int equator = (int)(globalOptions.mapHeight()/2);
        int radius = (int)(globalOptions.mapHeight()/10);
        if(position.getY() >= equator-radius && position.getY() <= equator+radius){
            jungleFreePositions.remove(position);
        }
        else{
            stepFreePositions.remove(position);
        }
    }
}