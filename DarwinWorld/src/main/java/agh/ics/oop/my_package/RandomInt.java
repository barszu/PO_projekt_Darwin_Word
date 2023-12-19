package agh.ics.oop.my_package;

public class RandomInt extends java.util.Random{
    public int randInt(int a, int b) {
        if (a > b) {
            throw new IllegalArgumentException("The lower bound (a) cannot be greater than the upper (b). in RandomInt");
        }
        return nextInt(b - a + 1) + a;
    }
}
