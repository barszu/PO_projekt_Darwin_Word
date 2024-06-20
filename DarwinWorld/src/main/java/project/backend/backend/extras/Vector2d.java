package project.backend.backend.extras; // jak interpretować nazwę pakietu?

import java.util.Objects;

//non mutable
public class Vector2d {
    private final int x ;
    private final int y ;

    //constructor
    public Vector2d(int x , int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d() {
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

   public String toString(){
        return String.format("(%d,%d)",this.x,this.y);
   }

   public boolean precedes(Vector2d other){
       return ((this.x <= other.x)&&(this.y <= other.y));
   }

   public boolean follows(Vector2d other){
       return ((this.x >= other.x)&&(this.y >= other.y));
   }

    public boolean isUpLeftRespectTo(Vector2d other){
        return ((other.x >= this.x)&&(other.y <= this.y ));
    }

    public boolean isDownRightRespectTo(Vector2d other){
        return (( other.x <= this.x )&&( other.y >= this.y ));
    }

    public boolean hasGreaterX(Vector2d other){
        return this.x >= other.x;
    }

    public boolean hasGreaterY(Vector2d other){
        return this.y >= other.y;
    }

    public boolean hasXbetween(int x1 , int x2){
        return (x1 <= this.x) && (this.x <= x2);
    }

    public boolean hasYbetween(int y1 , int y2){
        return (y1 <= this.y) && (this.y <= y2);
    }

   public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x , this.y + other.y);
   }

   public Vector2d subtract(Vector2d other){
       return new Vector2d(this.x - other.x , this.y - other.y);
   }

   public Vector2d upperRight(Vector2d other){
       return new Vector2d( Math.max(this.x , other.x) , Math.max(this.y , other.y));
   }

   public Vector2d lowerLeft(Vector2d other){
       return new Vector2d( Math.min(this.x , other.x) , Math.min(this.y , other.y));
   }

   public Vector2d opposite(){
        return new Vector2d(-this.x , -this.y);
   }

    public boolean equals(Object other){
       if (this == other)
           return true;
       if (!(other instanceof Vector2d))
           return false;
       Vector2d that = (Vector2d) other;
       return this.x == that.x && this.y == that.y;
   }

    public int hashCode(){
        return Objects.hash(this.x, this.y);
    }





}


