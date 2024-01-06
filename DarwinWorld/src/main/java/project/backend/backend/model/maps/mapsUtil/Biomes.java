package project.backend.backend.model.maps.mapsUtil;

import project.backend.backend.exceptions.NoPositionLeftException;
import project.backend.backend.extras.Random;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.model.enums.BiomeField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Biomes {
    protected final RectangleBoundary rectangleBox;
    protected final List<Vector2d> stepFreePositions = new ArrayList<>();
    protected final List<Vector2d> jungleFreePositions = new ArrayList<>();

    protected final int equator;
    protected final int radius;

    public Biomes(RectangleBoundary rectangleBox){
        this.rectangleBox = rectangleBox;

        //TODO: of by one errors might be here!
        this.equator = (rectangleBox.height()+1)/2;
        this.radius = (rectangleBox.height()+1)/10;
        //all soft boundaries
        for(int x=0; x<rectangleBox.width();x++){
            for(int y=0; y<rectangleBox.height();y++){
                Vector2d position = new Vector2d(x,y);
                if (position.hasYbetween(equator-radius,equator+radius)){
                    jungleFreePositions.add(position);
                }
                else{
                    stepFreePositions.add(position);
                }
            }
        }
    }

    public Vector2d giveFreePosition() throws NoPositionLeftException {
        //random free position, logic inside
        Collections.shuffle(stepFreePositions);
        Collections.shuffle(jungleFreePositions);

        int decidingNumber = Random.randInt(1,5);
        if( (decidingNumber == 1 || jungleFreePositions.isEmpty()) && !stepFreePositions.isEmpty() ){ //step wins or no places in jungle
            //inserting into step
            return stepFreePositions.remove(0);
        }
        else if ( !jungleFreePositions.isEmpty() ){
            return jungleFreePositions.remove(0);
        }
        throw new NoPositionLeftException(); //no places left in jungle or step
    }

    public Vector2d giveExactFreePosition(Vector2d position){
        //gives exacly position, logic inside
        if (!rectangleBox.contains(position)){
            throw new IllegalArgumentException("There is no such position {"+position+"} in rectangle "+rectangleBox);}

        Vector2d newPosition = null;

        if(position.getY() >= equator-radius && position.getY() <= equator+radius){

            if (!jungleFreePositions.contains(position)){
                throw new IllegalArgumentException("There is no such position {"+position+"} in free buffer in jungle");
            }
            newPosition = jungleFreePositions.get(jungleFreePositions.indexOf(position));
            jungleFreePositions.remove(position);
        }
        else{
            if (!stepFreePositions.contains(position)){
                throw new IllegalArgumentException("There is no such position {"+position+"} in free buffer in steppe");
            }
            newPosition = stepFreePositions.get(stepFreePositions.indexOf(position));
            stepFreePositions.remove(position);
        }
        return newPosition;
    }

    public void handOverPosition(Vector2d position){
        //give back position so be it free
        if (!rectangleBox.contains(position)){
            throw new IllegalArgumentException("There is no such position {"+position+"} in rectangle "+rectangleBox);}

        if(position.getY() >= equator-radius && position.getY() <= equator+radius){
            if(jungleFreePositions.contains(position)){
                throw new IllegalArgumentException("Position {"+position+"} is already in free buffer in jungle");
            }
            jungleFreePositions.add(position);
        }
        else{
            if(stepFreePositions.contains(position)){
                throw new IllegalArgumentException("Position {"+position+"} is already in free buffer in steppe");
            }
            stepFreePositions.add(position);
        }
    }

    public List<Vector2d> getAllFreePositions() {
        List<Vector2d> allFreePositions = new ArrayList<>();
        allFreePositions.addAll(stepFreePositions);
        allFreePositions.addAll(jungleFreePositions);
        return allFreePositions;
    }

    public BiomeField getBiomeRepresentation(Vector2d position) {
//        if (position.hasYbetween(equator-radius,equator+radius)){
//            return BiomeField.JUNGLE;
//        }
//        else{
//            return BiomeField.STEP;
//        }
        if (!rectangleBox.contains(position)){
            return null;
        }
        if (jungleFreePositions.contains(position)){
            return BiomeField.JUNGLE;
        }
        else if (stepFreePositions.contains(position)){
            return BiomeField.STEP;
        }
        else{
            return null;
        }
    }

}
