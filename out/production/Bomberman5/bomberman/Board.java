package bomberman;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bomberman.entity.Entity;
import bomberman.entity.Text;
import bomberman.entity.bomb.Bomb;
import bomberman.entity.bomb.Boom;
import bomberman.entity.character.Character;
import bomberman.entity.character.Player;
import bomberman.entity.block.powerup.PowerUp;
import bomberman.exceptionHandle.LevelEx;
import bomberman.image.Rendering;
import bomberman.image.Screen;
import bomberman.keyinput.Keyboard;
import bomberman.map.File;
import bomberman.map.Level;

public class Board implements Rendering {

    public int width, height;
    protected Level level;
    protected GameLoop name;
    protected Keyboard input;
    protected Screen screen;

    public Entity[] entityList;
    public List<Character> characterList = new ArrayList<Character>();
    protected List<Bomb> bombList = new ArrayList<Bomb>();
    private List<Text> textList = new ArrayList<Text>();

    private int diffScreen = -1;

    private int timeLeft = GameLoop.TIME;
    private int point = GameLoop.POINTS;
    private int lives = GameLoop.LIVES;

    public Board(GameLoop game, Keyboard input, Screen screen) {
        name = game;
        this.input = input;
        this.screen = screen;

        changeLevel(1);
    }

    @Override
    public void update() {
        if( name.isPaused() ) return;

        updateEntities();
        updateCharacter();
        updateBombs();
        updateMessages();
        detectEndGame();

        for (int i = 0; i < characterList.size(); i++) {
            Character a = characterList.get(i);
            if(((Entity)a).isRemoved()) characterList.remove(i);
        }
    }


