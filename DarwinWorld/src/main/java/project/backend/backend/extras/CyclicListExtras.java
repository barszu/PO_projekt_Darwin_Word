package project.backend.backend.extras;

public class CyclicListExtras {

    private static void checkArraySize(int CyclicArraySize){ //helper
        if (CyclicArraySize <= 0) {
            throw new IllegalArgumentException("CyclicArraySize must be > 0");
        }
    }

    public static int getIncrementedIdx(int idx , int CyclicArraySize){
        checkArraySize(CyclicArraySize);
        return (idx + 1)%CyclicArraySize;
    }

    public static int getDecrementedIdx(int idx , int CyclicArraySize){
        checkArraySize(CyclicArraySize);
        if (idx - 1 < 0){
            return CyclicArraySize - 1;
        }
        else{
            return idx - 1;
        }
    }

    public static int getNewIdx(int idx, int toAdd , int CyclicArraySize){
        checkArraySize(CyclicArraySize);
        // TODO: check heuristics!!

        int newIdx = idx + toAdd;

        while (newIdx < 0){
            newIdx += CyclicArraySize;
        }
        return newIdx % CyclicArraySize;
    }


}
