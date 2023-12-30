package project.backend.backend.model.maps;

import project.backend.backend.extras.Random;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.global.GlobalOptions;
import project.backend.backend.global.GlobalVariables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class WaterMap extends AbstractWorldMap{
    //TODO: problem -> how to move a water ?

    private final List<Vector2d> waterPositions = new ArrayList<>();
    private int waterPhase;
    private List<Vector2d> waterEdges = new ArrayList<>();
    public WaterMap(GlobalOptions globalOptions, GlobalVariables globalVariables) {
        super(globalOptions, globalVariables);
        waterPhase = 0;
        generateWater();
        waterEdges.addAll(findWaterEdges());
        int equator = globalOptions.mapHeight()/2;
        int radius = globalOptions.mapHeight()/10;
        for(int i=0; i<globalOptions.mapWidth();i++){
            for(int j=0; j< globalOptions.mapHeight();j++){
                if(j>=equator-radius && j<=equator+radius){
                    if(!waterPositions.contains(new Vector2d(i,j))){
                        jungleFreePositions.add(new Vector2d(i,j));
                    }
                }
                else{
                    if(!waterPositions.contains(new Vector2d(i,j))){
                        stepFreePositions.add(new Vector2d(i,j));
                    }

                }
            }
        }

        orderedAnimalList.addAll(getAllAnimals());
        initAllAnimals();
        setHierarchy(orderedAnimalList);
        placeGrasses(globalOptions.energyPerPlant());
    }

    @Override
    public void updateEverything(){
        if(waterPhase == 4){
            waterPhase = 0;
        }
        moveWater(waterPhase);
        super.updateEverything();
        waterPhase++;
    }
    @Override
    public Vector2d validatePosition(Vector2d newPosition , Vector2d oldPosition) {
        if (super.validatePosition(newPosition, oldPosition).equals(newPosition)) { //within the rectangle
            return newPosition;
        }
        //TODO: check water on newPosition!
        if(waterPositions.contains(newPosition)){
            return oldPosition;
        }
        return newPosition;
    }

    @Override
    public void initAllAnimals() {
        //TODO: init animals on positions without water!
    }

    //TODO: implement this method!
    private void initWaterPositions(int waterNo){
        //TODO: init water positions!
    }

//    finds a circle of points on map at most "radius" fields far from "center" (Euclidean distance)
//    NOTE: returns only fields that aren't in waterPositions yet
    public List<Vector2d> fieldsInRadius(int radius, Vector2d center){
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

    public void generateWater(){
//        Approximately one water pool on 50x50 map field
        int numberOfPools = Random.randInt(1, 1+(int)(globalOptions.mapWidth()* globalOptions.mapHeight()/2500));
        for(int i=0; i<numberOfPools; i++){
            int x = Random.randInt(0, globalOptions.mapWidth()-1);
            int y = Random.randInt(0, globalOptions.mapHeight()-1);
            Vector2d newCenter = new Vector2d(x,y);
            waterPositions.add(newCenter);
            List<Vector2d> surroundings = fieldsInRadius(globalOptions.mapWidth()/20,newCenter);
            waterPositions.addAll(surroundings);
        }
        Collections.shuffle(waterPositions);
        for(int i=0; i<numberOfPools*4; i++){
            List<Vector2d> surroundings = fieldsInRadius(globalOptions.mapWidth()/20, waterPositions.get(i));
            waterPositions.addAll(surroundings);
        }
    }

    public List<Vector2d> findWaterEdges(){
        List<Vector2d> edges = new ArrayList<>();
        for(Vector2d position : waterPositions){
            Vector2d neighborAbove = new Vector2d(position.getX(), position.getY() - 1);
            Vector2d neighborBelow = new Vector2d(position.getX(), position.getY() + 1);
            Vector2d neighborLeft = new Vector2d(position.getX() - 1, position.getY());
            Vector2d neighborRight = new Vector2d(position.getX() + 1, position.getY());
            if(!waterPositions.contains(neighborAbove) ||
                !waterPositions.contains(neighborBelow) ||
                !waterPositions.contains(neighborLeft) ||
                !waterPositions.contains(neighborRight)){
                    edges.add(position);
            }
        }
        return edges;
    }

    public void moveWater(int waterPhase) {
        switch (waterPhase) {
            case 0, 1 -> {
//                inflows 0, 1
                List<Vector2d> newEdges = new ArrayList<>();
                for (Vector2d edge : waterEdges) {
                    Vector2d neighborAbove = new Vector2d(edge.getX(), edge.getY() - 1);
                    Vector2d neighborBelow = new Vector2d(edge.getX(), edge.getY() + 1);
                    Vector2d neighborLeft = new Vector2d(edge.getX() - 1, edge.getY());
                    Vector2d neighborRight = new Vector2d(edge.getX() + 1, edge.getY());
                    if (!waterPositions.contains(neighborAbove)) {
                        grasses.remove(neighborAbove);
                        newEdges.add(neighborAbove);
                        waterPositions.add(neighborAbove);
                        removeFromFreeFields(neighborAbove);
                    }
                    if (!waterPositions.contains(neighborBelow)) {
                        grasses.remove(neighborBelow);
                        newEdges.add(neighborBelow);
                        waterPositions.add(neighborBelow);
                        removeFromFreeFields(neighborBelow);
                    }
                    if (!waterPositions.contains(neighborLeft)) {
                        grasses.remove(neighborLeft);
                        newEdges.add(neighborLeft);
                        waterPositions.add(neighborLeft);
                        removeFromFreeFields(neighborLeft);
                    }
                    if (!waterPositions.contains(neighborRight)) {
                        grasses.remove(neighborRight);
                        newEdges.add(neighborRight);
                        waterPositions.add(neighborRight);
                        removeFromFreeFields(neighborRight);
                    }
                }
                waterEdges = newEdges;
            }
            case 2, 3 -> {
//                outflows 2, 3
                List<Vector2d> newEdges = new ArrayList<>();
                for (Vector2d edge : waterEdges) {
                    Vector2d neighborAbove = new Vector2d(edge.getX(), edge.getY() - 1);
                    Vector2d neighborBelow = new Vector2d(edge.getX(), edge.getY() + 1);
                    Vector2d neighborLeft = new Vector2d(edge.getX() - 1, edge.getY());
                    Vector2d neighborRight = new Vector2d(edge.getX() + 1, edge.getY());
                    if (waterPositions.contains(neighborAbove) && !waterEdges.contains(neighborAbove)) {
                        newEdges.add(neighborAbove);
                        waterPositions.remove(edge);
                        addToFreeFields(edge);
                    }
                    if (waterPositions.contains(neighborBelow) && !waterEdges.contains(neighborBelow)) {
                        newEdges.add(neighborBelow);
                        waterPositions.remove(edge);
                        addToFreeFields(edge);
                    }
                    if (waterPositions.contains(neighborLeft) && !waterEdges.contains(neighborLeft)) {
                        newEdges.add(neighborLeft);
                        waterPositions.remove(edge);
                        addToFreeFields(edge);
                    }
                    if (waterPositions.contains(neighborRight) && !waterEdges.contains(neighborRight)) {
                        newEdges.add(neighborRight);
                        waterPositions.remove(edge);
                        addToFreeFields(edge);
                    }
                }
                waterEdges = newEdges;
            }
        }
    }

    public List<Vector2d> getWaterPositions() {
        return waterPositions;
    }

    public List<Vector2d> getWaterEdges() {
        return waterEdges;
    }
}
