package bomberman.map;

import bomberman.GameLoop;

public class Convert {

    public static int pixelToTile(double i) {
        return (int)(i / GameLoop.TILES_SIZE);
    }

    public static int tileToPixel(int i) {
        return i * GameLoop.TILES_SIZE;
    }

    public static int tileToPixel(double i) {
        return (int)(i * GameLoop.TILES_SIZE);
    }


}

