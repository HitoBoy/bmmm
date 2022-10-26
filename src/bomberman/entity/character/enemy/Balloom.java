package bomberman.entity.character.enemy;


import bomberman.Board;
import bomberman.GameLoop;
import bomberman.entity.character.enemy.ai.AILow;
import bomberman.image.Sprite;

public class Balloom extends Enemy {
	
	
	public Balloom(int x, int y, Board board) {
		super(x, y, board, Sprite.balloom_dead, GameLoop.getPlayerSpeed() / 2, 100);
		
		_sprite = Sprite.balloom_left1;
		
		_ai = new AILow();
		_direction = _ai.calculateDirection();
	}
	
	/*
	|--------------------------------------------------------------------------
	| Mob Sprite
	|--------------------------------------------------------------------------
	 */
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
					_sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animate, 60);
				break;
			case 2:
			case 3:
					_sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 60);
				break;
		}
	}
}
