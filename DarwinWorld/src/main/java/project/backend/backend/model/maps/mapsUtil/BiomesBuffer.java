package project.backend.backend.model.maps.mapsUtil;

import project.backend.backend.exceptions.NoPositionLeftException;
import project.backend.backend.extras.Random;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.model.enums.BiomeField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a buffer for biomes in the map.
 * It maintains a list of free positions in the steppe and jungle biomes.
 * It also provides methods to get free positions and to hand over positions back to the buffer.
 */
public class BiomesBuffer {
    protected final RectangleBoundary rectangleBox;
    protected final List<Vector2d> stepFreePositions = new ArrayList<>();
    protected final List<Vector2d> jungleFreePositions = new ArrayList<>();

    protected final int equator;
    protected final int radius;

    /**
     * The constructor for the BiomesBuffer class.
     * It initializes the rectangle box, equator, and radius.
     * It also populates the lists of free positions in the steppe and jungle biomes.
     * @param rectangleBox The rectangle box that defines the boundaries of the map.
     */
    public BiomesBuffer(RectangleBoundary rectangleBox){
        this.rectangleBox = rectangleBox;

        this.equator = (rectangleBox.height())/2;
        this.radius = (rectangleBox.height())/10;
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

    /**
     * This method returns a free position in the map.
     * It randomly selects a position from the list of free positions in the steppe or jungle biome.
     * @return A Vector2d representing the free position.
     * @throws NoPositionLeftException if there are no free positions left in the map.
     */
    public Vector2d giveFreePosition() throws NoPositionLeftException {
        //random free position, logic inside

        int decidingNumber = Random.randInt(1,5);
        if( (decidingNumber == 1 || jungleFreePositions.isEmpty()) && !stepFreePositions.isEmpty() ){ //step wins or no places in jungle
            //inserting into step
            Collections.shuffle(stepFreePositions);
            return stepFreePositions.remove(stepFreePositions.size() - 1);
        }
        else if ( !jungleFreePositions.isEmpty() ){
            Collections.shuffle(jungleFreePositions);
            return jungleFreePositions.remove(jungleFreePositions.size() - 1);
        }
        throw new NoPositionLeftException(); //no places left in jungle or step
    }

    /**
     * This method returns a specific free position in the map.
     * It checks if the position is in the rectangle box and if it is free.
     * @param position The position to get.
     * @return A Vector2d representing the free position.
     * @throws IllegalArgumentException if the position is not in the rectangle box or if it is not free.
     */
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

    /**
     * This method hands over a position back to the buffer.
     * It checks if the position is in the rectangle box and if it is not already free.
     * @param position The position to hand over.
     * @throws IllegalArgumentException if the position is not in the rectangle box or if it is already free.
     */
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

    /**
     * This method returns a list of all free positions in the map.
     * @return A list of Vector2d representing all free positions.
     */
    public List<Vector2d> getAllFreePositions() {
        List<Vector2d> allFreePositions = new ArrayList<>();
        allFreePositions.addAll(stepFreePositions);
        allFreePositions.addAll(jungleFreePositions);
        return allFreePositions;
    }

    /**
     * This method returns the biome representation of a position.
     * It checks if the position is in the rectangle box and if it is free.
     * @param position The position to get the biome representation for.
     * @return A BiomeField representing the biome of the position, or null if the position is not in the rectangle box or if it is not free.
     */
    public BiomeField getBiomeRepresentation(Vector2d position) {
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

    /**
     * This method returns the number of free positions in the steppe biome.
     * @return An int representing the number of free positions in the steppe biome.
     */
    public int getFreeSteppePositionsNo(){
        return stepFreePositions.size();
    }

    /**
     * This method returns the number of free positions in the jungle biome.
     * @return An int representing the number of free positions in the jungle biome.
     */
    public int getFreeJunglePositionsNo(){
        return jungleFreePositions.size();
    }

}
