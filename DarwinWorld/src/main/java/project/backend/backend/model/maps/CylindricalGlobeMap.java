package project.backend.backend.model.maps;

import project.backend.backend.extras.Random;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.globalViaSimulation.GlobalOptions;
import project.backend.backend.globalViaSimulation.GlobalVariables;
import project.backend.backend.model.sprites.Animal;

public class CylindricalGlobeMap extends AbstractWorldMap{

    public CylindricalGlobeMap(GlobalOptions globalOptions, GlobalVariables globalVariables) {
        super(globalOptions, globalVariables);
        initAllAnimals();
        placeGrasses(globalOptions.initGrassNo());
    }

    @Override
    public Vector2d validatePosition(Vector2d newPosition , Vector2d oldPosition) {
        if ( super.validatePosition(newPosition , oldPosition).equals(newPosition) ){ //within the rectangle
            return newPosition;
        }
        if (! newPosition.hasYbetween(0,super.globalOptions.mapHeight()-1)){// y not in rectangle
            return oldPosition;
        }
        int x = newPosition.getX();
        int y = newPosition.getY();
        if (x == super.globalOptions.mapWidth()){
            return new Vector2d(0,y);
        }
        else if (x == -1){
            return new Vector2d(super.globalOptions.mapWidth()-1,y);
        }
        throw new IllegalArgumentException("ERROR! Something went wrong: " +
                "CylindricalGlobe.canMoveTo new position: "
                + newPosition.toString() +
                " old position: "
                + oldPosition.toString() +
                "map: ["+globalOptions.mapWidth()+globalOptions.mapHeight()+"]");
//        return oldPosition; //if something went wrong
    }

    @Override
    public String toString(){
        return super.toString();
    }

    @Override
    public void initAllAnimals() {
        for (int i = 0; i < globalOptions.initAnimalsNo(); i++) {
            //animals can stack on the same position, position within rectangle
            Animal animal = new Animal(Random.randPosition(rectangleBox) , globalOptions , globalVariables);
            animalsDict.putInside(animal.getPosition() , animal);
        }
    }

}
