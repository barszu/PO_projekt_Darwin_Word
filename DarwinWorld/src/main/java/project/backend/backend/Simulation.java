package project.backend.backend;

import project.backend.backend.global.GlobalOptions;
import project.backend.backend.global.GlobalVariables;
import project.backend.backend.listeners.ConsoleMapDisplay;
import project.backend.backend.listeners.MapChangeListener;
import project.backend.backend.listeners.MapConsoleLogger;
import project.backend.backend.model.maps.CylindricalGlobeMap;
import project.backend.backend.model.maps.WaterMap;
import project.backend.backend.model.maps.WorldMap_able;
import project.frontend.listeners.SimulationStatusListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Simulation extends Thread{
    private Timer timer;

    public WorldMap_able getWorldMap() {
        return worldMap;
    }

    private final WorldMap_able worldMap;
    private final GlobalVariables globalVariables;
    private final GlobalOptions globalOptions;

    public boolean isRunning() {return isRunning;}

    private boolean isRunning = false;
    private final int simulationId;

    private final List<SimulationStatusListener> listeners = new LinkedList<>();


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


    public void addListenerToMap(MapChangeListener l){
        worldMap.addObserver(l);
    }

    @Override
    public void run(){
        System.out.println("Simulation " + simulationId + " has started (begun)");
        startSimulation();
    }


    public void startSimulation(){ //or resume
        if (!isRunning) {
            System.out.println("Simulation " + simulationId + " started (resumed)");
            isRunning = true;

            setUpTimer();
            notifyListeners();
        }
    }


    public void stopSimulation() {
        if (isRunning) {
            System.out.println("Simulation " + simulationId + " stopped");
            isRunning = false;

            // Zatrzymywanie timera
            timer.cancel();
            timer.purge();

            notifyListeners();
        }
    }


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

    public int getSimulationId() {
        return simulationId;
    }

    public int getDay() {
        return globalVariables.getDate();
    }

    public void addListener(SimulationStatusListener l){
        listeners.add(l);
    }

    public void notifyListeners(){
        for (SimulationStatusListener l : listeners){
            l.notify(isRunning);
        }
    }
}
