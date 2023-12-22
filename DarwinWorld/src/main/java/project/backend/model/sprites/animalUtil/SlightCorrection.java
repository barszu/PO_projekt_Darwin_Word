package project.backend.model.sprites.animalUtil;

import project.backend.model.models.CyclicListExtras;
import project.backend.model.models.Random;

public class SlightCorrection implements WayOfMutation_able{
    @Override
    public void applyMutation(int[] genotype) {
        int mutationNo = Random.randInt(0,genotype.length);
        int[] idxToChangeArray = Random.randIntArray(0 , genotype.length , mutationNo);

        CyclicListExtras cyclicListExtras = new CyclicListExtras(genotype.length);

        for (int idx : idxToChangeArray){
            //mutation of random idx to +- 1 gen value
            //treating as [0,1,2,3,4,5,6,7] cyclic list
            genotype[idx] = cyclicListExtras.getNewIdx( idx , Random.pickOneOf(-1,1));
        }
    }
}
