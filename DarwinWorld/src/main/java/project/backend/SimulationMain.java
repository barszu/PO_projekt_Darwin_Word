package project.backend;

import project.backend.global.GlobalOptions;
import project.backend.global.GlobalVariables;
import project.backend.model.enums.MapType;
import project.backend.model.enums.MutationType;
import project.backend.model.maps.RectangleBoundary;
import project.backend.model.models.Vector2d;
import project.backend.model.sprites.Animal;

public class SimulationMain {

    public static GlobalOptions G_OPTIONS;
    public static void main(String[] args) {
        System.out.println("Hello World!");

        //args for GlobalOptions

        //map options
        int mapWidth = 10;
        int mapHeight = 10;

        //environment options
        MapType mapType = MapType.NORMAL_MAP;
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
        GlobalVariables G_VAR = new GlobalVariables();

        RectangleBoundary bound = new RectangleBoundary(new Vector2d(0,0) , new Vector2d(10,10));

        Animal animal = new Animal(bound, G_OPTIONS , G_VAR);
        Animal animal2 = new Animal(bound, G_OPTIONS , G_VAR);
        animal.addEnergy(100);
        animal2.addEnergy(100);
        Animal animal3 = animal.reproduce(animal2);
    }
}
