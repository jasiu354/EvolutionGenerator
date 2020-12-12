package map;

import mapElements.MapElement;
import mapElements.Vector2d;

import java.util.Vector;

public interface IMap extends MapObserver{

    void place(MapElement e);
    void occupied(Vector2d v);

}
