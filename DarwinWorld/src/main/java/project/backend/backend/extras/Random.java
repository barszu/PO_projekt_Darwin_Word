package project.backend.backend.extras;

import project.backend.backend.model.enums.MapDirection;
import project.backend.backend.model.maps.mapsUtil.RectangleBoundary;

/**
 * This class provides utility methods for generating random values.
 */
public class Random {

    /**
     * This method generates a random integer between a minimum and maximum value.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return A random integer between min and max (inclusive) with linear distribution of a random variable.
     */
    public static int randInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    /**
     * This method generates a random position within a rectangle defined by two points.
     * @param lowerLeft The lower left point of the rectangle.
     * @param upperRight The upper right point of the rectangle.
     * @return A random position within the rectangle with linear distribution of a random variable.
     */
    public static Vector2d randPosition( Vector2d lowerLeft, Vector2d upperRight) {
        int x = randInt(lowerLeft.getX(), upperRight.getX());
        int y = randInt(lowerLeft.getY(), upperRight.getY());
        return new Vector2d(x, y);
    }

    /**
     * This method generates a random position within a rectangle defined by a RectangleBoundary object.
     * @param bound The RectangleBoundary object defining the rectangle.
     * @return A random position within the rectangle with linear distribution of a random variable.
     */
    public static Vector2d randPosition(RectangleBoundary bound) {
        return randPosition(bound.lowerLeft(), bound.upperRight());
    }

    /**
     * This method generates a random MapDirection.
     * @return A random MapDirection with linear distribution of a random variable.
     */
    public static MapDirection randDirection() {
        return MapDirection.getById(randInt(0, 7));
    }

    /**
     * This method generates an array of random integers.
     * @param min The minimum value for the integers.
     * @param max The maximum value for the integers.
     * @param n The length of the array.
     * @return An array of n random integers between min and max (inclusive) with linear distribution of a random variable.
     */
    public static int[] randIntArray(int min , int max , int n){
        if (n<0){throw new IllegalArgumentException("n (array len) must be >= 0");}
        int[] res = new int[n];
        for (int i = 0 ; i < n ; i++){
            res[i] = randInt(min,max);
        }
        return res;
    }

    /**
     * This method randomly picks one of two objects.
     * @param <T> The type of the objects.
     * @param a The first object.
     * @param b The second object.
     * @return Either a or b, chosen randomly with linear distribution of a random variable.
     */
    public static <T> T pickOneOf(T a , T b){
        T res = a;
        switch (Random.randInt(0,1)){
            case 1 -> res = a;
            case 0 -> res = b;
        }
        return res;
    }

}