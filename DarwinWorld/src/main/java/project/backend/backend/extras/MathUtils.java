package project.backend.backend.extras;

public class MathUtils {

    public static <N extends Number> boolean isBetween(N a, N b, N c){
        return (a.doubleValue() <= b.doubleValue() && b.doubleValue() <= c.doubleValue());
    }

}
