package project.backend.backend.model.sprites; // sprite'y w backendzie?

import project.backend.backend.globalViaSimulation.GlobalVariables;
import project.backend.backend.model.enums.MapDirection;
import project.backend.backend.model.sprites.animalUtil.SuccessorDFS;
import project.backend.backend.model.sprites.animalUtil.GenotypeMerger;
import project.backend.backend.extras.CyclicListExtras;
import project.backend.backend.globalViaSimulation.GlobalOptions;
import project.backend.backend.model.maps.IMoveValidator;
import project.backend.backend.extras.Random;
import project.backend.backend.extras.Vector2d;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents an animal in the @IWorldMap (simulation).
 * An animal has a direction, position, genotype, energy, age, list of children, and other properties.
 * It provides methods to get and set these properties, move the animal, reproduce with another animal, and other operations.
 */
public class Animal implements IWorldElement, Comparable<Animal> {


    private MapDirection direction;
    private Vector2d position;

    private final int[] genotype; //list of moves
    private int currentGenotypeIndex; //current index to move
    private int energy; // if <= 0 then animal is dead
    private int age = 0; //age of animal
    private final List<Animal> childrenList = new LinkedList<>(); //children list not grandchildren!
    private boolean isDead = false; //is dead on this moment
    private int eatenGrassNo = 0; //how much grass was eaten by this animal
    private final int spawnDate; //when animal was spawned
    private final int lifespan = 0;

    //global variables / options

    private final GlobalOptions globalOptions;
    private final GlobalVariables globalVariables;

    private String color;


    //getters
    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public int getEnergy() {
        return energy;
    }

    public int[] getGenotype() {
        return genotype; // dehermetyzacja
    }

    public int getAge() {
        return age;
    }

    public List<Animal> getChildrenList() {
//        return childrenList;
        return Collections.unmodifiableList(childrenList);
    }

    public int getSpawnDate() {
        return spawnDate;
    }

    public int getEatenGrassNo() {
        return eatenGrassNo;
    }

    public int getCurrentGenotypeIndex() {
        return currentGenotypeIndex;
    }

    // constructors

    @Deprecated //dangerous // to po co jest?
    public Animal() { //old
        this.direction = MapDirection.NORTH;
        this.position = new Vector2d(2, 2);
        this.genotype = new int[5];
        this.energy = 10;
        this.spawnDate = 0;

        this.globalOptions = null;
        this.globalVariables = null;
    }

    @Deprecated //dangerous
    public Animal(Vector2d fixedPosition) { //old , not working empty field
        this.direction = Random.randDirection();
        this.position = fixedPosition;
        this.genotype = new int[5];
        this.energy = 10;
        this.spawnDate = 0;

        this.globalOptions = null;
        this.globalVariables = null;
    }

    //constructor for initial animals using only globalOptions and globalVariables
    public Animal(Vector2d fixedPosition, GlobalOptions globalOptions, GlobalVariables globalVariables) {
        this.globalOptions = globalOptions;
        this.globalVariables = globalVariables;
        this.direction = Random.randDirection(); // random direction
        this.position = fixedPosition; // random position in the map in bound
        this.genotype = Random.randIntArray(0, 7, globalOptions.genotypeLength()); // random genotype
        this.energy = globalOptions.initAnimalEnergy();
        this.currentGenotypeIndex = Random.randInt(0, genotype.length - 1);

        this.spawnDate = 0;
    }

    //constructor for reproducted animal, passing by globalOptions and globalVariables
    public Animal(Vector2d fixedPosition, int[] genotype, int energy,
                  GlobalOptions globalOptions, GlobalVariables globalVariables) {

        if (energy <= 0) {
            throw new IllegalArgumentException("energy must be >= 1");
        }
        if (genotype.length != globalOptions.genotypeLength()) {
            throw new IllegalStateException("Tried to init Animal when genotype.length != globalOptions.genotypeLength()");
        }
        this.globalOptions = globalOptions;
        this.globalVariables = globalVariables;

        this.direction = Random.randDirection();
        this.position = fixedPosition;
        this.genotype = genotype.clone(); //TODO: maybe cloning is not necessary?
        this.energy = energy;
        this.currentGenotypeIndex = Random.randInt(0, genotype.length - 1);
        this.spawnDate = globalVariables.getDate();
    }

