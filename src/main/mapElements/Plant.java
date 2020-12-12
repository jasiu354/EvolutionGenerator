package mapElements;
import java.util.Random;
import map.WorldMap;
import runAndData.GlobalVariables;

public class Plant extends MapElement {

    public Plant(WorldMap map){
        this.observers.add(map);
    }

    public void pickPlaceInJungle(){
        Random rand = new Random();
        this.position = new Vector2d(rand.nextInt(
                GlobalVariables.u.getX() - GlobalVariables.d.getX()
        ) + GlobalVariables.d.getX(),  rand.nextInt(
                GlobalVariables.u.getY() - GlobalVariables.d.getY()
        ) + GlobalVariables.d.getY());
    }

    public void pickPlaceOutsideJungle(){
        Random rand = new Random();
        int opt = rand.nextInt(4),x = 0,y = 0;
        switch (opt) {
            case 0:
                x = rand.nextInt(GlobalVariables.d.getX());
                y = rand.nextInt(GlobalVariables.height);
            case 1:
                x = rand.nextInt(
                        GlobalVariables.u.getX() - GlobalVariables.d.getX())
                        + GlobalVariables.u.getX();
                y = rand.nextInt(
                        GlobalVariables.height - GlobalVariables.u.getY())
                        + GlobalVariables.u.getY();
            case 2:
                x = rand.nextInt(
                        GlobalVariables.u.getX() - GlobalVariables.d.getX())
                        + GlobalVariables.u.getX();
                y = rand.nextInt(GlobalVariables.d.getY());
            case 3:
                x = rand.nextInt(GlobalVariables.width - GlobalVariables.u.getX())
                        + GlobalVariables.u.getX();
                y = rand.nextInt(GlobalVariables.height);
        }
        this.position = new Vector2d(x,y);
    }
}
