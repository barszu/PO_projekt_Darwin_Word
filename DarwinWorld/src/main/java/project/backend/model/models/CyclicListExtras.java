package project.backend.model.models;

public class CyclicListExtras {

    private static void checkArraySize(int ArraySize){ //helper
        if (ArraySize <= 0) {
            throw new IllegalArgumentException("ArraySize must be > 0");
        }
    }

    public static int getIncrementedIdx(int idx , int ArraySize){
        checkArraySize(ArraySize);
        return (idx + 1)%ArraySize;
    }

    public static int getDecrementedIdx(int idx , int ArraySize){
        checkArraySize(ArraySize);
        if (idx - 1 < 0){
            return ArraySize - 1;
        }
        else{
            return idx - 1;
        }
    }

    public static int getNewIdx(int idx, int toAdd , int ArraySize){
        checkArraySize(ArraySize);
        if ((toAdd >= 0)) {
            return (idx + toAdd) % ArraySize;
        } else {
            // TODO: check heuristics!!
            return (ArraySize + ((idx + toAdd)%ArraySize));
//            return ((idx + toAdd) % ArraySize + ArraySize) % ArraySize;
        }
    }


}
