package bomberman.entity.block;

import bomberman.entity.Entity;
import bomberman.image.Sprite;

public class Grass extends Block {

    public Grass(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public boolean collide(Entity e) {
        return true;
    }
}
