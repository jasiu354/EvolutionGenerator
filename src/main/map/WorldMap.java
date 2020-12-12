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
        GlobalVariables.u = this.uR;
        GlobalVariables.d = this.lL;
    }

    public void remove(MapElement e) {
        mapElements.remove(e.getPosition());
    }

    public void place(MapElement e) {
        if(!this.mapElements.containsKey(e.getPosition())) {
            this.mapElements.put(e.getPosition(), new HashSet<>());
        }
        this.mapElements.get(e.getPosition()).add(e);
    }

    public void positionChanged(Vector2d oldPosition, MapElement e) {
        this.mapElements.get(oldPosition).remove(e);
        if(this.mapElements.get(oldPosition).isEmpty())
            this.mapElements.remove(oldPosition);
        place(e);
    }

    public void occupied(Vector2d v) {

    }

    public static Vector2d findPosForChild(Vector2d v){ return v; }

    public Map<Vector2d,Set<MapElement>> getElements(){
        return this.mapElements;
    }

    public void display() {
        for (int j = this.height; j >= -1; j--) {
            for (int i = -1; i <= this.width; i++) {
                if ((j == -1 && i == -1) || (j == this.height && i == this.width) || (j == -1 && i == this.width) || (j == this.height && i == -1))
                    System.out.print('"');
                else if (j == -1 || j == this.height)
                    System.out.print(" _ ");
                else if (i == -1 || i == this.width) {
                    if (i == -1)
                        System.out.print(j);
                    System.out.print('|');
                }
                else if(this.mapElements.containsKey(new Vector2d(i,j))) {
                    if(this.mapElements.get(new Vector2d(i,j)).stream().allMatch(a -> a instanceof Animal))
                        System.out.print(" a ");
                    else if(this.mapElements.get(new Vector2d(i,j)).stream().allMatch(p -> p instanceof Plant))
                        System.out.print( " p ");
                    else
                        System.out.print(" a ");
                }
                else{
                    if(lL.precedes( new Vector2d(i,j)) && uR.follows(new Vector2d(i,j))) {
                        System.out.print(" j ");
                    }
                    else
                        System.out.print("   ");
                }
            }
            System.out.println();
        }
        System.out.print(" ");
        for(int i = 0; i < GlobalVariables.width; i++)
            System.out.print(" " + i + " ");
        System.out.println();
    }

}