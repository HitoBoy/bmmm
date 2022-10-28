package bomberman.keyinput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private boolean[] keys = new boolean[120];
    public boolean up, down, left, right, space;

    public void update() {
        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
        space = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_X];
    }

    /**
     * ghi đè phương thức keyTyped.
     */
    @Override
    public void keyTyped(KeyEvent e) {}
    
    /**
     * ghi đè phương thức keyPressed, thay đổi kiểu dữ liệu di chuyển khi giữ phím.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;

    }

    /**
     * ghi đè phương thức keyReleased, thay đổi kiểu dữ liệu di chuyển khi thả phím.
     */
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;

    }

}
