package bomberman.ui.option;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import bomberman.ui.Frame;
import bomberman.GameLoop;
import bomberman.ui.Info;

public class Instruction extends JMenu {

    public Instruction(Frame frame)  {
        super("Instruction");
        JMenuItem instructions = new JMenuItem("How to play");
        instructions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        instructions.addActionListener(new MenuActionListener(frame));
        add(instructions);
        JMenuItem about = new JMenuItem("About");
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        about.addActionListener(new MenuActionListener(frame));
        add(about);

    }

    class MenuActionListener implements ActionListener {
        public Frame _frame;
        public MenuActionListener(Frame frame) {
            _frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand().equals("How to play")) {
                new Info(_frame, "How to Play", "Movement: W,A,S,D or UP,DOWN, RIGHT, LEFT\nPut Bombs: SPACE, X", JOptionPane.QUESTION_MESSAGE);
            }

            if(e.getActionCommand().equals("About")) {
                new Info(_frame, "About", "\n Nhom 5: Nguyen Viet Hoang, Nguyen Hoang Hao, Le Minh Hoang\n Bai tap lon OOP", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }
}
