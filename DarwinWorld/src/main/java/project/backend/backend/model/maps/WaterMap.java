package project.backend.backend.model.maps;

import project.backend.backend.extras.Vector2d;
import project.backend.backend.global.GlobalOptions;
import project.backend.backend.global.GlobalVariables;

import java.util.HashSet;

public class WaterMap extends AbstractWorldMap{
    //TODO: problem -> how to move a water ?

    private final HashSet<Vector2d> waterPositions = new HashSet<>();

    public WaterMap(GlobalOptions globalOptions, GlobalVariables globalVariables) {
        super(globalOptions, globalVariables);
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

    //TODO: implement this method!
    private void initWaterPositions(int waterNo){
        //TODO: init water positions!
    }



}
