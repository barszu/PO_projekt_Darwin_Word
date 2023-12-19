package agh.ics.oop.model;

import project.backend.model.models.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

//    Vector2d v = new Vector2d(1,1);
//    Vector2d u = new Vector2d(-1, 2);
//    Vector2d w = new Vector2d(2, 1000000000);
//    Vector2d a = new Vector2d(2,2);
//
//    Vector2d b = new Vector2d(-1,1);
//
//    Vector2d c = new Vector2d(1 ,1); //same as v , but not same obj!

    private Vector2d v1 = new Vector2d(2, 3);
    private Vector2d v2 = new Vector2d(4, 5);
    private Vector2d v3 = new Vector2d(6, 8);

    @Test
    public void testEquals() {
        Vector2d v4 = new Vector2d(2, 3);

        assertTrue(v1.equals(v4));
        assertFalse(v1.equals(v2));
    }

    @Test
    public void testToString() {
        assertEquals("(2,3)", v1.toString());
    }

    @Test
    public void testPrecedes() {
        assertTrue(v1.precedes(v2));
        assertFalse(v2.precedes(v1));
    }

    @Test
    public void testFollows() {
        assertTrue(v2.follows(v1));
        assertFalse(v1.follows(v2));
    }

    @Test
    public void testUpperRight() {
        Vector2d result = v1.upperRight(v2);
        assertEquals(v2, result);
    }

    @Test
    public void testLowerLeft() {
        Vector2d result = v2.lowerLeft(v1);
        assertEquals(v1, result);
    }

    @Test
    public void testAdd() {
        Vector2d result = v1.add(v2);
        assertEquals(v3, result);
    }

    @Test
    public void testSubtract() {
        Vector2d result = v3.subtract(v1);
        assertEquals(v2, result);
    }

    @Test
    public void testOpposite() {
        Vector2d result = v1.opposite();
        assertEquals(new Vector2d(-2, -3), result);
    }
}