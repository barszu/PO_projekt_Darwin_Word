package project.backend;

//import agh.ics.oop.model.*;
import project.backend.model.maps.WorldMap;
import project.backend.model.exceptions.PositionAlreadyOccupiedException;
import project.backend.model.models.Animal;
import project.backend.model.enums.MoveDirection;
import project.backend.model.models.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Simulation extends Thread {
    private final List<MoveDirection> directionsList; //moves for animals

    public List<Animal> getAnimalsList() {
        return Collections.unmodifiableList(animalsList);
    }

    private final List<Animal> animalsList; //animals in the simulation

    private final WorldMap worldMap;

    //for rectangular map
    public Simulation(List<MoveDirection> directionsList , List<Vector2d> positionsList , WorldMap worldMap ){
        this.directionsList = directionsList; //as link list
        this.animalsList = new ArrayList<>(); //as normal list because we will pick el from the middle
        this.worldMap = worldMap;


        for (Vector2d position: positionsList){
            Animal animal = new Animal(position);
            try {
                worldMap.place(animal);
                animalsList.add(animal);
            } catch (PositionAlreadyOccupiedException e) {
                //nothing to do
                System.out.println("WARNING: (Animal placing was skipped) when placing Animal: " + e.getMessage());

            }

        }
    }

    public void run(){
        int animalIdx = 0 ;
        if (this.animalsList.size() == 0){ return;} //nothing to do, no animals present
        for (MoveDirection direction: this.directionsList){ //if no moves 'for' won't start
            // animal on animalIdx gets moved
            Animal animal = this.animalsList.get(animalIdx);
//            animal.move(direction);
            this.worldMap.move(animal,direction);
//            System.out.print(this.worldMap.toString());

//            System.out.println("Zwierze %d: %s".formatted(animalIdx , animal.toString() ));
//            System.out.println("Zwierze %d: %s".formatted(animalIdx , animalReference.getPosition().toString() ));

            animalIdx ++ ;
            if (animalIdx >= this.animalsList.size()){
                animalIdx = 0 ; //reset idx for repetition
//                try {
//                    Thread.sleep(1500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

        }


//        System.out.print(this.worldMap.toString());
    }
}
