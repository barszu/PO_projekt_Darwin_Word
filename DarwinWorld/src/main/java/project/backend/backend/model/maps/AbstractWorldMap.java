package project.backend.backend.model.maps;

import project.backend.backend.exceptions.NoPositionLeftException;
import project.backend.backend.extras.ListHashMap;
import project.backend.backend.globalViaSimulation.GlobalOptions;
import project.backend.backend.globalViaSimulation.GlobalVariables;
import project.backend.backend.listeners.IMapChangeListener;
import project.backend.backend.model.enums.BiomeField;
import project.backend.backend.model.maps.mapsUtil.BiomesBuffer;
import project.backend.backend.model.maps.mapsUtil.RectangleBoundary;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.model.sprites.Animal;
import project.backend.backend.model.sprites.Grass;
import project.backend.backend.model.sprites.IWorldElement;
import project.backend.backend.util.MapVisualizer;

import java.util.*;

import static project.backend.backend.extras.MathUtils.isBetween;

public abstract class AbstractWorldMap implements IWorldMap {
    private final List<IMapChangeListener> observersList = new ArrayList<>();

    protected final BiomesBuffer biomesBuffer;
    protected final RectangleBoundary rectangleBox;

    protected final GlobalOptions globalOptions;
    protected final GlobalVariables globalVariables;
    protected final ListHashMap<Vector2d, Animal> animalsDict = new ListHashMap<>();
    //{ (x,y) : grass }
    protected final HashMap<Vector2d, Grass> grasses = new HashMap<>();



    protected final List<Animal> recentlySlainedAnimals = new LinkedList<>();

    @Override
    public List<Animal> getRecentlySlainedAnimals() {
        return Collections.unmodifiableList(recentlySlainedAnimals);
    }

    @Override
    public RectangleBoundary getBoundary() {
        return rectangleBox;
    }

    public AbstractWorldMap(GlobalOptions globalOptions , GlobalVariables globalVariables) {
        this.globalOptions = globalOptions;
        this.globalVariables = globalVariables;
        // -1 for solf boundaries on (x,y) squares ~ [mapWidth-1, mapHeight-1]
        //  (x,y) is a bottomLeft point of the square!
        // X , Y axis from normal quarter (1st)
        this.rectangleBox = new RectangleBoundary(new Vector2d(0,0),
                new Vector2d(globalOptions.mapWidth()-1,globalOptions.mapHeight()-1));

        this.biomesBuffer = new BiomesBuffer(this.rectangleBox);
        //and others...
    }

    @Override
    public void updateEverything(){
        recentlySlainedAnimals.clear();

        tryToRemoveAllDeadAnimals();
        makeAllAnimalsOlder();
        moveAllAnimals();
        feedAllAnimals();
        tryToBreedAllAnimals();
        updateColors();
        placeGrasses(globalOptions.plantsPerDay());
    }

    private void updateColors() {
        for(int x=0; x<rectangleBox.width();x++){
            for(int y=0; y<rectangleBox.height();y++){
                try {
                    Animal animal = (Animal) getOccupantFrom(new Vector2d(x,y));
                    int energy = animal.getEnergy();
                    int bestEnergy = globalOptions.energyToBreeding();
                    if(isBetween(0, energy, bestEnergy/5)){
                        animal.setColor("veryBadHealth");
                    }
                    else if(isBetween(bestEnergy/5, energy, 2*bestEnergy/5)){
                        animal.setColor("badHealth");
                    }
                    else if(isBetween(2*bestEnergy/5, energy, 3*bestEnergy/5)){
                        animal.setColor("neutralHealth");
                    }
                    else if(isBetween(3*bestEnergy/5, energy, 4*bestEnergy/5)){
                        animal.setColor("goodHealth");
                    }
                    else{
                        animal.setColor("veryGoodHealth");
                    }
                } catch(ClassCastException | NullPointerException ignored){}

            }
        }
    }

    @Override
    public Vector2d validatePosition(Vector2d newPosition , Vector2d oldPosition){
        //default implementation, withing in Rectangle?
        if (rectangleBox.contains(newPosition)){
            return newPosition;
        }
        return oldPosition;
    }

    protected void tryToRemoveAllDeadAnimals() {
        for (Animal animal : getAllAnimals()) {
            if (animal.checkIfDead()){
//                System.out.println("Animal "+animal+" has died at: " + animal.getPosition());
                recentlySlainedAnimals.add(animal);
                animalsDict.removeFrom(animal.getPosition() , animal); //remove from map
            }
        }
    }

    protected void moveAllAnimals() {
        for (Animal animal : getAllAnimals()) {
            Vector2d oldPosition = animal.getPosition();
            animalsDict.removeFrom(animal.getPosition(), animal); //temp removal
            animal.move(this);
            animalsDict.putInside(animal.getPosition(), animal);
//            System.out.println("Animal "+animal+" has moved from: " + oldPosition + " to: " + animal.getPosition());
        }
    }

