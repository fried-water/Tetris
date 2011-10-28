/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import tetris.util.GameAccessor;

/**
 *
 * @author User
 */
public class KeyController implements KeyListener {
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_J) {
            GameAccessor.getGame().execute(0, Command.LEFT);
        } else if(e.getKeyCode() == KeyEvent.VK_L) {
            GameAccessor.getGame().execute(0, Command.RIGHT);
        } else if(e.getKeyCode() == KeyEvent.VK_K) {
            GameAccessor.getGame().execute(0, Command.DOWN);
        } else if(e.getKeyCode() == KeyEvent.VK_I) {
            GameAccessor.getGame().execute(0, Command.ROTATE_RIGHT);
        } else if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
            GameAccessor.getGame().execute(0, Command.ROTATE_LEFT);
        } else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            GameAccessor.getGame().execute(0, Command.DROP);
        }
        
        if(e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
            GameAccessor.getGame().execute(1, Command.LEFT);
        } else if(e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
            GameAccessor.getGame().execute(1, Command.RIGHT);
        } else if(e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
            GameAccessor.getGame().execute(1, Command.DOWN);
        } else if(e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
            GameAccessor.getGame().execute(1, Command.ROTATE_RIGHT);
        } else if(e.getKeyCode() == KeyEvent.VK_UP) {
            GameAccessor.getGame().execute(1, Command.ROTATE_LEFT);
        } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            GameAccessor.getGame().execute(1, Command.DROP);
        }

        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    public void keyTyped(KeyEvent e) { }
    
    public void keyReleased(KeyEvent e) { }
}
