package project.backend;

public record GlobalOptions(
        int MapWidth,
        int MapHeight,
        int startEnergy,
        int moveEnergy,
        int eatEnergy
) {
}
