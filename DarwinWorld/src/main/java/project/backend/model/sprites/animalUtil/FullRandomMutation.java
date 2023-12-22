package project.backend.model.sprites.animalUtil;

import project.backend.model.models.Random;

public class FullRandomMutation implements WayOfMutation_able{
    @Override
    public void applyMutation(int[] genotype) {
        int mutationNo = Random.randInt(0,genotype.length);
        int[] idxToChangeArray = Random.randIntArray(0 , genotype.length , mutationNo);
        for (int idx : idxToChangeArray){
            //mutation of random idx to random gen value
            genotype[idx] = Random.randInt(0,7);
        }
    }
}
