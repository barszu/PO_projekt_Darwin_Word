package project.backend.model.maps;

import project.backend.model.sprites.Grass;
import project.backend.model.models.Vector2d;
import project.backend.model.sprites.WorldElementable;
import agh.ics.oop.my_package.RandomPositionGenerator;

import java.util.*;

public class GrassField extends AbstractWorldMap {

    private final Map<Vector2d, Grass> grasses = new HashMap<>();


    public GrassField(int grassNo) { //puts grass
        super(UUID.randomUUID());
        if (grassNo < 0){
            throw new IllegalArgumentException("negative grassNo in GrassField");
        }

        int xyLastCord = (int) Math.round(Math.sqrt(10*grassNo));
        Vector2d topLeft = new Vector2d(0,0);
        Vector2d bottomRight = new Vector2d(xyLastCord , xyLastCord);

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(bottomRight,grassNo);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }

    }

    @Override
    public WorldElementable objectAt(Vector2d position) { //as animal or grass
        WorldElementable animal = super.objectAt(position);
        if (animal != null){
            return animal;
        }
        if (this.grasses.containsKey(position)){ //otherwise maybe grass
            return this.grasses.get(position);
        }
        return null; //nothing
    }

    @Override
    public boolean isOccupied(Vector2d position) {//as animal or grass
        return (super.animals.containsKey(position)) || (this.grasses.containsKey(position)) ;
    }

//    @Override
    public RectangleBoundary getCurrentBounds(){ //only for GrassField
        ArrayList<WorldElementable> worldElementsList = new ArrayList<>(getElements());

        if (worldElementsList.isEmpty()){
            return null;
        }

        Vector2d mostLowerLeftPoint = worldElementsList.get(0).getPosition(); // "min"
        Vector2d mostUpperRightPoint = worldElementsList.get(0).getPosition(); // "max"

        for (WorldElementable worldElementable : worldElementsList) {
            Vector2d currentPos = worldElementable.getPosition();
            mostUpperRightPoint = currentPos.upperRight(mostUpperRightPoint) ; // for "max"
            mostLowerLeftPoint = currentPos.lowerLeft(mostLowerLeftPoint) ; // for "min"
        }
        return new RectangleBoundary(mostLowerLeftPoint , mostUpperRightPoint);
    }

    @Override
    public Collection<WorldElementable> getElements() {
        Collection<WorldElementable> elements = super.getElements();
        elements.addAll(grasses.values());
        return elements;
    }

}
