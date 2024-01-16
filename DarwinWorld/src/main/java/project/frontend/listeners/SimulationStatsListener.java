package project.frontend.listeners;

import javafx.application.Platform;
import javafx.scene.control.Label;
import project.backend.backend.listeners.MapChangeListener;
import project.backend.backend.model.maps.WorldMap_able;
import project.backend.backend.model.sprites.Animal;
import project.frontend.listeners.listenerUtil.SimulationStats;
import project.frontend.listeners.listenerUtil.SimulationStatsBuilder;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class SimulationStatsListener implements MapChangeListener {

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

    @Deprecated
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
    public void mapChanged(WorldMap_able worldMap_able, String message) {
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

                    String text = simulationStatsField.get(simulationStats).toString();
                    label.setText(text);

                    simulationStatsField.setAccessible(false);
                }
                field.setAccessible(false);
            } catch (NullPointerException ignored){//field is null
            } catch (Exception e){
                System.out.println("Error while updating labels -> " + e.getMessage());
            }
        }
    }

    private void addNewStat(WorldMap_able worldMap_able){
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

    public void dupa(){
        new SimulationStatsListener().toBuild().build();
    }


}
