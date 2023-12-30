package project.backend.backend;

import project.backend.backend.global.GlobalOptions;
import project.backend.backend.global.GlobalVariables;
import project.backend.backend.model.maps.CylindricalGlobeMap;
import project.backend.backend.model.maps.WaterMap;
import project.backend.backend.model.maps.WorldMap_able;

public class Simulation extends Thread{
    private final WorldMap_able worldMap;
    private final GlobalVariables globalVariables;
    private final GlobalOptions globalOptions;

    private boolean isRunning = false;


    public Simulation(GlobalOptions globalOptions){
        this.globalOptions = globalOptions;
        this.globalVariables = new GlobalVariables();
        switch (globalOptions.mapType()){
            case NORMAL_MAP -> this.worldMap = new CylindricalGlobeMap(globalOptions,globalVariables);
            case WATER_MAP -> this.worldMap = new WaterMap(globalOptions,globalVariables);
            default -> throw new IllegalArgumentException("Unknown map type: " + globalOptions.mapType().toString());
        }
    }

    //TODO: implement this method!
    public void start(){ //or resume
//        this.isRunning = true;
//        while(this.isRunning){
////            TODO: update everithing on map!!!
//            this.worldMap.updateEverything();
//
//        }
        WaterMap waterMap = new WaterMap(globalOptions, globalVariables);
        System.out.println("lista wody: "+waterMap.getWaterPositions());
        System.out.println("lista krawędzi: "+waterMap.getWaterEdges());
        waterMap.updateEverything();
        System.out.println("lista wody: "+waterMap.getWaterPositions());
        System.out.println("lista krawędzi: "+waterMap.getWaterEdges());
        waterMap.updateEverything();
        System.out.println("lista wody: "+waterMap.getWaterPositions());
        System.out.println("lista krawędzi: "+waterMap.getWaterEdges());
        waterMap.updateEverything();
        System.out.println("lista wody: "+waterMap.getWaterPositions());
        System.out.println("lista krawędzi: "+waterMap.getWaterEdges());
        waterMap.updateEverything();
    }

}
