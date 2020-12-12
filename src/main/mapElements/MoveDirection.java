package mapElements;

import java.util.Random;

public enum MoveDirection {
    NORTH,NORTHEAST,EAST,SOUTHEAST,SOUTH, SOUTHWEST,WEST,NORTHWEST;

    public MoveDirection next() {
        return (MoveDirection.values()[(this.ordinal()+1)%8]);
    }

    public MoveDirection previous() {
        return (MoveDirection.values()[(this.ordinal()-1)%8]);
    }

    public static MoveDirection random(){ return MoveDirection.values()[ new Random().nextInt(8)];}

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
            case EAST -> new Vector2d(1, 0);
            case NORTHEAST -> new Vector2d(1, 1);
            case NORTHWEST -> new Vector2d(-1, 1);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case NORTHWEST -> "Północny zachód";
            case NORTH -> "Północ";
            case SOUTHEAST -> "Południowy wschód";
            case SOUTH -> "Południe";
            case SOUTHWEST -> "Południowy zachód";
            case WEST -> "Zachód";
            case EAST -> "Wschód";
            case NORTHEAST -> "Północny wschód";
        };
    }

}
