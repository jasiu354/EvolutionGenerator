package mapElements;

import map.MapObserver;
import java.util.*;
import runAndData.*;

public class Animal extends MapElement{
    Genotype geno;
    int energyLevel;
    Set<Animal> children = new HashSet<>();
    Animal[] parents;
    int id;

    Animal(MapObserver observer){ //adam i ewa
        this.observers.add(observer);
        this.geno = new Genotype();
        this.id = GlobalVariables.animalCounter++;
        this.parents = new Animal[1];
        this.parents[0] = null;
    }

    Animal(MapObserver observer, Animal dad, Animal mom){ //dzieci
        this.observers.add(observer);
        this.geno = new Genotype();
        this.geno.pickGeno(dad,mom);
        this.parents = new Animal[2];
        this.parents[0] = dad;
        this.parents[1] = mom;
    }

    int getEnergyLevel(){
        return this.energyLevel;
    }

    void move(){
        if(this.energyLevel <= GlobalVariables.moveEnergy)
            this.die();
    }

    void die(){
        this.energyLevel = 0;
        super.remove();
    }

    boolean canCopulate(){
        return this.energyLevel>4;
    }


    void copulate(){

    }

    void consume(){

    }

}
