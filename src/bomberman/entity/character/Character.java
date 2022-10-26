package bomberman.entity.character;

import bomberman.Board;
import bomberman.GameLoop;
import bomberman.entity.MovingEntity;
import bomberman.image.Screen;

public abstract class Character extends MovingEntity {

    protected Board _board;
    protected int _direction = -1;
    protected boolean _alive = true;
    protected boolean _moving = false;
    public int _timeAfter = 80;

    public Character(int x, int y, Board board) {
        _x = x;
        _y = y;
        _board = board;
    }

    @Override
    public abstract void update();

    @Override
    public abstract void render(Screen screen);

    protected abstract void calculateMove();

    protected abstract void move(double xa, double ya);

    public abstract void kill();

    protected abstract void afterKill();

    protected abstract boolean canMove(double x, double y);

    public boolean isAlive() {
        return _alive;
    }

    public boolean isMoving() {
        return _moving;
    }

    public int getDirection() {
        return _direction;
    }

    protected double getXMessage() {
        return (_x * GameLoop.SCALE) + (_sprite.SIZE / 2 * GameLoop.SCALE);
    }

    protected double getYMessage() {
        return (_y* GameLoop.SCALE) - (_sprite.SIZE / 2 * GameLoop.SCALE);
    }

}
