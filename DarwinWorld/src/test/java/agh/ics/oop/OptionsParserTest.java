package agh.ics.oop;

import project.backend.OptionsParser;
import agh.ics.oop.model.MoveDirection;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class OptionsParserTest {
    @Test
    public void testParseValidDirections() {
        String[] args = {"f", "b", "l", "r"};
        List<MoveDirection> expected = List.of(MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.RIGHT);
        List<MoveDirection> result = OptionsParser.parse(args);
        assertIterableEquals(expected, result);
    }

    @Test
    public void testParseInvalidDirections() {
        String[] args = {"f", "b", "x", "r"};
        List<MoveDirection> expected = List.of(MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.parse(args);
        });

        assertTrue(exception.getMessage().contains("'x'" + " is not a legal move specification"));
    }

    @Test
    public void testParseEmptyArray() {
        String[] args = {};
        List<MoveDirection> expected = new ArrayList<>();
        List<MoveDirection> result = OptionsParser.parse(args);
        assertIterableEquals(expected, result);
    }

    @Test
    public void testParseNullArray() {

        String[] args = null;
        try {
            List<MoveDirection> result = OptionsParser.parse(args);
        } catch (NullPointerException e) {
            assertTrue(true);
        }

    }

}