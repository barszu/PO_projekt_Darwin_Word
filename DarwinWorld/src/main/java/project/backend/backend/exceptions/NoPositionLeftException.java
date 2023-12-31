package project.backend.backend.exceptions;

public class NoPositionLeftException extends Exception{
        //only for Biomes
        public NoPositionLeftException() {
            super("No free position left in Biomes.");
        }
}
