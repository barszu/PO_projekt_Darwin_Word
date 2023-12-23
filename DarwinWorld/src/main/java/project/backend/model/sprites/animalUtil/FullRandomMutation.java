package project.backend.model.sprites.animalUtil;

import project.backend.global.GlobalOptions;
import project.backend.model.models.Random;

public class FullRandomMutation implements WayOfMutation_able{

    private final GlobalOptions globalOptions;

    public FullRandomMutation(GlobalOptions globalOptions){
        this.globalOptions = globalOptions;
    }

    @Override
    public void applyMutation(int[] genotype) {
        final int mutationNo = Random.randInt(globalOptions.minMutationsNo(),globalOptions.maxMutationsNo());

        final int[] idxToChangeArray = Random.randIntArray(0 , genotype.length-1 , mutationNo);
        for (int idx : idxToChangeArray){
            //mutation of random idx to random gen value
            genotype[idx] = Random.randInt(0,7);
        }
    }
}
