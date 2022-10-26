package bomberman.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import bomberman.GameLoop;
import bomberman.exceptionHandle.GameEx;

public class Panel extends JPanel {

    private GameLoop _game;

    public Panel(Frame frame) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(GameLoop.WIDTH * GameLoop.SCALE, GameLoop.HEIGHT * GameLoop.SCALE));

        try {
            _game = new GameLoop(frame);

            add(_game);

            _game.setVisible(true);

        } catch (GameEx e) {
            e.printStackTrace();
            System.exit(0);
        }
        setVisible(true);
        setFocusable(true);

    }

    public void changeSize() {
        setPreferredSize(new Dimension(GameLoop.WIDTH * GameLoop.SCALE, GameLoop.HEIGHT * GameLoop.SCALE));
        revalidate();
        repaint();
    }

    public GameLoop getGame() {
        return _game;
    }

}
