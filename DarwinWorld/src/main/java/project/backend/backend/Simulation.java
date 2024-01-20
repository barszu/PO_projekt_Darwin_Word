package project.backend.backend;

import project.backend.backend.extras.Vector2d;
import project.backend.backend.globalViaSimulation.GlobalOptions;
import project.backend.backend.globalViaSimulation.GlobalVariables;
import project.backend.backend.listeners.IMapChangeListener;
import project.backend.backend.model.maps.CylindricalGlobeMap;
import project.backend.backend.model.maps.WaterMap;
import project.backend.backend.model.maps.IWorldMap;
import project.frontend.listeners.SimulationStatusListener;

import java.util.*;

/**
 * This class represents a simulation of the world.
 * It extends the Thread class, allowing it to run in a separate thread.
 * It contains a timer for updating the world, a world map, global variables and options, and a list of listeners for simulation status changes.
 * It also provides methods to start, stop, and run the simulation, and to add listeners to the simulation and the map.
 */
public class Simulation extends Thread{
    private Timer timer;
    private final IWorldMap worldMap;
    private final GlobalVariables globalVariables;
    private final GlobalOptions globalOptions;

    private boolean isRunning = false;
    private final int simulationId;
    private final List<SimulationStatusListener> listeners = new LinkedList<>();

    //getters
    public IWorldMap getWorldMap() {
        return worldMap;
    }
    public boolean isRunning() {return isRunning;}
    public int getSimulationId() {
        return simulationId;
    }
    public int getDay() {
        return globalVariables.getDate();
    }

    /**
     * Constructor for the Simulation class.
     * It initializes the world map based on the map type specified in globalOptions.
     * @param globalOptions The global options for the simulation.
     * @param simulationId The ID of the simulation.
     */
    public Simulation(GlobalOptions globalOptions , int simulationId){
        this.simulationId = simulationId;
        this.globalOptions = globalOptions;
        this.globalVariables = new GlobalVariables();
        switch (globalOptions.mapType()){
            case NORMAL_MAP -> this.worldMap = new CylindricalGlobeMap(globalOptions,globalVariables);
            case WATER_MAP -> this.worldMap = new WaterMap(globalOptions,globalVariables);
            default -> throw new IllegalArgumentException("Unknown map type: " + globalOptions.mapType().toString());
        }
//        this.worldMap.addObserver(new MapConsoleLogger());
//        this.worldMap.addObserver(new ConsoleMapDisplay());
    }

    /**
     * This method adds a listener to the map.
     * @param l The listener to add.
     */
    public void addListenerToMap(IMapChangeListener l){
        worldMap.addObserver(l);
    }

    /**
     * This method is called when the simulation thread is started.
     * It starts the simulation.
     */
    @Override
    public void run(){
        System.out.println("Simulation " + simulationId + " has started (begun)");
        startSimulation();
    }

    /**
     * This method starts or resumes the simulation.
     * It sets up the timer and notifies the listeners.
     */
    public void startSimulation(){
        if (!isRunning) {
            System.out.println("Simulation " + simulationId + " started (resumed)");
            isRunning = true;

            setUpTimer();
            notifyListeners();
        }
    }

    /**
     * This method stops the simulation.
     * It cancels and purges the timer and notifies the listeners.
     * It only performs these actions if the simulation is currently running.
     */
    public void stopSimulation() {
        if (isRunning) {
            System.out.println("Simulation " + simulationId + " stopped");
            isRunning = false;

            // Zatrzymywanie timera
            timer.cancel();
            timer.purge();

            notifyListeners();

//            int topGene = worldMap.getTopGene();
//            Set<Vector2d> animalsToColor = worldMap.getAnimalsPositionsHavingGene(topGene);
//            for (Vector2d animalPosition : animalsToColor) {
//
//            }
        }
    }

    /**
     * This method sets up the timer for updating the world.
     * It creates a new Timer and schedules a TimerTask to run every second.
     * The TimerTask updates the world and notifies all observers.
     */
    private void setUpTimer(){
        // Tworzenie i uruchamianie timera
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                worldMap.updateEverything();
                worldMap.notifyAllObservers("Day: " + globalVariables.getDate() + " has passed.");
                globalVariables.addDay();
            }
        }, 0, 1000); // Aktualizacja co sekundę
    }


    /**
     * This method adds a listener to the simulation.
     * The listener will be notified when the simulation starts or stops.
     * @param l The listener to add.
     */
    public void addListener(SimulationStatusListener l){
        listeners.add(l);
    }

    /**
     * This method notifies all listeners of the simulation status.
     * It calls the notify method of each listener with the current running status of the simulation.
     */
    public void notifyListeners(){
        for (SimulationStatusListener l : listeners){
            l.notify(isRunning);
        }
    }
}
