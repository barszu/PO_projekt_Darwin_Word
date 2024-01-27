package project.backend.backend.globalViaSimulation;

public class GlobalVariables{ // zmienne globalne są złe
    private int Date = 0; //no of days elapsed

    public int getDate() {
        return this.Date;
    }

    public void addDay() {
        this.Date++;
    }

}
