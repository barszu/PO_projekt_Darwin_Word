package project.backend.model.sprites.animalUtil;

import project.backend.global.GlobalOptions;
import project.backend.model.models.CyclicListExtras;
import project.backend.model.models.Random;

public class SlightCorrection implements WayOfMutation_able{

    private final GlobalOptions globalOptions;

    public SlightCorrection(GlobalOptions globalOptions){
        this.globalOptions = globalOptions;
    }

    @Override
    public void applyMutation(int[] genotype) {
        final int mutationNo = Random.randInt(globalOptions.minMutationsNo(),globalOptions.maxMutationsNo());
        final int[] idxToChangeArray = Random.randIntArray(0 , genotype.length-1 , mutationNo);

        for (int idx : idxToChangeArray){
            //mutation of random idx to +- 1 gen value
            //treating as [0,1,2,3,4,5,6,7] cyclic list
            genotype[idx] = CyclicListExtras.getNewIdx( idx , Random.pickOneOf(-1,1) , 8);
        }
    }
}
