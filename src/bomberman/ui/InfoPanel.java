package bomberman.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import bomberman.GameLoop;

public class InfoPanel extends JPanel {

    private JLabel timeLabel;
    private JLabel pointsLabel;
    private JLabel livesLabel;

    public InfoPanel(GameLoop game) {
        setLayout(new GridLayout());
        StringBuilder time1 = new StringBuilder();
        int t1 = game.getBoard().getTime()/60;
        int t2 = game.getBoard().getTime()-t1*60;
        time1.append(t1);
        time1.append(":");
        if(t2<10) {
            time1.append("0");
        }
        time1.append(t2);
        timeLabel = new JLabel("Time: " + time1);
        timeLabel.setForeground(Color.green);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(timeLabel);

        livesLabel = new JLabel("Lives left: " + game.getBoard().getLives());
        livesLabel.setForeground(Color.green);
        livesLabel.setHorizontalAlignment(JLabel.CENTER);
        add(livesLabel);

        pointsLabel = new JLabel("Score: " + game.getBoard().getPoints());
        pointsLabel.setForeground(Color.green);
        pointsLabel.setHorizontalAlignment(JLabel.CENTER);
        add(pointsLabel);

        setBackground(Color.gray);
        setPreferredSize(new Dimension(0, 25));
    }

    public void setTime(int t) {
        StringBuilder time = new StringBuilder();
        int t1 = t/60;
        int t2 = t-t1*60;
        time.append(t1);
        time.append(":");
        if(t2<10) {
            time.append("0");
        }
        time.append(t2);
        timeLabel.setText("Time: " + time);
    }

    public void setLives(int t) {
        livesLabel.setText("Lives left: " + t);

    }

    public void setPoints(int t) {
        pointsLabel.setText("Score: " + t);
    }

}
