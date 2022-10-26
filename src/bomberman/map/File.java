package bomberman.map;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;



import bomberman.Board;
import bomberman.GameLoop;
import bomberman.entity.HiddenEntity;
import bomberman.entity.character.Player;
import bomberman.entity.character.enemy.Balloom;
import bomberman.entity.character.enemy.Doll;
import bomberman.entity.character.enemy.Kondoria;
import bomberman.entity.character.enemy.Minvo;
import bomberman.entity.character.enemy.Oneal;
import bomberman.entity.block.Grass;
import bomberman.entity.block.Gate;
import bomberman.entity.block.Wall;
import bomberman.entity.block.Obstacle;
import bomberman.entity.block.powerup.BombBonus;
import bomberman.entity.block.powerup.RadiusBonus;
import bomberman.entity.block.powerup.SpeedBonus;
import bomberman.exceptionHandle.LevelEx;
import bomberman.image.Screen;
import bomberman.image.Sprite;

public class File extends Level {

    public File(String path, Board board) throws LevelEx {
        super(path, board);
    }

    @Override
    public void loadLevel(String path) throws LevelEx {
        try {
            java.io.File a = new java.io.File("res/"+path);
            URL absPath = new URL("file:"+a.getAbsolutePath());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(absPath.openStream()));

            String data = in.readLine();
            StringTokenizer tokens = new StringTokenizer(data);

            _level = Integer.parseInt(tokens.nextToken());
            _height = Integer.parseInt(tokens.nextToken());
            _width = Integer.parseInt(tokens.nextToken());
            _lineTiles = new String[_height];

            for(int i = 0; i < _height; ++i) {
                _lineTiles[i] = in.readLine().substring(0, _width);
            }

            in.close();
        } catch (IOException e) {
            throw new LevelEx("Error loading level " + path, e);
        }
    }

    @Override
    public void createEntities() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                addLevelEntity( _lineTiles[y].charAt(x), x, y );
            }
        }
    }

    public void addLevelEntity(char c, int x, int y) {
        int pos = x + y * getWidth();

        switch(c) { // TODO: minimize this method
            case '#':
                _board.addEntitie(pos, new Wall(x, y, Sprite.wall));
                break;
            case 'b':
                HiddenEntity layer = new HiddenEntity(x, y,
                        new Grass(x ,y, Sprite.grass),
                        new Obstacle(x ,y, Sprite.brick));

                if(_board.isPowerupUsed(x, y, _level) == false) {
                    layer.addBeforeTop(new BombBonus(x, y, _level, Sprite.powerup_bombs));
                }

                _board.addEntitie(pos, layer);
                break;
            case 's':
                layer = new HiddenEntity(x, y,
                        new Grass(x ,y, Sprite.grass),
                        new Obstacle(x ,y, Sprite.brick));

                if(_board.isPowerupUsed(x, y, _level) == false) {
                    layer.addBeforeTop(new SpeedBonus(x, y, _level, Sprite.powerup_speed));
                }

                _board.addEntitie(pos, layer);
                break;
            case 'f':
                layer = new HiddenEntity(x, y,
                        new Grass(x ,y, Sprite.grass),
                        new Obstacle(x ,y, Sprite.brick));

                if(_board.isPowerupUsed(x, y, _level) == false) {
                    layer.addBeforeTop(new RadiusBonus(x, y, _level, Sprite.powerup_flames));
                }

                _board.addEntitie(pos, layer);
                break;
            case '*':
                _board.addEntitie(pos, new HiddenEntity(x, y,
                        new Grass(x ,y, Sprite.grass),
                        new Obstacle(x ,y, Sprite.brick)) );
                break;
            case 'x':
                _board.addEntitie(pos, new HiddenEntity(x, y,
                        new Grass(x ,y, Sprite.grass),
                        new Gate(x ,y, _board, Sprite.portal),
                        new Obstacle(x ,y, Sprite.brick)) );
                break;
            case ' ':
                _board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
                break;
            case 'p':
                _board.addMob( new Player(Convert.tileToPixel(x), Convert.tileToPixel(y) + GameLoop.TILES_SIZE, _board) );
                Screen.setOffset(0, 0);

                _board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
                break;
            //Enemies
            case '1':
                _board.addMob( new Balloom(Convert.tileToPixel(x), Convert.tileToPixel(y) + GameLoop.TILES_SIZE, _board));
                _board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
                break;
            case '2':
                _board.addMob( new Oneal(Convert.tileToPixel(x), Convert.tileToPixel(y) + GameLoop.TILES_SIZE, _board));
                _board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
                break;
            case '3':
                _board.addMob( new Doll(Convert.tileToPixel(x), Convert.tileToPixel(y) + GameLoop.TILES_SIZE, _board));
                _board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
                break;
            case '4':
                _board.addMob( new Minvo(Convert.tileToPixel(x), Convert.tileToPixel(y) + GameLoop.TILES_SIZE, _board));
                _board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
                break;
            case '5':
                _board.addMob( new Kondoria(Convert.tileToPixel(x), Convert.tileToPixel(y) + GameLoop.TILES_SIZE, _board));
                _board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
                break;
            default:
                _board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
                break;
        }
    }

}
