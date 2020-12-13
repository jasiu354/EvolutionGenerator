package runAndData;

import map.WorldMap;
import mapElements.*;
import java.util.*;
import java.util.stream.Collectors;

public class SimulationEngine implements IEngine {

    WorldMap map;

    SimulationEngine(){
        this.map = new WorldMap();
    }

    public void run() {
        startAnimals();
        this.map.display();
        generatePlants();
        System.out.println(GlobalVariables.d + " " + GlobalVariables.u);
        for(int i = 0; i < 10 ; i++) {
            removeDeadAnimals();
            moveAnimals();
            feedAnimals();
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

    public void feedAnimals() {
        Set<Plant> plants = getPlants();
        Set<Animal> animals = getAnimals();
        for(Plant p:plants){
            Set<Animal> luckyAnimals = animals.stream().
                    filter(a -> a.getPosition().equals(p.getPosition())).collect(Collectors.toSet());
            if(!luckyAnimals.isEmpty()){
                luckyAnimals.forEach(a -> a.increaseEnergyLevel((GlobalVariables.plantEnergy/luckyAnimals.size())));
                p.remove();
                p.unregister(this.map);
            }
        }
    }

    public List<Animal> getStrongestAnimals(Set<Animal> animals){
        int max = animals.stream().map(Animal::getEnergyLevel).
                max(Integer::compareTo).orElse(0);
        return animals.stream().filter(a -> a.getEnergyLevel() == max).collect(Collectors.toList());
    }

    public void moveAnimals() {
        getAnimals().forEach(Animal::move);
    }
    public void removeDeadAnimals(){
        getAnimals().forEach(a -> {
            a.die();
            if(a.getEnergyLevel() == 0)
                a.unregister(this.map);
        });
    }
}