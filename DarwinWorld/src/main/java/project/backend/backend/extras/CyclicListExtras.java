package project.backend.backend.extras;

/**
 * This class provides utility methods for working with cyclic lists.
 * A cyclic list is a list where the next element of the last element is the first element.
 */
public class CyclicListExtras {

    /**
     * This method checks if the size of the cyclic array is greater than 0.
     * If the size is less than or equal to 0, it throws an IllegalArgumentException.
     * @param CyclicArraySize The size of the cyclic array.
     * @throws IllegalArgumentException if CyclicArraySize is less than or equal to 0.
     */
    private static void checkArraySize(int CyclicArraySize){
        if (CyclicArraySize <= 0) {
            throw new IllegalArgumentException("CyclicArraySize must be > 0");
        }
    }

    /**
     * This method returns the index of the next element in the cyclic array.
     * @param idx The current index.
     * @param CyclicArraySize The size of the cyclic array.
     * @return The index of the next element.
     */
    public static int getIncrementedIdx(int idx , int CyclicArraySize){
        checkArraySize(CyclicArraySize);
        return (idx + 1)%CyclicArraySize;
    }

    /**
     * This method returns the index of the previous element in the cyclic array.
     * @param idx The current index.
     * @param CyclicArraySize The size of the cyclic array.
     * @return The index of the previous element.
     */
    public static int getDecrementedIdx(int idx , int CyclicArraySize){
        checkArraySize(CyclicArraySize);
        if (idx - 1 < 0){
            return CyclicArraySize - 1;
        }
        else{
            return idx - 1;
        }
    }

    /**
     * This method returns a new index in the cyclic array after adding a specified number to the current index.
     * @param idx The current index.
     * @param toAdd The number to add to the current index.
     * @param CyclicArraySize The size of the cyclic array.
     * @return The new index.
     */
    public static int getNewIdx(int idx, int toAdd , int CyclicArraySize){
        checkArraySize(CyclicArraySize);

        int newIdx = idx + toAdd;

        while (newIdx < 0){
            newIdx += CyclicArraySize;
        }
        return newIdx % CyclicArraySize;
    }
}