    protected void makeAllAnimalsOlder(){
        for (Animal animal : getAllAnimals()) {
            animal.incrementAge();
            animal.subtractEnergy(1);
        }
    }

    @Override
    public void placeGrasses(int grassNo) { //init map , run
        for (int i=0; i<grassNo; i++){
            try {
                Vector2d position = biomesBuffer.giveFreePosition();
                Grass grass = new Grass(position);
                grasses.put(position, grass);
//                System.out.println("Grass has benn placed at: " + position);
            } catch (NoPositionLeftException e) {
                //nothing to do
                System.out.println("No position left for grass! Day: " + globalVariables.getDate());
            }
        }
    }

    protected void tryToBreedAllAnimals() {
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
    public IWorldElement getOccupantFrom(Vector2d position) {
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
    public List<IWorldElement> getAllOccupantsFrom(Vector2d position) { // in sorted order
        List<IWorldElement> res = new ArrayList<>();
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
    protected void feedAllAnimals(){
        for(Vector2d grassPosition: new ArrayList<>(grasses.keySet()) ){ //TODO: problem nadpisywania slownika?
            if (animalsDict.containsKey(grassPosition)){
                setHierarchy(grassPosition);
                Animal animal = animalsDict.getListFrom(grassPosition).get(0);

                animal.incrementEatenGrassNo();
                animal.addEnergy(globalOptions.energyPerPlant());

                grasses.remove(grassPosition);
                biomesBuffer.handOverPosition(grassPosition);
//                System.out.println("Animal "+animal+" has eaten grass at: " + grassPosition);
            }
        }
    }

    @Override
    public String toString() {
        MapVisualizer mapVis = new MapVisualizer(this);
        return mapVis.draw(rectangleBox.lowerLeft() , rectangleBox.upperRight() );
    }

    @Override
    public BiomeField getBiomeRepresentation(Vector2d position) {
        return biomesBuffer.getBiomeRepresentation(position);
    }

    @Override
    public int getTopGene() {
        HashMap<Integer , Integer> geneCntMap = new HashMap<>();
        for (Animal animal : getAllAnimals()) {
            for (int gene : animal.getGenotype()) {
                geneCntMap.put(gene , geneCntMap.getOrDefault(gene , 0) + 1);
            }
        }
        if (geneCntMap.size() == 0){return -1;} //no animals on map
        Integer bestGene = Collections.max(geneCntMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        return (int) bestGene;
    }

    @Override
    public int getAnimalsNo(){
        return getAllAnimals().size();
    }

    @Override
    public int getGrassesNo(){
        return grasses.size();
    }

    @Override
    public GlobalVariables getGlobalVariables() {
        return globalVariables;
    }

    @Override
    public int getDay() {
        return globalVariables.getDate();
    }

    @Override
    public int getFieldsWithoutGrassNo(){
        return rectangleBox.area() - grasses.size();
    }

    @Override
    public int getFieldsWithoutAnimalsNo(){
        return rectangleBox.area() - animalsDict.size();
    }

    @Override
    public int getEmptyFieldsNo(){
        Set<Vector2d> set = new HashSet<>();
        set.addAll(animalsDict.keySet());
        set.addAll(grasses.keySet());
        return rectangleBox.area() - set.size();
    }

    @Override
    public double getAvgEnergy(){
        return getAllAnimals().stream().mapToDouble(Animal::getEnergy).average().orElse(0.0);
    }

    @Override
    public double getAvgNumberOfChildren(){
        return getAllAnimals().stream()
                .mapToDouble(animal -> animal.getChildrenList().size())
                .average()
                .orElse(0.0);
    }

    @Override
    public double getAvgNumberOfSuccessors(){
        return getAllAnimals().stream()
                .mapToDouble(animal -> animal.getSuccessorsNo())
                .average()
                .orElse(0.0);
    }
    @Override
    public void addObserver(IMapChangeListener observer) {observersList.add(observer);}
    @Override
    public void removeObserver(IMapChangeListener observer) {observersList.remove(observer);}

    @Override
    public void notifyAllObservers(String description) {
        for (IMapChangeListener observer : observersList) {
            observer.mapChanged(this , description);
        }

    }

    public Set<Vector2d> getAnimalsPositionsHavingGene(int gene){
        Set<Vector2d> res = new HashSet<>();
        for (Animal animal : getAllAnimals()) {
            // if animal has gene or more
            if (Arrays.stream(animal.getGenotype()).filter(g -> g == gene).count() > 0){
                res.add(animal.getPosition());
            }
        }
        return res;
    }

}