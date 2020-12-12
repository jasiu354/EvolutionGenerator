package map;

import mapElements.*;
import runAndData.GlobalVariables;

import java.util.*;


public class WorldMap extends Jungle implements IMap {

    Map<Vector2d, Set<MapElement>> mapElements = new HashMap<>();
    final int width, height;


    public WorldMap() {
        this.width = GlobalVariables.width;
        this.height = GlobalVariables.height;
        this.lL = new Vector2d( (int)(this.width * (0.5 - GlobalVariables.jungleRatio* 0.5)),
                (int)(this.height * (0.5 - GlobalVariables.jungleRatio* 0.5)));
        this.uR = new Vector2d( (int)(this.width * (0.5 + GlobalVariables.jungleRatio * 0.5)),
                (int)(this.height * ( 0.5 + GlobalVariables.jungleRatio* 0.5)));
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, MapElement e) {

    }

    public void remove(MapElement e) {
        mapElements.remove(e.getPosition());
    }

    public void place(MapElement e) {
        //  this.mapElements.put(e.getPosition(), (Set<MapElement>) e);
    }

    public void occupied(Vector2d v) {

    }

    public void display() {
        for (int j = this.height; j >= -1; j--) {
            for (int i = this.width; i >= -1; i--) {
                if ((j == -1 && i == -1) || (j == this.height && i == this.width) || (j == -1 && i == this.width) || (j == this.height && i == -1))
                    System.out.print('"');
                else if (j == -1 || j == this.height)
                    System.out.print(" _ ");
                else if (i == -1 || i == this.width)
                    System.out.print('|');
                else {
                    if(lL.precedes( new Vector2d(i,j)) && uR.follows(new Vector2d(i,j))) {
                        System.out.print(" j ");
                    }
                    else
                        System.out.print("   ");
                }
            }
            System.out.println();
        }
    }

}
