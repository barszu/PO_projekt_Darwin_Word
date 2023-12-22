package project.backend.model.sprites.animalUtil;

import project.backend.model.models.Random;
import project.backend.model.sprites.Animal;

public class GenotypeMerger {
    //TODO: globalne ustawienia! genotypeLen , mutationType , mutationChance
    public static int[] merge(Animal Animal1, Animal Animal2 , int genotypeLen){
        if (Animal1.getGenotype().length != Animal2.getGenotype().length){
            System.out.println("WARNING: Genotypes are not of equal length");
            return null;
        }
        Animal AnimalsGenesOrder[] = {Animal1 , Animal2}; //left , right taking of slice
        if (Random.randInt(0,1) == 1){ //randomly change Animals order
            AnimalsGenesOrder = new Animal[]{Animal2 , Animal1};
        }
        //bug warning! excluded idx!
        int leftSliceEndIdx = genotypeLen * ((AnimalsGenesOrder[0].getEnergy()) / (Animal1.getEnergy() + Animal2.getEnergy()));

        //rewrite genotype
        int[] newGenotype = new int[genotypeLen];
        for (int i = 0 ; i < leftSliceEndIdx ; i++){
            newGenotype[i] = AnimalsGenesOrder[0].getGenotype()[i];
        }
        for (int i = leftSliceEndIdx ; i < genotypeLen ; i++){
            newGenotype[i] = AnimalsGenesOrder[1].getGenotype()[i];
        }

        //TODO: mutate zaleznie od mutationType
        WayOfMutation_able wayOfMutation = new SlightCorrection();
        wayOfMutation.applyMutation(newGenotype);





        return newGenotype;
    }
}
