package mapElements;

import java.util.*;
import runAndData.*;
import static map.WorldMap.findPosForChild;

public class Animal extends MapElement{
    Genotype geno;
    int energyLevel, id;
    Set<Animal> children = new HashSet<>();
    Animal[] parents;
    MoveDirection direction;


    public Animal(){ //adam i ewa
        this.geno = new Genotype();
        this.id = GlobalVariables.animalCounter++;
        this.parents = new Animal[1];
        this.parents[0] = null;
        this.position = new Vector2d((int) Math.floor(Math.random()*GlobalVariables.width) ,
                (int)Math.floor(Math.random()*GlobalVariables.height));
        this.geno.randomGeno();
        this.direction = MoveDirection.random();
    }

    public Animal(Animal dad, Animal mom){ //dzieci
        this.geno = new Genotype();
        this.geno.pickGeno(dad,mom);
        this.parents = new Animal[2];
        this.parents[0] = dad;
        this.parents[1] = mom;
        this.id = GlobalVariables.animalCounter++;
        this.position = findPosForChild(dad.getPosition());
    }

    int getEnergyLevel(){
        return this.energyLevel;
    }
    Genotype getGeno(){ return this.geno; }

    public void move(){
        //if(this.energyLevel <= GlobalVariables.moveEnergy) { this.die(); }
        //this.energyLevel -= GlobalVariables.moveEnergy;
        this.direction = turn();
        Vector2d prev = this.position;
        this.position = this.position.add(this.direction.toUnitVector());
        super.positionChanged(prev);

    }

    MoveDirection turn(){
        int x = (int)(Math.floor(Math.random()*(33-0.0000001))), i = 0;
        while(x > 0 && i < 7){
            x -= this.geno.genes[i++];
        }
        return MoveDirection.values()[i];
    }

    void die(){
        this.energyLevel = 0;
        super.remove();
    }

    boolean canCopulate(){ return this.energyLevel>4; }


    void copulate(){

    }

    void consume(){

    }

}
