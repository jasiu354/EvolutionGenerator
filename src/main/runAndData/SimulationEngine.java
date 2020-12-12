package runAndData;

import map.WorldMap;

public class SimulationEngine implements IEngine {

    WorldMap map;


    SimulationEngine(){
        this.map = new WorldMap();
    }

    public void run() {
        this.map.display();
    }

    public void generateAnimals() {

    }

    public void generatePlants() {

    }

    public void copulation() {

    }

    public void consummation() {

    }

    public void moveAnimals() {

    }
}