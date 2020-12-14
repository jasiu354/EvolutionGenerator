package map;

import mapElements.Vector2d;

public abstract class Jungle {
    Vector2d lL;
    Vector2d uR;

    public Vector2d getlL() { return this.lL; }

    public Vector2d getuR() { return this.uR; }

    public int getLowerXJungle(){return getlL().x;}

    public int getLowerYJungle(){return getlL().y;}

    public int getUpperXJungle(){return getuR().x;}

    public int getUpperYJungle(){return getuR().y;}

}
