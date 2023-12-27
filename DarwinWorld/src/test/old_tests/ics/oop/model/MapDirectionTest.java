package agh.ics.oop.model;

import project.backend.model.enums.MapDirection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {
    @Test
    void next() {
        assertEquals(MapDirection.EAST, MapDirection.NORTH_EAST.next());
        assertEquals(MapDirection.SOUTH, MapDirection.SOUTH_EAST.next());
        assertEquals(MapDirection.WEST, MapDirection.SOUTH_WEST.next());
        assertEquals(MapDirection.NORTH, MapDirection.NORTH_WEST.next());
    }

    @Test
    void previous() {
        assertEquals(MapDirection.WEST, MapDirection.NORTH.previous().previous());
        assertEquals(MapDirection.NORTH, MapDirection.EAST.previous().previous());
        assertEquals(MapDirection.EAST, MapDirection.SOUTH.previous().previous());
        assertEquals(MapDirection.SOUTH, MapDirection.WEST.previous().previous());
    }
}