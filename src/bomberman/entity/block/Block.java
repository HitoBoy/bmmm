package bomberman.entity.block;

import bomberman.entity.Entity;
import bomberman.image.Screen;
import bomberman.image.Sprite;
import bomberman.map.Convert;

public abstract class Block extends Entity {


    public Block(int x, int y, Sprite sprite) {
        _x = x;
        _y = y;
        _sprite = sprite;
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity( Convert.tileToPixel(_x), Convert.tileToPixel(_y), this);
    }

    @Override
    public void update() {}
}