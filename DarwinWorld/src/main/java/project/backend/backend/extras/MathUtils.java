package project.backend.backend.extras;

/**
 * This class provides utility methods for mathematical operations.
 */
public class MathUtils {

    /**
     * This method checks if a number is between two other numbers.
     * It uses the doubleValue method of the Number class to compare the numbers.
     * This allows it to work with any class that extends Number, such as Integer, Double, etc.
     *
     * @param <N> This is a type parameter that extends Number.
     * @param a The lower bound.
     * @param b The number to check.
     * @param c The upper bound.
     * @return true if b is between a and c (inclusive), false otherwise.
     */
    public static <N extends Number> boolean isBetween(N a, N b, N c){ // niejasne które jest między pozosytałymi -> isBetween(number, lowerBpund, upperBound)
        return (a.doubleValue() <= b.doubleValue() && b.doubleValue() <= c.doubleValue());
    }

}
