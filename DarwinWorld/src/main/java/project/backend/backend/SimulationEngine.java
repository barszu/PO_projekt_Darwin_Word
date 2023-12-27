package project.backend.backend;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private final ExecutorService execService;

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
        this.execService = Executors.newFixedThreadPool(4);
    }

    public void runSync() {
        for(Simulation simulation : this.simulations) {
            simulation.start();
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
            execService.awaitTermination(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void runAsyncInThreadPool() {
        for(Simulation simulation : this.simulations) {
            execService.submit(simulation);
        }
        execService.shutdown();
    }
}
