package project.backend.model.models;

public class CyclicListExtras {

    private int size;

    public CyclicListExtras(int ArraySize){
        if (ArraySize <= 0) {
            throw new IllegalArgumentException("ArraySize must be > 0");
        }
        this.size = ArraySize;
    }

    public int getIncrementedIdx(int idx){
        return (idx + 1)%size;
    }

    public int getDecrementIdx(int idx){
        if (idx - 1 < 0){
            return size - 1;
        }
        else{
            return idx - 1;
        }
    }

    public int getNewIdx(int idx, int toAdd){
        if ((toAdd >= 0)) {
            return (idx + toAdd) % size;
        } else {
            // TODO: check heuristics!!
            return (size + ((idx + toAdd)%size));
//            return ((idx + toAdd) % size + size) % size;
        }
    }


}
