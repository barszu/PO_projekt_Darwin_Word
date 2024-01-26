package project.frontend.listeners;

import javafx.application.Platform;
import javafx.scene.control.Label;
import project.backend.backend.listeners.IMapChangeListener;
import project.backend.backend.model.maps.IWorldMap;
import project.backend.backend.model.sprites.Animal;
import project.frontend.statistics.SimulationStats;
import project.frontend.statistics.SimulationStatsBuilder;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class SimulationStatsListener implements IMapChangeListener {

    private final List<SimulationStats> simulationStatsList = new LinkedList<>();
    private int deadAnimalsNo = 0;
    private double avgLifeTime = 0.0;

    //labels
    private Label dayLabel = null;
    private Label animalsNoLabel = null;
    private Label deadAnimalsNoLabel = null;
    private Label grassNoLabel = null;
    private Label topGeneLabel = null;
    private Label fieldsWithoutGrassNoLabel = null;
    private Label fieldsWithoutAnimalsNoLabel = null;
    private Label emptyFieldsNoLabel = null;
    private Label avgEnergyLabel = null;
    private Label avgLifeTimeLabel = null;
    private Label avgNumberOfChildrenLabel = null;
    private Label avgNumberOfSuccessorsLabel = null;
    public SimulationStatsListener(
            Label DayLabel,
            Label AnimalsNoLabel,
            Label DeadAnimalsNoLabel,
            Label grassNoLabel,
            Label TopGeneLabel,
            Label FieldsWithoutGrassNoLabel,
            Label FieldsWithoutAnimalsNoLabel,
            Label EmptyFieldsNoLabel,
            Label AvgEnergyLabel,
            Label AvgLifeTimeLabel,
            Label AvgNumberOfChildrenLabel,
            Label AvgNumberOfSuccessorsLabel
    ) {
        this.dayLabel = DayLabel;
        this.animalsNoLabel = AnimalsNoLabel;
        this.deadAnimalsNoLabel = DeadAnimalsNoLabel;
        this.grassNoLabel = grassNoLabel;
        this.topGeneLabel = TopGeneLabel;
        this.fieldsWithoutGrassNoLabel = FieldsWithoutGrassNoLabel;
        this.fieldsWithoutAnimalsNoLabel = FieldsWithoutAnimalsNoLabel;
        this.emptyFieldsNoLabel = EmptyFieldsNoLabel;
        this.avgEnergyLabel = AvgEnergyLabel;
        this.avgLifeTimeLabel = AvgLifeTimeLabel;
        this.avgNumberOfChildrenLabel = AvgNumberOfChildrenLabel;
        this.avgNumberOfSuccessorsLabel = AvgNumberOfSuccessorsLabel;
    }

    public SimulationStatsListener(){}

    public SimulationStatsBuilder toBuild(){
        return new SimulationStatsBuilder();
    }


    @Override
    public void mapChanged(IWorldMap worldMap_able, String message) {
        addNewStat(worldMap_able);
        Platform.runLater(() ->{
            //satisfy labels
            updateLabels(simulationStatsList.get(simulationStatsList.size()-1));
        });
    }

    private void updateLabels(SimulationStats simulationStats){
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.getType().equals(Label.class)) {
                    Label label = (Label) field.get(this);
                    String labelName = field.getName().replaceFirst("Label", "");

                    Field simulationStatsField = simulationStats.getClass().getDeclaredField(labelName);
                    simulationStatsField.setAccessible(true);

                    String text;
                    if (simulationStatsField.getType().equals(double.class)) {
                        double value = (double) simulationStatsField.get(simulationStats);
                        text = String.format("%.2f", value);
                    } else {
                        text = simulationStatsField.get(simulationStats).toString();
                    }

//                    String text = simulationStatsField.get(simulationStats).toString();
                    label.setText(text);

                    simulationStatsField.setAccessible(false);
                }
                field.setAccessible(false);
            } catch (NullPointerException ignored){//field is null // to chyba źle?
            } catch (Exception e){ // ?
                System.out.println("Error while updating labels -> " + e.getMessage());
            }
        }
    }

    private void addNewStat(IWorldMap worldMap_able){
        List<Animal> freshDeadAnimalsList= worldMap_able.getRecentlySlainedAnimals();

        if (freshDeadAnimalsList.size() > 0){ //otherwise it would divide by 0
            avgLifeTime = (avgLifeTime * (double) deadAnimalsNo + freshDeadAnimalsList.stream().mapToDouble(Animal::getAge).sum())
                    / (double) (deadAnimalsNo + freshDeadAnimalsList.size());
            deadAnimalsNo += freshDeadAnimalsList.size();
        }

        SimulationStats simStat = new SimulationStats(
                worldMap_able.getDay(),
                worldMap_able.getAnimalsNo(),
                deadAnimalsNo,
                worldMap_able.getGrassesNo(),
                worldMap_able.getTopGene(),
                worldMap_able.getFieldsWithoutGrassNo(),
                worldMap_able.getFieldsWithoutAnimalsNo(),
                worldMap_able.getEmptyFieldsNo(),
                worldMap_able.getAvgEnergy(),
                avgLifeTime,
                worldMap_able.getAvgNumberOfChildren(),
                worldMap_able.getAvgNumberOfSuccessors()
        );
        simulationStatsList.add(simStat);
    }

    public List<SimulationStats> getSimulationStatsList() {
        return simulationStatsList;
    }

}
