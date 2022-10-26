package bomberman.entity.block;

import bomberman.Board;
import bomberman.entity.Entity;
import bomberman.entity.character.Player;
import bomberman.image.Sprite;

public class Gate extends Block {

    protected Board _board;

    public Gate(int x, int y, Board board, Sprite sprite) {
        super(x, y, sprite);
        _board = board;
    }

    @Override
    public boolean collide(Entity e) {

        if(e instanceof Player ) {

            if(_board.detectNoEnemies() == false)
                return false;

            if(e.getXTile() == getX() && e.getYTile() == getY()) {
                if(_board.detectNoEnemies())
                    _board.nextLevel();
            }

            return true;
        }

        return false;
    }

}
