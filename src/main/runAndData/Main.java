package runAndData;

public class Main {

    public static void main(String[] args) {
        GlobalVariables.moveEnergy = 10;
        GlobalVariables.startEnergy = 100;
        GlobalVariables.height = 10;
        GlobalVariables.jungleRatio = 0.3;
        GlobalVariables.width = 10;
        GlobalVariables.startAnimals = 4;
        SimulationEngine sI = new SimulationEngine();
        sI.run();
    }
}