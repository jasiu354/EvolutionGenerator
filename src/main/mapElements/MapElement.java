package mapElements;

import map.MapObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class MapElement {

    Vector2d position;
    MapDirection direction;
    List<MapObserver> observers = new ArrayList<>();

    public Vector2d getPosition(){
        return this.position;
    }

    void register(MapObserver observer){
        this.observers.add(observer);
    }

    void unregister(MapObserver observer){
        this.observers.remove(observer);
    }

    void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        this.observers.forEach(observer -> observer.positionChanged(oldPosition, newPosition,this));
    }

    void remove(){
        this.observers.forEach(observer -> observer.remove(this));
    }
}
