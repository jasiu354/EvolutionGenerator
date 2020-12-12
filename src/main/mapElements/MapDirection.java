package mapElements;
public enum MapDirection {
    NORTH,NORTHEAST,EAST,SOUTHEAST,SOUTH, SOUTHWEST,WEST,NORTHWEST;

    public MapDirection next() {
        return (MapDirection.values()[(this.ordinal()+1)%8]);
    }

    public MapDirection previous() {
        return (MapDirection.values()[(this.ordinal()-1)%8]);
    }

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
