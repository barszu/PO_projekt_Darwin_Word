package project.backend.model.sprites;
import project.backend.model.maps.RectangleBoundary;
import project.backend.model.maps.MoveValidatorable;
import project.backend.model.enums.MapDirection;
import agh.ics.oop.model.MoveDirection;
import project.backend.model.exceptions.PositionAlreadyOccupiedException;
import project.backend.model.models.Random;
import project.backend.model.models.Vector2d;
import project.backend.model.sprites.animalUtil.GenotypeMerger;

import java.sql.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Animal implements WorldElementable , Comparable<Animal> {


    private MapDirection direction ;
    private Vector2d position;

    private final int[] genotype; //list of moves
    private int currentGenotypeIndex; //current index to move
    private int energy; // if <= 0 then animal is dead
    private int age = 0; //age of animal
    private List<Animal> childrenList = new LinkedList<>(); //children list not grandchildren!
    private boolean isDead = false; //is dead on this moment
    private int eatenGrassNo = 0; //how much grass was eaten by this animal
    private final int spawnDate; //when animal was spawned

    //getters
    public Vector2d getPosition() {
        return position;
    }
    public MapDirection getDirection() {
        return direction;
    }
    public int getEnergy() {return energy;}
    public int[] getGenotype() {return genotype;}

    // constructors
    public Animal(){ //old
        this.direction = MapDirection.NORTH;
        this.position = new Vector2d(2,2);
        this.genotype = new int[5];
        this.energy = 10;
        this.spawnDate = 0;
    }

    public Animal(Vector2d fixedPosition){ //old
        this.direction = Random.randDirection();
        this.position = fixedPosition;
        this.genotype = new int[5];
        this.energy = 10;
        this.spawnDate = 0;
    }

    public Animal(Vector2d fixedPosition , int genotypeLen , int energy){
        if (genotypeLen <= 0){throw new IllegalArgumentException("genotypeLen must be >= 1");}
        if (energy <= 0){throw new IllegalArgumentException("energy must be >= 1");}
        this.direction = Random.randDirection();
        this.position = fixedPosition;
        this.genotype = new int[genotypeLen];
        this.energy = energy;
        this.currentGenotypeIndex = Random.randInt(0,genotypeLen-1);
        this.spawnDate = 0;
    }

    //constructor for initial animals
    public Animal(RectangleBoundary bound , int genotypeLen , int energy){
        if (genotypeLen <= 0){throw new IllegalArgumentException("genotypeLen must be >= 1");}
        if (energy <= 0){throw new IllegalArgumentException("energy must be >= 1");}
        this.direction = Random.randDirection(); // random direction
        this.position = Random.randPosition(bound); // random position in the map in bound
        this.genotype = Random.randIntArray(0,7,genotypeLen); // random genotype
        this.energy = energy;
        this.currentGenotypeIndex = Random.randInt(0,genotypeLen-1);

        this.spawnDate = 1;
    }

    //constructor for reproducted animal
    public Animal(Vector2d fixedPosition , int[] genotype , int energy , int spawnDate){
        if (genotype.length <= 0){throw new IllegalArgumentException("genotype array must contain >= 1 el");}
        if (energy <= 0){throw new IllegalArgumentException("energy must be >= 1");}
        this.direction = Random.randDirection();
        this.position = fixedPosition;
        this.genotype = genotype.clone(); //TODO: maybe cloning is not necessary?
        this.energy = energy;
        this.currentGenotypeIndex = Random.randInt(0,genotype.length-1);
        this.spawnDate = spawnDate;
    }

    //energy methods
    public void addEnergy(int energy){
        if (energy < 0){throw new IllegalArgumentException("energy must be >= 0");}
        this.energy += energy;
    }

    public void subtractEnergy(int energy){
        if (energy < 0){throw new IllegalArgumentException("energy must be >= 0");}
        this.energy -= energy;
    }

    //others implementing methods
    @Override
    public String toString() {
        return "Animal{" +
            "direction=" + direction +
            ", position=" + position +
            ", genotype=" + Arrays.toString(genotype) +
            ", currentGenotypeIndex=" + currentGenotypeIndex +
            ", energy=" + energy +
            '}';
//        return "Animal{" + direction.toString() + position.toString() + '}';
//        return direction.toString();
    }
    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    @Override
    public int compareTo(Animal otherAnimal) {
        int energyCompare = Integer.compare(this.energy, otherAnimal.energy);
        int ageCompare = Integer.compare(this.age, otherAnimal.age);
        int childrenNoCompare = Integer.compare(this.childrenList.size(), otherAnimal.childrenList.size());

        if (energyCompare != 0){return energyCompare;}
        else if (ageCompare != 0){return ageCompare;}
        else if (childrenNoCompare != 0){return childrenNoCompare;}

        return Random.pickOneOf(-1,1); //animal 1 loses or wins
    }


    //strict animal methods
    public void growUp(){
        if (this.isDead){
            throw new IllegalStateException("Animal is dead! Cannot grow up!");
        }
        this.age++;
    }

    public boolean isDeadAlready(){ //getter for isDead
        return this.isDead;
    }

    public void incrementEatenGrassNo(){
        if (this.isDead){
            throw new IllegalStateException("Animal is dead! Cannot increment eaten grass no!");
        }
        this.eatenGrassNo++;
    }


    //old
    public void move(MoveDirection direction, MoveValidatorable moveValidatorable) throws PositionAlreadyOccupiedException {
        Vector2d newPosition = this.position;
        switch (direction){
            //changing direction by rotation
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            //changing position, go forward/backward with no rotation
            case FORWARD -> newPosition = this.position.add(this.direction.toUnitVector());
            case BACKWARD -> newPosition = this.position.subtract(this.direction.toUnitVector());
        }
        if (moveValidatorable.canMoveTo(newPosition)){
            this.position = newPosition;
        }
        else {
            throw new PositionAlreadyOccupiedException(newPosition);
            // without changing position!
        }
    }

    //new, nie obsluguje dziwnego teleportowania na koniec mapy!
    //TODO: implement this!
    public void move(MoveValidatorable moveValidatorable) throws PositionAlreadyOccupiedException{
        if (isDead){
            throw new IllegalStateException("Animal is dead! Cannot move!");
        }
        this.direction = MapDirection.getById(genotype[currentGenotypeIndex]);
        this.currentGenotypeIndex = (currentGenotypeIndex + 1) % genotype.length;

        Vector2d newPosition = this.position.add(this.direction.toUnitVector());
        if (moveValidatorable.canMoveTo(newPosition)){ //do zmiany problem to te zawiajane mapy
            this.position = newPosition;
        }
        else {
            throw new PositionAlreadyOccupiedException(newPosition);
            // without changing position!
        }
    }

    //TODO: implement this
    public Animal reproduce(Animal other , int wellFedEnergy , int reproduceEnergyCost , int genotypeLen , int currDate){
    //reproduceEnergyCost < wellFedEnergy !
        if (this.isDead || other.isDead){throw new IllegalStateException("One of animals is dead! Cannot reproduce!");}
        if (this.energy < wellFedEnergy || other.energy < wellFedEnergy){throw new IllegalStateException("One of animals is not well fed! Cannot reproduce!");}

        int[] childGenotype = GenotypeMerger.merge(this , other , genotypeLen);

        this.subtractEnergy(reproduceEnergyCost);
        other.subtractEnergy(reproduceEnergyCost);

        Animal child = new Animal(this.position , childGenotype , 2*reproduceEnergyCost , currDate);

        this.childrenList.add(child);
        other.childrenList.add(child);

        return child;
    }





}
