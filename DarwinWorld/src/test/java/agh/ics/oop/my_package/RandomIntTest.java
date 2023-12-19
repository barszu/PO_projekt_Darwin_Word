package agh.ics.oop.my_package;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomIntTest {

    @Test
    void randInt() {
        RandomInt rr = new RandomInt();
        System.out.println(rr.randInt(0,10));
        System.out.println(rr.randInt(0,0));
        System.out.println(rr.randInt(0,1));
        System.out.println(rr.randInt(0,1));
        System.out.println(rr.randInt(0,1));
    }
}