package project.backend.backend.model.sprites.animalUtil;

import project.backend.backend.global.GlobalOptions;
import project.backend.backend.extras.Random;
import project.backend.backend.model.sprites.Animal;

public class GenotypeMerger {

    public static int[] merge(Animal Animal1, Animal Animal2 , GlobalOptions globalOptions){
        if (Animal1.getGenotype().length != Animal2.getGenotype().length){
            throw new IllegalStateException("Genotypes of Animal1 , Animal2 are not of equal length!");
        }

        Animal AnimalsGenesOrder[] = {Animal1 , Animal2}; //left , right taking of slice
        if (Random.randInt(0,1) == 1){ //randomly change Animals order
            AnimalsGenesOrder = new Animal[]{Animal2 , Animal1};
        }

        //TODO: bug warning! excluded idx!
        final float ratio =  ( (float) AnimalsGenesOrder[0].getEnergy()) / ( (float) Animal1.getEnergy() + (float) Animal2.getEnergy());
        final int leftSliceEndIdx = (int) (globalOptions.genotypeLength() * ratio);

        //rewrite genotype
        int[] newGenotype = new int[globalOptions.genotypeLength()];
        for (int i = 0 ; i < leftSliceEndIdx ; i++){
            newGenotype[i] = AnimalsGenesOrder[0].getGenotype()[i];
        }
        for (int i = leftSliceEndIdx ; i < globalOptions.genotypeLength() ; i++){
            newGenotype[i] = AnimalsGenesOrder[1].getGenotype()[i];
        }

        WayOfMutation_able wayOfMutation;
        switch (globalOptions.mutationType()){
            case SLIGHT_CORRECTION -> {wayOfMutation = new SlightCorrection(globalOptions);}
            case FULL_RANDOM -> {wayOfMutation = new FullRandomMutation(globalOptions);}
            default -> throw new IllegalStateException("Unexpected MutationType: " + globalOptions.mutationType());
        }

        wayOfMutation.applyMutation(newGenotype);





        return newGenotype;
    }
}
