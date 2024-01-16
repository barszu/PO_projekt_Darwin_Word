package project.backend.backend.model.sprites.animalUtil;

import project.backend.backend.extras.CyclicListExtras;
import project.backend.backend.globalViaSimulation.GlobalOptions;
import project.backend.backend.extras.Random;

/**
 * This class implements the IWayOfMutation interface and defines a way of mutation for a genotype.
 * The mutation is applied by changing a random number of genes to slightly different values (+- 1).
 * The number of genes to change is determined by a random number between the minimum and maximum number of mutations defined in globalOptions.
 * The genes to change are selected randomly.
 * The new values for the genes are determined by adding or subtracting 1 from the current value, treating the gene values as a cyclic list.
 */
public class SlightCorrectionMutation implements IWayOfMutation {

    // GlobalOptions object that contains the configuration for the mutation
    private final GlobalOptions globalOptions;

    /**
     * The constructor for the SlightCorrectionMutation class.
     * It initializes the globalOptions object.
     * @param globalOptions The GlobalOptions object that contains the configuration for the mutation.
     */
    public SlightCorrectionMutation(GlobalOptions globalOptions){
        this.globalOptions = globalOptions;
    }

    /**
     * This method applies a mutation to a genotype.
     * It changes a random number of genes to slightly different values.
     * The number of genes to change is determined by a random number between the minimum and maximum number of mutations defined in globalOptions.
     * The genes to change are selected randomly.
     * The new values for the genes are determined by adding or subtracting 1 from the current value, treating the gene values as a cyclic list.
     * @param genotype The genotype to mutate. It is an array of integers.
     */
    @Override
    public void applyMutation(int[] genotype) {
        // Determine the number of mutations
        final int mutationNo = Random.randInt(globalOptions.minMutationsNo(),globalOptions.maxMutationsNo());

        // Determine the indices of the genes to change
        final int[] idxToChangeArray = Random.randIntArray(0 , genotype.length-1 , mutationNo);

        // Apply the mutations
        for (int idx : idxToChangeArray){
            // Change the gene at idx to a value that is 1 more or 1 less than the current value, treating the gene values as a cyclic list
            genotype[idx] = CyclicListExtras.getNewIdx( idx , Random.pickOneOf(-1,1) , 8);
        }
    }
}
