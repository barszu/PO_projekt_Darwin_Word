package agh.ics.oop.model;

import project.backend.model.maps.mapsUtil.RectangleBoundary;
import project.backend.model.models.Vector2d;
import agh.ics.oop.my_package.Rectangle;

import java.util.UUID;

public class RectangularMap extends AbstractWorldMap {
    private final Rectangle rectangleBox;
    private final int width;
    private final int height;


    public RectangularMap(int width, int height) {
        super(UUID.randomUUID());
        if (width<0 || height<0) {
            throw new IllegalArgumentException("invalid width or height in RectangularMap");
        }
        this.width = width;
        this.height = height;
        this.rectangleBox = new Rectangle(new Vector2d(0,0),new Vector2d(width,height)); //archaik
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (this.rectangleBox.contains(position)) && (super.canMoveTo(position));
    }

    @Override
    public RectangleBoundary getCurrentBounds(){
        return new RectangleBoundary(new Vector2d(0,0),new Vector2d(width,height));
    }


}