    //energy methods
    public void addEnergy(int energy) {
        if (energy < 0) {
            throw new IllegalArgumentException("energy must be >= 0");
        }
        this.energy += energy;
    }

    public void subtractEnergy(int energy) {
        if (energy < 0) {
            throw new IllegalArgumentException("energy must be >= 0");
        }
        this.energy -= energy;
    }

    //others implementing methods
    @Override
    public String toString() {
        return color;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    @Override
    public int compareTo(Animal otherAnimal) {
        int energyCompare = Integer.compare(this.energy, otherAnimal.energy);
        int ageCompare = Integer.compare(this.age, otherAnimal.age);
        int childrenNoCompare = Integer.compare(this.childrenList.size(), otherAnimal.childrenList.size());

        if (energyCompare != 0) {
            return energyCompare;
        } else if (ageCompare != 0) {
            return ageCompare;
        } else if (childrenNoCompare != 0) {
            return childrenNoCompare;
        }

        return Random.pickOneOf(-1, 1); //animal 1 loses or wins
    }


    //strict animal methods
    public void incrementAge() {
        if (this.isDead) {
            throw new IllegalStateException("Animal is dead! Cannot grow up!");
        }
        this.age++;
    }

    public boolean isDeadAlready() { //getter for isDead
        return this.isDead;
    }

    public void incrementEatenGrassNo() {
        if (this.isDead) {
            throw new IllegalStateException("Animal is dead! Cannot increment eaten grass no!");
        }
        this.eatenGrassNo++;
    }

    public boolean checkIfDead() {
        if (this.energy <= 0) {
            this.isDead = true;
            return true;
        }
        return false;
    }

    public boolean isWellFed() {
        return this.energy >= globalOptions.energyToBeFeed();
    }

    public int getSuccessorsNo() {
        return SuccessorDFS.searchSuccessorsNo(this);
    }

    public void move(IMoveValidator moveValidatorable) {
        if (isDead) {
            throw new IllegalStateException("Animal is dead! Cannot move!");
        }
        this.direction = MapDirection.getById(genotype[currentGenotypeIndex]);
        //incrementing currentGenotypeIndex
        this.currentGenotypeIndex = CyclicListExtras.getIncrementedIdx(currentGenotypeIndex, genotype.length);

        Vector2d newPosition = this.position.add(this.direction.toUnitVector());
        newPosition = moveValidatorable.validatePosition(newPosition, this.position);

        this.position = newPosition;
    }

    public Animal reproduce(Animal other) {
        //reproduceEnergyCost < wellFedEnergy !
        if (this.isDead || other.isDead) {
            throw new IllegalStateException("One of animals is dead! Cannot reproduce!");
        }
        if (this.energy < globalOptions.energyToBeFeed() || other.energy < globalOptions.energyToBeFeed()) {
            throw new IllegalStateException("One of animals is not well fed! Cannot reproduce!");
        }

        int[] childGenotype = GenotypeMerger.merge(this, other, globalOptions);

        this.subtractEnergy(globalOptions.energyToBreeding());
        other.subtractEnergy(globalOptions.energyToBreeding());

        Animal child = new Animal(this.position, childGenotype, 2 * globalOptions.energyToBreeding(),
                globalOptions, globalVariables);

        this.childrenList.add(child);
        other.childrenList.add(child);

        return child;
    }

    public void setColor(String color) { // public? czy zwierzę powinno coś wiedzieć o kolorach?
        this.color = color;
    }

}

