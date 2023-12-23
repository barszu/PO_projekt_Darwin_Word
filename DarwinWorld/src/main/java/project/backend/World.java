package project.backend;

import agh.ics.oop.Simulation;
import project.backend.model.maps.GrassField;
import project.backend.model.maps.RectangularMap;
import project.backend.model.observers.ConsoleMapDisplay;
import project.backend.model.models.Vector2d;
import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class World {

    public static void main(String[] main_args) {
        System.out.println("system wystartowal");

        OptionsParser optionsParser = new OptionsParser();
        List<MoveDirection> directions = optionsParser.parse(main_args);

        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));

//        RectangularMap worldMap = new RectangularMap(5,5);
//        Simulation simulation = new Simulation(directions, positions, worldMap);
//        simulation.run();

        ArrayList<Simulation> simulations = new ArrayList<Simulation>();
        ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
        //GrassField Generator
        for(int i = 0; i < 300; i++) {
            GrassField mapGrassField = new GrassField(10);
            mapGrassField.addObserver(consoleMapDisplay);
            simulations.add(new Simulation(directions, positions, mapGrassField));
        }
        //RectangularMap Generator
        for(int i = 0; i < 1; i++) {
            RectangularMap mapRectangularMap = new RectangularMap(10, 10);
            mapRectangularMap.addObserver(consoleMapDisplay);
            simulations.add(new Simulation(directions, positions, mapRectangularMap));
        }

//        SimulationEngine
        SimulationEngine engine = new SimulationEngine(simulations);
//        engine.runASync();
        engine.runAsyncInThreadPool();
        try {
            engine.awaitSimulationsEnd();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        System.out.println("system zakonczyl dzialanie");

    }

    public static void run(List<MoveDirection> directions){
        System.out.println("Start");

        for (MoveDirection direction : directions) {
            switch (direction) {
                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case LEFT -> System.out.println("Zwierzak skręca w lewo,");
            }
        }
        System.out.println("Stop");
    }
}
