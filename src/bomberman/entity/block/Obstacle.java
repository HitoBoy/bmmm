package bomberman.entity.block;

import bomberman.entity.Entity;
import bomberman.entity.bomb.UnusualBoom;
import bomberman.entity.character.enemy.Kondoria;
import bomberman.image.Screen;
import bomberman.image.Sprite;
import bomberman.map.Convert;

public class Obstacle extends Destroyable {

    public Obstacle(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Screen screen) {
        int x = Convert.tileToPixel(_x);
        int y = Convert.tileToPixel(_y);

        if(_destroyed) {
            _sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);

            screen.renderEntityWithBelowSprite(x, y, this, _belowSprite);
        }
        else
            screen.renderEntity( x, y, this);
    }

    @Override
    public boolean collide(Entity e) {

        if(e instanceof UnusualBoom)
            destroy();

        if(e instanceof Kondoria)
            return true;

        return false;
    }


}
