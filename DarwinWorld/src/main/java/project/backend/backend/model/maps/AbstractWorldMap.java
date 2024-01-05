package project.backend.backend.model.maps;

import project.backend.backend.exceptions.NoPositionLeftException;
import project.backend.backend.extras.ListHashMap;
import project.backend.backend.global.GlobalOptions;
import project.backend.backend.global.GlobalVariables;
import project.backend.backend.listeners.MapChangeListener;
import project.backend.backend.model.maps.mapsUtil.Biomes;
import project.backend.backend.model.maps.mapsUtil.RectangleBoundary;
import project.backend.backend.extras.Random;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.model.sprites.Animal;
import project.backend.backend.model.sprites.Grass;
import project.backend.backend.model.sprites.WorldElement_able;
import project.backend.backend.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap_able{
    private final List<MapChangeListener> observersList = new ArrayList<>();

    @Override
    public RectangleBoundary getBoundary() {
        return rectangleBox;
    }

    protected final Biomes biomes;
    protected final RectangleBoundary rectangleBox;
    protected final GlobalOptions globalOptions;
    protected final GlobalVariables globalVariables;
    protected final ListHashMap<Vector2d, Animal> animalsDict = new ListHashMap<>();
    //{ (x,y) : grass }
    protected final HashMap<Vector2d, Grass> grasses = new HashMap<>();


    public AbstractWorldMap(GlobalOptions globalOptions , GlobalVariables globalVariables) {
        this.globalOptions = globalOptions;
        this.globalVariables = globalVariables;
        // -1 for solf boundaries on (x,y) squares ~ [mapWidth-1, mapHeight-1]
        //  (x,y) is a bottomLeft point of the square!
        // X , Y axis from normal quarter (1st)
        this.rectangleBox = new RectangleBoundary(new Vector2d(0,0),
                new Vector2d(globalOptions.mapWidth()-1,globalOptions.mapHeight()-1));

        this.biomes = new Biomes(this.rectangleBox);
        //and others...
    }

    @Override
    public void updateEverything(){
        tryToRemoveAllDeadAnimals();
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
    public void tryToRemoveAllDeadAnimals() {
        for (Animal animal : getAllAnimals()) {
            if (animal.checkIfDead()){
                System.out.println("Animal "+animal+" has died at: " + animal.getPosition());
                animalsDict.removeFrom(animal.getPosition() , animal); //remove from map
            }
        }
    }

    @Override
    public void moveAllAnimals() {
        for (Animal animal : getAllAnimals()) {
            Vector2d oldPosition = animal.getPosition();
            animalsDict.removeFrom(animal.getPosition(), animal); //temp removal
            animal.move(this);
            animalsDict.putInside(animal.getPosition(), animal);
            System.out.println("Animal "+animal+" has moved from: " + oldPosition + " to: " + animal.getPosition());
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
        for (int i=0; i<grassNo; i++){
            try {
                Vector2d position = biomes.giveFreePosition();
                Grass grass = new Grass(position);
                grasses.put(position, grass);
                System.out.println("Grass has benn placed at: " + position);
            } catch (NoPositionLeftException e) {
                //nothing to do
                System.out.println("No position left for grass! Day: " + globalVariables.getDate());
            }
        }
    }



    @Override
    public void tryToBreedAllAnimals() {
        for(Vector2d position: animalsDict.keySet()){
            setHierarchy(position);
            List<Animal> animalsOnPosition = animalsDict.getListFrom(position); //raw list

            int couplesNo = animalsOnPosition.size()/2;
            //TODO: out of list danger!
            for(int i=0; i<couplesNo; i++){
                Animal leftAnimal = animalsOnPosition.get(2*i);
                Animal rightAnimal = animalsOnPosition.get(2*i+1);
                if (leftAnimal.isWellFed() && rightAnimal.isWellFed()){
                    Animal child = leftAnimal.reproduce(rightAnimal);
                    animalsDict.putInside(position, child);
                }
                else{
                    break;
                    //there will be no more well fed animals on this position, because list is sorted
                }
            }
        }
    }

    @Override
    public WorldElement_able getOccupantFrom(Vector2d position) {
        if (animalsDict.containsKey(position)){
            setHierarchy(position);
            return animalsDict.getListFrom(position).get(0); //return first best animal
        }
        else if (grasses.containsKey(position)){
            return grasses.get(position); //return grass
        }
        return null;
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

    //Sets Animal order within animals on same position
    public void setHierarchy(Vector2d position){
        animalsDict.getListFrom(position).sort(Animal::compareTo);
    }

    //claims one grass for one animal
    public void feedAllAnimals(){
        for(Vector2d grassPosition: new ArrayList<>(grasses.keySet()) ){ //TODO: problem nadpisywania slownika?
            if (animalsDict.containsKey(grassPosition)){
                setHierarchy(grassPosition);
                Animal animal = animalsDict.getListFrom(grassPosition).get(0);

                animal.incrementEatenGrassNo();
                animal.addEnergy(globalOptions.energyPerPlant());

                grasses.remove(grassPosition);
                biomes.handOverPosition(grassPosition);
                System.out.println("Animal "+animal+" has eaten grass at: " + grassPosition);
            }
        }
    }

    @Override
    public String toString() {
        MapVisualizer mapVis = new MapVisualizer(this);
        return mapVis.draw(rectangleBox.lowerLeft() , rectangleBox.upperRight().add(new Vector2d(1,1)) );
    }

    @Override
    public String getBiomeRepresentation(Vector2d position) {
        return biomes.getBiomeRepresentation(position);
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