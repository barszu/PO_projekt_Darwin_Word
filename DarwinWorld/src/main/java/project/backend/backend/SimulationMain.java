package project.backend.backend;

import project.backend.backend.global.GlobalOptions;
import project.backend.backend.global.GlobalVariables;
import project.backend.backend.model.enums.MapType;
import project.backend.backend.model.maps.mapsUtil.RectangleBoundary;
import project.backend.backend.model.sprites.Animal;
import project.backend.backend.extras.Random;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.model.enums.MutationType;

public class SimulationMain {

    public static GlobalOptions G_OPTIONS;
    public static void main(String[] args) {
        System.out.println("Hello World!");

        //args for GlobalOptions

        //map options
        int mapWidth = 50;
        int mapHeight = 50;

        //environment options
//        MapType mapType = MapType.NORMAL_MAP;
        MapType mapType = MapType.WATER_MAP;
        MutationType mutationType = MutationType.SLIGHT_CORRECTION;

        //initial options
        int initAnimalEnergy = 10;
        int initPlantEnergy = 5;
        int initAnimalsNo = 10;

        //general simulation options
        int genotypeLength = 10;
        int energyPerPlant = 10;
        int plantsPerDay = 2;
        int energyToBeFeed = 20;
        int energyToBreeding = 10;
        int minMutationsNo = 1;
        int maxMutationsNo = 3;

        GlobalOptions G_OPTIONS = new GlobalOptions(mapWidth, mapHeight, mapType, mutationType, initAnimalEnergy, initPlantEnergy, initAnimalsNo, genotypeLength, energyPerPlant, plantsPerDay, energyToBeFeed, energyToBreeding, minMutationsNo, maxMutationsNo);

        Simulation simulation = new Simulation(G_OPTIONS, 1);

        // Symulacja działa przez 5 sekund, a następnie jest zatrzymywana
        simulation.startSimulation();
        try {Thread.sleep(5000);
        } catch (InterruptedException e) {e.printStackTrace();}
        simulation.stopSimulation();

        // Odczekanie 2 sekundy, a następnie wznowienie symulacji na kolejne 3 sekundy
        try {Thread.sleep(2000);
        } catch (InterruptedException e) {e.printStackTrace();}
        simulation.startSimulation();

        try {Thread.sleep(5000);
        } catch (InterruptedException e) {e.printStackTrace();
        }
        simulation.stopSimulation();

    }
}
