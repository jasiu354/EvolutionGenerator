package mapElements;

import runAndData.GlobalVariables;

import java.util.*;

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
        this.energyLevel = GlobalVariables.startEnergy;
    }

    public Animal(Animal dad, Animal mom, Vector2d childPos){ //dzieci
        this.geno = new Genotype();
        this.geno = this.geno.pickGeno(dad,mom);
        this.parents = new Animal[2];
        this.parents[0] = dad;
        this.parents[1] = mom;
        this.id = GlobalVariables.animalCounter++;
        this.position = childPos;
        this.energyLevel = dad.getEnergyLevel()/4 + mom.getEnergyLevel()/4;
    }

    public int getEnergyLevel(){
        return this.energyLevel;
    }

    public void increaseEnergyLevel(int energy){ this.energyLevel += energy; }

    Genotype getGeno(){ return this.geno; }

    public void move(){
        this.energyLevel -= GlobalVariables.moveEnergy;
        this.direction = turn();
        Vector2d prev = this.position;
        this.position = this.position.add(this.direction.toUnitVector());
        super.positionChanged(prev);

    }

    MoveDirection turn(){
        Random rand = new Random();
        int x = rand.nextInt(32), i = 0;
        while(x > 0 && i < 7){
            x -= this.geno.genes[i++];
        }
        return MoveDirection.values()[i];
    }

    public void die(){
        if(this.energyLevel <= 0) {
            this.energyLevel = 0;
            super.remove();
        }
    }

    public boolean canCopulate(){ return this.energyLevel> 0.5*GlobalVariables.startEnergy; }

}
