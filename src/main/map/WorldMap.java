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

    public void remove(MapElement e, Vector2d position) {
        if(this.mapElements.containsKey(position)) {
            this.mapElements.get(position).remove(e);
            if(this.mapElements.get(position).isEmpty())
                this.mapElements.remove(position);
        }
    }


    public void place(MapElement e) {
        if(!this.mapElements.containsKey(e.getPosition())) {
            this.mapElements.put(e.getPosition(), new HashSet<>());
        }
        this.mapElements.get(e.getPosition()).add(e);
    }

    public void positionChanged(Vector2d oldPosition, MapElement e) {
        remove(e,oldPosition);
        place(e);
    }

    public boolean occupied(Vector2d v) {
        return this.mapElements.containsKey(v);
    }

    public Vector2d findPosForChild(Vector2d v){
        Random rand = new Random();
        int x = rand.nextInt(8);
        if(occupied(v.add(MoveDirection.values()[x].toUnitVector()))){
            for(int i = 0; i < 8; i++){
                if(!occupied(v.add(MoveDirection.values()[i].toUnitVector()))) {
                    return v.add(MoveDirection.values()[i].toUnitVector());
                }
            }
        }
        return v.add(MoveDirection.values()[x].toUnitVector());
    }

    public Map<Vector2d,Set<MapElement>> getElements(){
        return this.mapElements;
    }

    public int getHeight() { return this.height; }

    public int getWidth() { return this.width; }

    public boolean inJungle(Vector2d v){
        return lL.precedes(v) && uR.follows(v);
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
                    if(inJungle(new Vector2d(i,j))) {
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

    public String buildDisplay() {
        StringBuilder output = new StringBuilder();
        for (int j = this.height; j >= -1; j--) {
            for (int i = -1; i <= this.width; i++) {
                if ((j == -1 && i == -1) || (j == this.height && i == this.width) || (j == -1 && i == this.width) || (j == this.height && i == -1))
                    output.append('"');
                else if (j == -1 || j == this.height)
                    output.append("_");
                else if (i == -1 || i == this.width) {
                    if (i == -1)
                        output.append(j);
                    output.append('|');
                }
                else if(this.mapElements.containsKey(new Vector2d(i,j))) {
                    if(this.mapElements.get(new Vector2d(i,j)).stream().allMatch(a -> a instanceof Animal))
                        output.append("a");
                    else if(this.mapElements.get(new Vector2d(i,j)).stream().allMatch(p -> p instanceof Plant))
                        output.append("p");
                    else
                        output.append("a");
                }
                else{
                    if(inJungle(new Vector2d(i,j))) {
                        output.append("j");
                    }
                    else
                        output.append(" ");
                }
            }
            output.append('\n');
        }
        output.append(" ");
        for(int i = 0; i < GlobalVariables.width; i++)
            output.append(i);
        output.append('\n');
        return output.toString();
    }
}