package bomberman.map;

import bomberman.exceptionHandle.LevelEx;

public interface Leveling {

    public void loadLevel(String path) throws LevelEx;
}
