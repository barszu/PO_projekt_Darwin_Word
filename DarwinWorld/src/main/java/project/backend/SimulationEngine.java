package project.backend;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final ArrayList<Simulation> simulations;
    private final ExecutorService executorService;

    public SimulationEngine(ArrayList<Simulation> simulations) {
        this.simulations = simulations;
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public void runSync() {
        for(Simulation simulation : this.simulations) {
            simulation.run();
        }
    }
    public void runASync() {
        for(Simulation simulation : this.simulations) {
            simulation.start();
        }
    }

    public void awaitSimulationsEnd() throws InterruptedException{
        for(Simulation simulation1 : this.simulations) {
            simulation1.join();
        }
        try {
            executorService.awaitTermination(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void runAsyncInThreadPool() {
        for(Simulation simulation : this.simulations) {
            executorService.submit(simulation);
        }
        executorService.shutdown();
    }
}
