package project.frontend.listeners.listenerUtil;

import javafx.scene.control.Label;
import project.frontend.listeners.SimulationStatsListener;

public class SimulationStatsBuilder {
    private Label DayLabel;
    private Label AnimalsNoLabel;
    private Label DeadAnimalsNoLabel;
    private Label PlantsNoLabel;
    private Label TopGeneLabel;
    private Label FieldsWithoutGrassNoLabel;
    private Label FieldsWithoutAnimalsNoLabel;
    private Label EmptyFieldsNoLabel;
    private Label AvgEnergyLabel;
    private Label AvgLifeTimeLabel;
    private Label AvgNumberOfChildrenLabel;
    private Label AvgNumberOfSuccessorsLabel;

    public SimulationStatsBuilder setDayLabel(Label DayLabel) {
        this.DayLabel = DayLabel;
        return this;
    }

    public SimulationStatsBuilder setAnimalsNoLabel(Label AnimalsNoLabel) {
        this.AnimalsNoLabel = AnimalsNoLabel;
        return this;
    }

    public SimulationStatsBuilder setDeadAnimalsNoLabel(Label DeadAnimalsNoLabel) {
        this.DeadAnimalsNoLabel = DeadAnimalsNoLabel;
        return this;
    }

    public SimulationStatsBuilder setPlantsNoLabel(Label PlantsNoLabel) {
        this.PlantsNoLabel = PlantsNoLabel;
        return this;
    }

    public SimulationStatsBuilder setTopGeneLabel(Label TopGeneLabel) {
        this.TopGeneLabel = TopGeneLabel;
        return this;
    }

    public SimulationStatsBuilder setFieldsWithoutGrassNoLabel(Label FieldsWithoutGrassNoLabel) {
        this.FieldsWithoutGrassNoLabel = FieldsWithoutGrassNoLabel;
        return this;
    }

    public SimulationStatsBuilder setFieldsWithoutAnimalsNoLabel(Label FieldsWithoutAnimalsNoLabel) {
        this.FieldsWithoutAnimalsNoLabel = FieldsWithoutAnimalsNoLabel;
        return this;
    }

    public SimulationStatsBuilder setEmptyFieldsNoLabel(Label EmptyFieldsNoLabel) {
        this.EmptyFieldsNoLabel = EmptyFieldsNoLabel;
        return this;
    }

    public SimulationStatsBuilder setAvgEnergyLabel(Label AvgEnergyLabel) {
        this.AvgEnergyLabel = AvgEnergyLabel;
        return this;
    }

    public SimulationStatsBuilder setAvgLifeTimeLabel(Label AvgLifeTimeLabel) {
        this.AvgLifeTimeLabel = AvgLifeTimeLabel;
        return this;
    }

    public SimulationStatsBuilder setAvgNumberOfChildrenLabel(Label AvgNumberOfChildrenLabel) {
        this.AvgNumberOfChildrenLabel = AvgNumberOfChildrenLabel;
        return this;
    }

    public SimulationStatsBuilder setAvgNumberOfSuccessorsLabel(Label AvgNumberOfSuccessorsLabel) {
        this.AvgNumberOfSuccessorsLabel = AvgNumberOfSuccessorsLabel;
        return this;
    }

    public SimulationStatsListener build() {
        return new SimulationStatsListener(
                DayLabel,
                AnimalsNoLabel,
                DeadAnimalsNoLabel,
                PlantsNoLabel,
                TopGeneLabel,
                FieldsWithoutGrassNoLabel,
                FieldsWithoutAnimalsNoLabel,
                EmptyFieldsNoLabel,
                AvgEnergyLabel,
                AvgLifeTimeLabel,
                AvgNumberOfChildrenLabel,
                AvgNumberOfSuccessorsLabel
        );
    }
}
