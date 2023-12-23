package agh.ics.oop.model;

import project.backend.OptionsParser;
import agh.ics.oop.Simulation;
import project.backend.model.sprites.Animal;
import project.backend.model.enums.MapDirection;
import project.backend.model.models.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTestGrassField {

    @Test
    void testRun1() { //is visualization good?
        System.out.println("\ntestRun1:");
        String[] args = {"f"};
        List<MoveDirection> movesList = OptionsParser.parse(args);
        List<Vector2d> positionsList = List.of(new Vector2d(2, 2),new Vector2d(10, 0) , new Vector2d(5,5));
        WorldMap_able worldMapable = new GrassField(10);

        Simulation simulation = new Simulation(movesList, positionsList, worldMapable);

        simulation.run(); //working as void on animals list

        assertTrue(worldMapable.toString().contains("*"));

        Animal animal1 = simulation.getAnimalsList().get(0);
        Animal animal2 = simulation.getAnimalsList().get(1);
        Animal animal3 = simulation.getAnimalsList().get(2);

        assertEquals(animal1.getDirection(), MapDirection.NORTH);
        assertEquals(new Vector2d(2, 3) , animal1.getPosition() );

        assertEquals(animal2.getDirection(), MapDirection.NORTH);
        assertEquals(new Vector2d(10, 0) , animal2.getPosition() );

        assertEquals(animal3.getDirection(), MapDirection.NORTH);
        assertEquals(new Vector2d(5, 5) , animal3.getPosition());

    }

    @Test
    void testRun2() { //stacking animals when spawn?
        System.out.println("\ntestRun2:");
        String[] args = {"f","f"};
        List<MoveDirection> movesList = OptionsParser.parse(args);
        List<Vector2d> positionsList = List.of(new Vector2d(2, 2),new Vector2d(2, 2));
        WorldMap_able worldMapable = new GrassField(12);

        Simulation simulation = new Simulation(movesList, positionsList, worldMapable);

        simulation.run(); //working as void on animals list

        assertTrue(simulation.getAnimalsList().size() == 1);

        Animal animal1 = simulation.getAnimalsList().get(0);


        assertEquals(animal1.getDirection(), MapDirection.NORTH);
        assertEquals(animal1.getPosition(), new Vector2d(2, 4) );


    }

    @Test
    void testRun3() { //no border of map check
        System.out.println("\ntestRun3:");
        String[] args = {"f","f","f","f","f","f","f","f","f","f","f","f","f","f","f","f","f"};
        List<MoveDirection> movesList = OptionsParser.parse(args);
        List<Vector2d> positionsList = List.of(new Vector2d(2, 2) , new Vector2d(20,20));
        WorldMap_able worldMapable = new GrassField(20);

        Simulation simulation = new Simulation(movesList, positionsList, worldMapable);

        simulation.run(); //working as void on animals list

        Animal animal1 = simulation.getAnimalsList().get(0);
        Animal animal2 = simulation.getAnimalsList().get(1);

        assertEquals(animal1.getDirection(), MapDirection.NORTH);
        assertEquals(new Vector2d(2, 11) , animal1.getPosition() );

        assertEquals(animal2.getDirection(), MapDirection.NORTH);
        assertEquals(new Vector2d(20, 28) , animal2.getPosition() );
    }

    @Test
    void testRun4() { //no animals -> __repr__ = something 2x2 surface
        System.out.println("\ntestRun4:");
        String[] args = {"f","f","f","f","f","f","f","f","f","f","f","f","f","f","f","f"};
        List<MoveDirection> movesList = OptionsParser.parse(args);
        List<Vector2d> positionsList = List.of();
        WorldMap_able worldMapable = new GrassField(20);

        Simulation simulation = new Simulation(movesList, positionsList, worldMapable);

        simulation.run(); //working as void on animals list

        assertEquals(simulation.getAnimalsList().size() , 0);
        assertNotEquals(worldMapable.toString() , "");
    }

    @Test
    void testRun5() { //is 20 grasses and 2 animals?
        System.out.println("\ntestRun5:");
        String[] args = {"f","f","f","f","f","f","f","f","f","f","f","f","f","f","f","f","f"};
        List<MoveDirection> movesList = OptionsParser.parse(args);
        List<Vector2d> positionsList = List.of(new Vector2d(2, 2) , new Vector2d(20,20));
        WorldMap_able worldMapable = new GrassField(20);

        Simulation simulation = new Simulation(movesList, positionsList, worldMapable);

        simulation.run(); //working as void on animals list

        assertTrue(worldMapable.getElements().size() == 22);
    }

}