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
    protected final List<Vector2d> freeOfGrassPositions = new ArrayList<>();
    protected final List<Vector2d> attractiveGrassFields= new ArrayList<>();
    protected final List<Vector2d> nonAttractiveGrassFields= new ArrayList<>();
    protected final List<Vector2d> jungle = new ArrayList<>();
    public AbstractWorldMap(GlobalOptions globalOptions , GlobalVariables globalVariables) {
        this.globalOptions = globalOptions;
        this.globalVariables = globalVariables;
        // -1 for solf boundaries on (x,y) squares ~ [mapWidth-1, mapHeight-1]
        //  (x,y) is a bottomLeft point of the square!
        // X , Y axis from normal quarter (1st)
        this.rectangleBox = new RectangleBoundary(new Vector2d(0,0),
                new Vector2d(globalOptions.mapWidth()-1,globalOptions.mapHeight()-1));

//      Approximately 20% of fields is considered more attractive for plants
        for(int i=0; i<globalOptions.mapWidth();i++){
            for(int j=0; i<globalOptions.mapHeight();j++){
                int randNum = Random.randInt(1,5);
                if(randNum==1){
                    attractiveGrassFields.add(new Vector2d(i,j));
                }
                else{
                    nonAttractiveGrassFields.add(new Vector2d(i,j));
                }
            }
        }
        generateJungle();
        initAllAnimals();
        placeGrasses(globalOptions.initPlantsNo());
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
        Collections.shuffle(attractiveGrassFields);
        Collections.shuffle(nonAttractiveGrassFields);
        int attractiveIdx = 0;
        int nonAttractiveIdx = 0;
        int jungleIdx = 0;
        for(int i=0;i<globalOptions.plantsPerDay();i++){
            int decidingNumber = Random.randInt(1,5);
            if(decidingNumber == 1 || jungleIdx >= jungle.size()){
                int decidingNumber2 = Random.randInt(1,5);
                if(decidingNumber2 == 1 || attractiveIdx >= attractiveGrassFields.size()){
                    grasses.put(nonAttractiveGrassFields.get(nonAttractiveIdx), new Grass(nonAttractiveGrassFields.get(nonAttractiveIdx)));
                    nonAttractiveIdx++;
                }
                else{
                    grasses.put(attractiveGrassFields.get(attractiveIdx), new Grass(attractiveGrassFields.get(attractiveIdx)));
                    attractiveIdx++;
                }
            }
            else{
                grasses.put(jungle.get(jungleIdx), new Grass(jungle.get(jungleIdx)));
                jungleIdx++;
            }
        }
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

//    Generates positions of fields in circle that has center in "center" and radius "radius"
    public List<Vector2d> fieldsInRadius(int radius, Vector2d center){
        List<Vector2d> fieldsToAdd = new ArrayList<>();
        for(int i=center.getX()-radius; i<center.getX()+2*radius-1;i++){
            for(int j=center.getY()-radius;j<center.getY()+2*radius-1;j++){
                if(i>=0 && i<=globalOptions.mapWidth()-1 && j>=0 && j<= globalOptions.mapHeight()-1){
                    if(Math.sqrt((center.getX()-i)^2+(center.getY()-j)^2)<=radius){
                        fieldsToAdd.add(new Vector2d(i,j));
                    }
                }
            }
        }
        return fieldsToAdd;
    }

    public void generateJungle(){
        int numberOfForests = Random.randInt(1, 1+(int)(globalOptions.mapWidth()* globalOptions.mapHeight()/2500));
        for(int i=0; i<numberOfForests; i++){
            int x = Random.randInt(0, globalOptions.mapWidth()-1);
            int y = Random.randInt(0, globalOptions.mapHeight()-1);
            Vector2d newCenter = new Vector2d(x,y);
            jungle.add(newCenter);
            List<Vector2d> surroundings = fieldsInRadius((int)(globalOptions.mapWidth()/20),newCenter);
            jungle.addAll(surroundings);
        }
        Collections.shuffle(jungle);
        for(int i=0; i<numberOfForests*4; i++){
            List<Vector2d> surroundings = fieldsInRadius((int)(globalOptions.mapWidth()/20), jungle.get(i));
            jungle.addAll(surroundings);
        }
    }

}
