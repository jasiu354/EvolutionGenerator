package runAndData;

import map.WorldMap;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        GlobalVariables.moveEnergy = 100;
        GlobalVariables.height = 20;
        GlobalVariables.jungleRatio = 0.3;
        GlobalVariables.width = 100;
        SimulationEngine sI = new SimulationEngine();
        sI.run();
    }
}