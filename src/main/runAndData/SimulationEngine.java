package runAndData;

import map.WorldMap;
import mapElements.*;
import java.util.*;

public class SimulationEngine implements IEngine {

    WorldMap map;

    SimulationEngine(){
        this.map = new WorldMap();
    }

    public void run() {
        startAnimals();
        this.map.display();
        generatePlants();
        for(int i = 0; i < 10 ; i++) {
            moveAnimals();
            generatePlants();
            System.out.println();
            this.map.display();
        }
    }

    public void startAnimals() {
        for(int i = 0; i < GlobalVariables.startAnimals; i++){
            Animal startingAnimal = new Animal();
            startingAnimal.register(this.map);
            this.map.place(startingAnimal);
        }
    }


    public Set<Animal> getAnimals(){
        Set<Animal> animals = new HashSet<>();
        for (Map.Entry<Vector2d,Set<MapElement>> entry : this.map.getElements().entrySet()){
            for (MapElement x : entry.getValue()) {
                if (x instanceof Animal)
                    animals.add((Animal) x);
            }
        }
        return animals;
    }

    public Set<Plant> getPlants(){
        Set<Plant> plants = new HashSet<>();
        for (Map.Entry<Vector2d,Set<MapElement>> entry : this.map.getElements().entrySet()){
            for (MapElement x : entry.getValue()) {
                if (x instanceof Plant)
                    plants.add((Plant) x);
            }
        }
        return plants;
    }

    public void generatePlants() {
        Plant in = new Plant(this.map), out = new Plant(this.map);
        boolean i = true, o = true;
        in.pickPlaceInJungle();
        out.pickPlaceOutsideJungle();
        for (Plant p : getPlants()) {
            if (p.getPosition().equals(in.getPosition()))
                i = false;
            if (p.getPosition().equals(out.getPosition()))
                o = false;
        }
        if (i) {
            in.register(this.map);
            this.map.place(in);
        }
        if (o){
            out.register(this.map);
            this.map.place(out);
        }
    }


    public void copulation() {

    }

    public void consummation() {

    }

    public void moveAnimals() {
       // this.getAnimals().forEach(a -> System.out.println(a.getPosition()));
        getAnimals().forEach(Animal::move);
    }
}