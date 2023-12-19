package project.backend.model.models;
import project.backend.model.MoveValidator;
import project.backend.model.enums.MapDirection;
import project.backend.model.enums.MoveDirection;
import project.backend.model.exceptions.PositionAlreadyOccupiedException;

public class Animal implements WorldElement{


    private MapDirection direction ;
    private Vector2d position;

    public Vector2d getPosition() {
        return position;
    }
    public MapDirection getDirection() {
        return direction;
    }

    public Animal(){
        this.direction = MapDirection.NORTH;
        this.position = new Vector2d(2,2);
    }

    public Animal(Vector2d fixedPosition){
        this.direction = MapDirection.NORTH;
        this.position = fixedPosition;
    }

    @Override
    public String toString() {
//        return "Animal{" + direction.toString() + position.toString() + '}';
        return direction.toString();
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public void move(MoveDirection direction, MoveValidator moveValidator) throws PositionAlreadyOccupiedException {
        Vector2d newPosition = this.position;
        switch (direction){
            //changing direction by rotation
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            //changing position, go forward/backward with no rotation
            case FORWARD -> newPosition = this.position.add(this.direction.toUnitVector());
            case BACKWARD -> newPosition = this.position.subtract(this.direction.toUnitVector());
        }
        if (moveValidator.canMoveTo(newPosition)){
            this.position = newPosition;
        }
        else {
            throw new PositionAlreadyOccupiedException(newPosition);
            // without changing position!
        }
    }
}
