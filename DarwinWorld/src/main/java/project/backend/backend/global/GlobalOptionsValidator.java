package project.backend.backend.global;

import project.backend.backend.exceptions.OptionsValidateException;
import project.backend.backend.extras.MathUtils;

public class GlobalOptionsValidator {
    public static final GlobalOptions minOptions = new GlobalOptions (
            3,
            3,
            null,
            null,
            1,
            0,
            0,
            1,
            0,
            0,
            0,
            0,
            0,
            0
    );

    public static final GlobalOptions maxOptions = new GlobalOptions (
            200,
            200,
            null,
            null,
            10000,
            10000,
            10000,
            10000,
            10000,
            10000,
            10000,
            10000,
            10000,
            10000
    );


    public static void validate(GlobalOptions options) throws OptionsValidateException{ //rzuca wyjatkiem jak jest cos zle, z wyjatku da sie wyciagnac co jest zle
        if (!MathUtils.isBetween(minOptions.mapWidth(), options.mapWidth(), maxOptions.mapWidth())){
            throw new OptionsValidateException("mapWidth" , "mapWidth must be between " + minOptions.mapWidth() + " and " + maxOptions.mapWidth());
        }
        if (!MathUtils.isBetween(minOptions.mapHeight(), options.mapHeight(), maxOptions.mapHeight())){
            throw new OptionsValidateException("mapHeight" , "mapHeight must be between " + minOptions.mapHeight() + " and " + maxOptions.mapHeight());
        }
        if (!MathUtils.isBetween(minOptions.initAnimalEnergy(), options.initAnimalEnergy(), maxOptions.initAnimalEnergy())){
            throw new OptionsValidateException("initAnimalEnergy" , "initAnimalEnergy must be between " + minOptions.initAnimalEnergy() + " and " + maxOptions.initAnimalEnergy());
        }
        if (!MathUtils.isBetween(minOptions.initPlantEnergy(), options.initPlantEnergy(), maxOptions.initPlantEnergy())){
            throw new OptionsValidateException("initPlantEnergy" , "initPlantEnergy must be between " + minOptions.initPlantEnergy() + " and " + maxOptions.initPlantEnergy());
        }
        if (!MathUtils.isBetween(minOptions.initAnimalsNo(), options.initAnimalsNo(), maxOptions.initAnimalsNo())){
            throw new OptionsValidateException("initAnimalsNo" , "initAnimalsNo must be between " + minOptions.initAnimalsNo() + " and " + maxOptions.initAnimalsNo());
        }
        if (!MathUtils.isBetween(minOptions.genotypeLength(), options.genotypeLength(), maxOptions.genotypeLength())){
            throw new OptionsValidateException("genotypeLength" , "genotypeLength must be between " + minOptions.genotypeLength() + " and " + maxOptions.genotypeLength());
        }
        if (!MathUtils.isBetween(minOptions.energyPerPlant(), options.energyPerPlant(), maxOptions.energyPerPlant())){
            throw new OptionsValidateException("energyPerPlant" , "energyPerPlant must be between " + minOptions.energyPerPlant() + " and " + maxOptions.energyPerPlant());
        }
        if (!MathUtils.isBetween(minOptions.plantsPerDay(), options.plantsPerDay(), maxOptions.plantsPerDay())){
            throw new OptionsValidateException("plantsPerDay" , "plantsPerDay must be between " + minOptions.plantsPerDay() + " and " + maxOptions.plantsPerDay());
        }
        if (!MathUtils.isBetween(minOptions.energyToBeFeed(), options.energyToBeFeed(), maxOptions.energyToBeFeed())){
            throw new OptionsValidateException("energyToBeFeed" , "energyToBeFeed must be between " + minOptions.energyToBeFeed() + " and " + maxOptions.energyToBeFeed());
        }
        if (!MathUtils.isBetween(minOptions.energyToBreeding(), options.energyToBreeding(), maxOptions.energyToBreeding())){
            throw new OptionsValidateException("energyToBreeding" , "energyToBreeding must be between " + minOptions.energyToBreeding() + " and " + maxOptions.energyToBreeding());
        }
        if (!MathUtils.isBetween(minOptions.minMutationsNo(), options.minMutationsNo(), maxOptions.minMutationsNo())){
            throw new OptionsValidateException("minMutationsNo" , "minMutationsNo must be between " + minOptions.minMutationsNo() + " and " + maxOptions.minMutationsNo());
        }
        if (!MathUtils.isBetween(minOptions.maxMutationsNo(), options.maxMutationsNo(), maxOptions.maxMutationsNo())){
            throw new OptionsValidateException("maxMutationsNo" , "maxMutationsNo must be between " + minOptions.maxMutationsNo() + " and " + maxOptions.maxMutationsNo());
        }

        //logical
        if (!(options.minMutationsNo() <= options.maxMutationsNo())){
            throw new OptionsValidateException("minMutationsNo" , "minMutationsNo must be <= maxMutationsNo");
        }
        if (!(options.energyToBreeding() <= options.energyToBeFeed())){
            throw new OptionsValidateException("energyToBreeding" , "energyToBreeding must be <= energyToBeFeed");
        }


    }


}
