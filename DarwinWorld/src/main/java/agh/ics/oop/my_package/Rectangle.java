package agh.ics.oop.my_package;

import project.backend.model.models.Vector2d;

//my own class for easy check if point (Animal.position) is in the Word map
//and for global settings -> if I decide to change map size
public class Rectangle {
    //designating a field just like selecting a part of the screen by mouse
    private final Vector2d topLeft; //left,up most point
    private final Vector2d bottomRight; //right,down most point
    private final Vector2d bottomLeft;
    private final Vector2d topRight;

    public static final int WORLD_MAP_SIZE = 5; //GLOBAL SETTING, can be changed

    public Rectangle(){
        this.topLeft = new Vector2d(0,0);
        this.bottomRight = new Vector2d(WORLD_MAP_SIZE-1 , WORLD_MAP_SIZE-1);
        this.bottomLeft = this.topLeft.lowerLeft(this.bottomRight);
        this.topRight = this.topLeft.upperRight(this.bottomRight);
    }

    public Rectangle(Vector2d topLeft, Vector2d bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.bottomLeft = this.topLeft.lowerLeft(this.bottomRight);
        this.topRight = this.topLeft.upperRight(this.bottomRight);
    }

    public boolean contains(Vector2d point){
        // if point is Null it will crash on Vector2d
        // variables for easy code
        return point.precedes(this.topRight) && point.follows(this.bottomLeft);
    }
}
