package bomberman.entity.block.powerup;

import bomberman.GameLoop;
import bomberman.entity.Entity;
import bomberman.entity.character.Player;
import bomberman.image.Sprite;

public class RadiusBonus extends PowerUp {

    public RadiusBonus(int x, int y, int level, Sprite sprite) {
        super(x, y, level, sprite);
    }

    @Override
    public boolean collide(Entity e) {

        if(e instanceof Player) {
            ((Player) e).addPowerup(this);
            remove();
            return true;
        }

        return false;
    }

    @Override
    public void setValues() {
        _active = true;
        GameLoop.addBombRadius(1);
    }



}
