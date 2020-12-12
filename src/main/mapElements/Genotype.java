package mapElements;

public class Genotype {
    int[] genes;
    Genotype(){
        this.genes = new int[8];
        for(int i = 0; i < 8; i++)
            genes[i] = 1;
    }

    Genotype pickGeno(Animal dad, Animal mom){
        return new Genotype();
    }

    public void randomGeno(){
        for(int i = 8; i < 32; i++)
            this.genes[(int)(Math.floor(Math.random()*(8-0.0000001)))]++;
    }
}
