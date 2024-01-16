package project.backend.backend.model.maps;

import project.backend.backend.extras.CyclicListExtras;
import project.backend.backend.extras.Random;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.global.GlobalOptions;
import project.backend.backend.global.GlobalVariables;
import project.backend.backend.model.enums.BiomeField;
import project.backend.backend.model.maps.mapsUtil.Biomes;
import project.backend.backend.model.maps.mapsUtil.RectangleBoundary;
import project.backend.backend.model.sprites.Animal;
import project.backend.backend.model.sprites.WorldElement_able;

import java.util.*;

public class WaterMap extends AbstractWorldMap{
    //TODO: bardzo duzy problem ze w waterPositions i waterEdges moze ta sama pozycja sie kilka razy pojawic -> solve sety
    private final Set<Vector2d> waterPositions = new HashSet<>();
    private int waterPhase;
    private Set<Vector2d> waterEdges = new HashSet<>(); //egdes from waterPositions


    public List<Vector2d> getWaterPositions() {
        return new ArrayList<>(waterPositions);
    }
    public List<Vector2d> getWaterEdges() {return new ArrayList<>(waterEdges);}



    public WaterMap(GlobalOptions globalOptions, GlobalVariables globalVariables) {
        super(globalOptions, globalVariables);

        waterPhase = 0;
        generateWater();
        waterEdges.addAll(findWaterEdges());

        for (Vector2d waterPosition : waterPositions) {
            biomes.giveExactFreePosition(waterPosition);
             //kicking that position from free positions
        }

        initAllAnimals();
        placeGrasses(globalOptions.initGrassNo());
    }

    @Override
    public void updateEverything(){
        moveWater(waterPhase);
        super.updateEverything();
        waterPhase = CyclicListExtras.getIncrementedIdx(waterPhase,4);
    }
    @Override
    public Vector2d validatePosition(Vector2d newPosition , Vector2d oldPosition) {
        if (!super.validatePosition(newPosition, oldPosition).equals(newPosition)) { //not within the rectangle
            return oldPosition;
        }
        if(waterPositions.contains(newPosition)){
            return oldPosition;
        }
        return newPosition;
    }

    @Override
    public void initAllAnimals() {
        List<Vector2d> notWaterPositions = biomes.getAllFreePositions(); // positions without water
        for (int i = 0; i < globalOptions.initAnimalsNo(); i++) {
            int decidingNumber = Random.randInt(0, notWaterPositions.size()-1);
            Animal animal = new Animal(notWaterPositions.get(decidingNumber), globalOptions , globalVariables);
            animalsDict.putInside(animal.getPosition() , animal);
        }
    }

    @Override
    public BiomeField getBiomeRepresentation(Vector2d position){
        if(waterPositions.contains(position)){
            return BiomeField.WATER;
        }
        return super.getBiomeRepresentation(position);
    }






    //water generation part

    //    finds a circle of points on map at most "radius" fields far from "center" (Euclidean distance)
    //    NOTE: returns only fields that aren't in waterPositions yet
    private List<Vector2d> fieldsInRadius(int radius, Vector2d center){
        List<Vector2d> fieldsToAdd = new ArrayList<>();
        for(int i=center.getX()-radius; i<center.getX()+2*radius;i++){
            for(int j=center.getY()-radius;j<center.getY()+2*radius;j++){
                if(i>=0 && i<=globalOptions.mapWidth()-1 && j>=0 && j<= globalOptions.mapHeight()-1){
                    if(Math.sqrt((center.getX()-i)^2+(center.getY()-j)^2)<=radius){
                        if(!waterPositions.contains(new Vector2d(i,j))){
                            fieldsToAdd.add(new Vector2d(i,j));
                        }
                    }
                }
            }
        }
        return fieldsToAdd;
    }

    private void generateWater(){ //TODO: podczas initu mapy tworzone
//        Approximately one water pool on 50x50 map field
        int numberOfPools = Random.randInt(1, 1+globalOptions.mapWidth()* globalOptions.mapHeight()/400);
        for(int i=0; i<numberOfPools; i++){
            int x = Random.randInt(0, globalOptions.mapWidth()-1);
            int y = Random.randInt(0, globalOptions.mapHeight()-1);
            Vector2d newCenter = new Vector2d(x,y);
            waterPositions.add(newCenter);
            List<Vector2d> surroundings = fieldsInRadius(globalOptions.mapWidth()/20,newCenter);
            waterPositions.addAll(surroundings);
        }

        List<Vector2d> shuffledWaterPositions = new ArrayList<>(waterPositions);
        Collections.shuffle(shuffledWaterPositions);

        int newNumberOfPools = Math.min(numberOfPools*4,shuffledWaterPositions.size());
        for(int i=0; i<newNumberOfPools; i++){
            List<Vector2d> surroundings = fieldsInRadius(globalOptions.mapWidth()/20, shuffledWaterPositions.get(i));
            waterPositions.addAll(surroundings);
        }
    }

    private List<Vector2d> findWaterEdges(){
        List<Vector2d> edges = new ArrayList<>();
        for(Vector2d position : waterPositions){
            for (Vector2d neighbor : getNeighbors(position)) {
                if(!waterPositions.contains(neighbor)){
                    edges.add(position);
                    break;
                }
            }
        }
        return edges;
    }

    private void moveWater(int waterPhase) {
        switch (waterPhase) {
            case 0, 1 -> {
//                inflows 0, 1
                Set<Vector2d> newEdges = new HashSet<>();
                for (Vector2d edge : waterEdges) {
                    for (Vector2d neighbor : getNeighbors(edge)) {
                        if (!waterPositions.contains(neighbor)) {
//                            TODO: uwaga neighbor wychodzi poza reclangleBox!

                            if (grasses.containsKey(neighbor)){ //flooded grass removed
                                grasses.remove(neighbor);
                                biomes.handOverPosition(neighbor);
                            }


                            newEdges.add(neighbor);
                            waterPositions.add(neighbor);
//                          removeFromFreeFields(neighbor);

                            if (rectangleBox.contains(neighbor)){
                                biomes.giveExactFreePosition(neighbor);

                            }

                        }
                    }
                }
                waterEdges = newEdges;
            }
            case 2, 3 -> {
//                outflows 2, 3
                Set<Vector2d> newEdges = new HashSet<>();
                for (Vector2d edge : waterEdges) {
                    for (Vector2d neighbor : getNeighbors(edge)) {
                        if (waterPositions.contains(neighbor) && !waterEdges.contains(neighbor)){
                            newEdges.add(neighbor);
                            waterPositions.remove(edge);

//                          addToFreeFields(edge);

                            try { //try to hand over position to biomes
                                biomes.handOverPosition(edge);
                            } catch (IllegalArgumentException ignored) {}

                        }
                    }
                }
                waterEdges = newEdges;
            }
        }
    }

    private Vector2d[] getNeighbors(Vector2d position){
        Vector2d neighborAbove = new Vector2d(position.getX(), position.getY() + 1);
        Vector2d neighborBelow = new Vector2d(position.getX(), position.getY() - 1);
        Vector2d neighborLeft = new Vector2d(position.getX() - 1, position.getY());
        Vector2d neighborRight = new Vector2d(position.getX() + 1, position.getY());
        return new Vector2d[]{neighborAbove, neighborBelow, neighborLeft, neighborRight};
    }


}