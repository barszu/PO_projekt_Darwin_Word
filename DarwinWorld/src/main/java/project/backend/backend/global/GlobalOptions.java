package project.backend.backend.global;

import project.backend.backend.model.enums.MapType;
import project.backend.backend.model.enums.MutationType;

public record GlobalOptions(
        //map options
        int mapWidth,
        int mapHeight,

        //environment options
        MapType mapType,
        MutationType mutationType,

        //initial options
        int initAnimalEnergy,
        int initPlantEnergy,
        int initAnimalsNo,

        //general simulation options
        int genotypeLength,
        int energyPerPlant,
        int plantsPerDay,
        int energyToBeFeed,
        int energyToBreeding,
        int minMutationsNo,
        int maxMutationsNo

) {}
