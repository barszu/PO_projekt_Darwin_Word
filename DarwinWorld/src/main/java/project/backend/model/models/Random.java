package project.backend.model.models;

import project.backend.model.maps.mapsUtil.RectangleBoundary;
import project.backend.model.enums.MapDirection;

public class Random {

    public static int randInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static Vector2d randPosition( Vector2d lowerLeft, Vector2d upperRight) {
        int x = randInt(lowerLeft.getX(), upperRight.getX());
        int y = randInt(lowerLeft.getY(), upperRight.getY());
        return new Vector2d(x, y);
    }

    public static Vector2d randPosition(RectangleBoundary bound) {
        return randPosition(bound.lowerLeft(), bound.upperRight());
    }

    public static MapDirection randDirection() {
        return MapDirection.getById(randInt(0, 7));
    }

    public static int[] randIntArray(int min , int max , int n){
        if (n<0){throw new IllegalArgumentException("n (array len) must be >= 0");}
        int[] res = new int[n];
        for (int i = 0 ; i < n ; i++){
            res[i] = randInt(min,max);
        }
        return res;
    }

    public static <T> T pickOneOf(T a , T b){
        T res = a;
        switch (Random.randInt(0,1)){
            case 1 -> res = a;
            case 0 -> res = b;
        }
        return res;
    }

}
