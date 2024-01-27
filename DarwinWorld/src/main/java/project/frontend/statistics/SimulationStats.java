package project.frontend.statistics;  // to na pewno frontend?

public record SimulationStats(
        int day,
        int animalsNo,
        int deadAnimalsNo,
        int grassNo,
        int topGene,
        int fieldsWithoutGrassNo,
        int fieldsWithoutAnimalsNo,
        int emptyFieldsNo,
        double avgEnergy, // from living one's
        double avgLifeTime, //from dead one's
        double avgNumberOfChildren,
        double avgNumberOfSuccessors
) {

//    public SimulationStats {
//        if (day < 0) throw new IllegalArgumentException("Day cannot be negative");
//        if (animalsNo < 0) throw new IllegalArgumentException("Animals number cannot be negative");
//        if (deadAnimalsNo < 0) throw new IllegalArgumentException("Dead animals number cannot be negative");
//        if (grassNo < 0) throw new IllegalArgumentException("Grass number cannot be negative");
//        if (topGene < 0) throw new IllegalArgumentException("Top gene cannot be negative");
//        if (fieldsWithoutGrassNo < 0) throw new IllegalArgumentException("Fields without grass number cannot be negative");
//        if (fieldsWithoutAnimalsNo < 0) throw new IllegalArgumentException("Fields without animals number cannot be negative");
//        if (emptyFieldsNo < 0) throw new IllegalArgumentException("Empty fields number cannot be negative");
//        if (avgEnergy < 0) throw new IllegalArgumentException("Average energy cannot be negative");
//        if (avgLifeTime < 0) throw new IllegalArgumentException("Average life time cannot be negative");
//        if (avgNumberOfChildren < 0) throw new IllegalArgumentException("Average number of children cannot be negative");
//        if (avgNumberOfSuccessors < 0) throw new IllegalArgumentException("Average number of descendants cannot be negative");
//    }

    private static final String CSV_SPLIT_BY = ",";

    @Override
    public String toString() {
        return day + CSV_SPLIT_BY +
                animalsNo + CSV_SPLIT_BY +
                deadAnimalsNo + CSV_SPLIT_BY +
                grassNo + CSV_SPLIT_BY +
                topGene + CSV_SPLIT_BY +
                fieldsWithoutGrassNo + CSV_SPLIT_BY +
                fieldsWithoutAnimalsNo + CSV_SPLIT_BY +
                emptyFieldsNo + CSV_SPLIT_BY +
                avgEnergy + CSV_SPLIT_BY +
                avgLifeTime + CSV_SPLIT_BY +
                avgNumberOfChildren + CSV_SPLIT_BY +
                avgNumberOfSuccessors;
    }

    public static String getAtributesName(){
        return "day" + CSV_SPLIT_BY +
                "animalsNo" + CSV_SPLIT_BY +
                "deadAnimalsNo" + CSV_SPLIT_BY +
                "grassNo" + CSV_SPLIT_BY +
                "topGene" + CSV_SPLIT_BY +
                "fieldsWithoutGrassNo" + CSV_SPLIT_BY +
                "fieldsWithoutAnimalsNo" + CSV_SPLIT_BY +
                "emptyFieldsNo" + CSV_SPLIT_BY +
                "avgEnergy" + CSV_SPLIT_BY +
                "avgLifeTime" + CSV_SPLIT_BY +
                "avgNumberOfChildren" + CSV_SPLIT_BY +
                "avgNumberOfSuccessors";
    }
}
