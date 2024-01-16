package project.backend.backend.model.sprites.animalUtil;

/**
 * This interface defines a way of mutation for a genotype.
 * Classes that implement this interface can apply a mutation to a genotype by implementing the applyMutation method.
 */
public interface IWayOfMutation {

    /**
     * This method applies a mutation to a genotype.
     * Implementing classes should define how the mutation is applied.
     *
     * @param genotype The genotype to mutate. It is an array of integers.
     */
    void applyMutation(int[] genotype);
}
