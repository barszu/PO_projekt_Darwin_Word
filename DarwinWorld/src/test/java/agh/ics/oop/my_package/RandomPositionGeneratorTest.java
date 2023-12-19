package agh.ics.oop.my_package;

import project.backend.model.models.Vector2d;
import org.junit.jupiter.api.Test;

class RandomPositionGeneratorTest {//pseudotesty

    @Test
    void test1 (){
        int MAP_WIDTH = 17;
        int MAP_HEIGHT = 1;
        int TO_GENERATE = 10;
        Vector2d bottomRightExclusive = new Vector2d(MAP_WIDTH,MAP_HEIGHT);
        RandomPositionGenerator generator = new RandomPositionGenerator(bottomRightExclusive,TO_GENERATE);
        for (Vector2d position : generator) {
            System.out.println("Wylosowana pozycja: " + position);
        }
        generator = null;
        System.out.println("koniec");
    }

    @Test
    void test2 (){ //pseudotest
        int MAP_WIDTH = 16;
        int MAP_HEIGHT = 20;
        int TO_GENERATE = 10;
        Vector2d bottomRightExclusive = new Vector2d(MAP_WIDTH,MAP_HEIGHT);
        RandomPositionGenerator generator = new RandomPositionGenerator(bottomRightExclusive,TO_GENERATE);
        for (Vector2d position : generator) {
            System.out.println("Wylosowana pozycja: " + position);
        }
        generator = null;
        System.out.println("koniec");
    }

    @Test
    void test3 (){ //pseudotest
        //dziwny bug??? Caused by: java.io.IOException: Connection reset by peer
        //fixed ale co to jest???
        int MAP_WIDTH = 16;
        int MAP_HEIGHT = 100;
        int TO_GENERATE = 10;
        Vector2d bottomRightExclusive = new Vector2d(MAP_WIDTH,MAP_HEIGHT);
        RandomPositionGenerator generator = new RandomPositionGenerator(bottomRightExclusive,TO_GENERATE);
        for (Vector2d position : generator) {
            System.out.println("Wylosowana pozycja: " + position);
        }
        generator = null;
        System.out.println("koniec");
    }

}