    @Override
    public void render(Screen screen) {
        if( name.isPaused() ) return;


        int x0 = Screen.xOffset >> 4;
        int x1 = (Screen.xOffset + screen.getWidth() + GameLoop.TILES_SIZE) / GameLoop.TILES_SIZE; //
        int y0 = Screen.yOffset >> 4;
        int y1 = (Screen.yOffset + screen.getHeight()) / GameLoop.TILES_SIZE;

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                entityList[x + y * level.getWidth()].render(screen);
            }
        }

        renderBombs(screen);
        renderCharacter(screen);

    }

    public void newGame() {
        resetProperties();
        changeLevel(1);
    }

    @SuppressWarnings("static-access")
    private void resetProperties() {
        point = GameLoop.POINTS;
        lives = GameLoop.LIVES;
        Player._powerups.clear();
        name.bombRate = 1;
        name.playerSpeed = 1.0;
        name.bombRadius = 1;


    }

    public void restartLevel() {
        changeLevel(level.getLevel());
    }

    public void nextLevel() {
        changeLevel(level.getLevel() + 1);
    }

    public void changeLevel(int level) {
        timeLeft = GameLoop.TIME;
        diffScreen = 2;
        name.resetScreenDelay();
        name.pause();
        characterList.clear();
        bombList.clear();
        textList.clear();

        try {
            this.level = new File("levels/Level" + level + ".txt", this);
            entityList = new Entity[this.level.getHeight() * this.level.getWidth()];

            this.level.createEntities();
        } catch (LevelEx e) {
            endGame();
        }
    }

    public void changeLevelByCode(String str) {
        int i = level.validCode(str);

        if(i != -1) changeLevel(i + 1);
    }

    public boolean isPowerupUsed(int x, int y, int level) {
        PowerUp p;
        for (int i = 0; i < Player._powerups.size(); i++) {
            p = Player._powerups.get(i);
            if(p.getX() == x && p.getY() == y && level == p.getLevel())
                return true;
        }

        return false;
    }

    protected void detectEndGame() {
        if(timeLeft <= 0)
            restartLevel();
    }

    public void endGame() {
        diffScreen = 1;
        name.resetScreenDelay();
        name.pause();
    }

    public boolean detectNoEnemies() {
        int total = 0;
        for (int i = 0; i < characterList.size(); i++) {
            if(characterList.get(i) instanceof Player == false)
                ++total;
        }

        return total == 0;
    }

    public void gamePause() {
        name.resetScreenDelay();
        if(diffScreen <= 0)
            diffScreen = 3;
        name.pause();
    }

    public void gameResume() {
        name.resetScreenDelay();
        diffScreen = -1;
        name.run();
    }

    public void drawScreen(Graphics g) {
        switch (diffScreen) {
            case 1:
                screen.drawEndGame(g, point, level.getActualCode());
                break;
            case 2:
                screen.drawChangeLevel(g, level.getLevel());
                break;
            case 3:
                screen.drawPaused(g);
                break;
        }
    }


    public Entity getEntity(double x, double y, Character m) {

        Entity res = null;

        res = getExplosionAt((int)x, (int)y);
        if( res != null) return res;

        res = getBombAt(x, y);
        if( res != null) return res;

        res = getCharacterNotIn((int)x, (int)y, m);
        if( res != null) return res;

        res = getEntityAt((int)x, (int)y);

        return res;
    }

    public List<Bomb> getBombs() {
        return bombList;
    }

    public Bomb getBombAt(double x, double y) {
        Iterator<Bomb> bs = bombList.iterator();
        Bomb b;
        while(bs.hasNext()) {
            b = bs.next();
            if(b.getX() == (int)x && b.getY() == (int)y)
                return b;
        }

        return null;
    }

    public Character getCharacterIn(double x, double y) {
        Iterator<Character> itr = characterList.iterator();

        Character cur;
        while(itr.hasNext()) {
            cur = itr.next();

            if(cur.getXTile() == x && cur.getYTile() == y)
                return cur;
        }

        return null;
    }

    public Player getPlayer() {
        Iterator<Character> itr = characterList.iterator();

        Character cur;
        while(itr.hasNext()) {
            cur = itr.next();

            if(cur instanceof Player)
                return (Player) cur;
        }

        return null;
    }

    public Character getCharacterNotIn(int x, int y, Character a) {
        Iterator<Character> itr = characterList.iterator();

        Character cur;
        while(itr.hasNext()) {
            cur = itr.next();
            if(cur == a) {
                continue;
            }

            if(cur.getXTile() == x && cur.getYTile() == y) {
                return cur;
            }

        }

        return null;
    }

    public Boom getExplosionAt(int x, int y) {
        Iterator<Bomb> bs = bombList.iterator();
        Bomb b;
        while(bs.hasNext()) {
            b = bs.next();

            Boom e = b.explosionAt(x, y);
            if(e != null) {
                return e;
            }

        }

        return null;
    }

    public Entity getEntityAt(double x, double y) {
        return entityList[(int)x + (int)y * level.getWidth()];
    }

    public void addEntity(int pos, Entity e) {
        entityList[pos] = e;
    }

    public void addCharacter(Character e) {
        characterList.add(e);
    }

    public void addBomb(Bomb e) {
        bombList.add(e);
    }

    public void addMessage(Text e) {
        textList.add(e);
    }

    /*
    |--------------------------------------------------------------------------
    | Renders
    |--------------------------------------------------------------------------
     */
    protected void renderEntities(Screen screen) {
        for (int i = 0; i < entityList.length; i++) {
            entityList[i].render(screen);
        }
    }

    protected void renderCharacter(Screen screen) {
        Iterator<Character> itr = characterList.iterator();

        while(itr.hasNext())
            itr.next().render(screen);
    }

    protected void renderBombs(Screen screen) {
        Iterator<Bomb> itr = bombList.iterator();

        while(itr.hasNext())
            itr.next().render(screen);
    }

    public void renderMessages(Graphics g) {
        Text m;
        for (int i = 0; i < textList.size(); i++) {
            m = textList.get(i);
            g.setColor(m.getColor());
            g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
            g.drawString(m.getMessage(), (int)m.getX() - Screen.xOffset  * GameLoop.SCALE, (int)m.getY());
        }
    }

    protected void updateEntities() {
        if( name.isPaused() ) return;
        for (int i = 0; i < entityList.length; i++) {
            entityList[i].update();
        }
    }

    protected void updateCharacter() {
        if( name.isPaused() ) return;
        Iterator<Character> itr = characterList.iterator();

        while(itr.hasNext() && !name.isPaused())
            itr.next().update();
    }

    protected void updateBombs() {
        if( name.isPaused() ) return;
        Iterator<Bomb> itr = bombList.iterator();

        while(itr.hasNext())
            itr.next().update();
    }

    protected void updateMessages() {
        if( name.isPaused() ) return;
        Text m;
        int left = 0;
        for (int i = 0; i < textList.size(); i++) {
            m = textList.get(i);
            left = m.getDuration();

            if(left > 0)
                m.setDuration(--left);
            else
                textList.remove(i);
        }
    }


    public Keyboard getInput() {
        return input;
    }

    public Level getLevel() {
        return level;
    }

    public GameLoop getGame() {
        return name;
    }

    public int getShow() {
        return diffScreen;
    }

    public void setShow(int i) {
        diffScreen = i;
    }

    public int getTime() {
        return timeLeft;
    }

    public int getLives() {
        return lives;
    }

    public int subtractTime() {
        if(name.isPaused())
            return this.timeLeft;
        else
            return this.timeLeft--;
    }

    public int getPoints() {
        return point;
    }

    public void addPoints(int points) {
        this.point += points;
    }

    public void addLives(int lives) {
        this.lives += lives;
    }

    public int getWidth() {
        return level.getWidth();
    }

    public int getHeight() {
        return level.getHeight();
    }

}

