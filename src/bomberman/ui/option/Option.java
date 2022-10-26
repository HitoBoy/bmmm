package bomberman.ui.option;

import javax.swing.JMenuBar;

import bomberman.ui.Frame;

public class Option extends JMenuBar {

    public Option(Frame frame) {
        add( new Game(frame) );
        add( new Slider(frame) );
        add( new Instruction(frame) );
    }

}
