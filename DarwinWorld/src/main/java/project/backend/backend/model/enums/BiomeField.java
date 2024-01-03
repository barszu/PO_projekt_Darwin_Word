package project.backend.backend.model.enums;

public enum BiomeField {
    STEP,
    JUNGLE,
    WATER;

    public String toString(){
        return switch(this) {
            case STEP -> ".";
            case JUNGLE -> "-";
            case WATER -> "#";
        };
    }

}


