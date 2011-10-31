/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import tetris.core.Game;

/**
 *
 * @author User
 */
public class KeyController implements KeyListener {
	private Game game;
	
	public KeyController(Game game) {
		this.game = game;
	}
	
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_J) {
        	game.execute(0, Command.LEFT);
        } else if(e.getKeyCode() == KeyEvent.VK_L) {
        	game.execute(0, Command.RIGHT);
        } else if(e.getKeyCode() == KeyEvent.VK_K) {
        	game.execute(0, Command.DOWN);
        } else if(e.getKeyCode() == KeyEvent.VK_I) {
        	game.execute(0, Command.ROTATE_RIGHT);
        } else if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
        	game.execute(0, Command.ROTATE_LEFT);
        } else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
        	game.execute(0, Command.DROP);
        }
        
        if(game.getNumPlayers() == 2) {
	        if(e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
	        	game.execute(1, Command.LEFT);
	        } else if(e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
	        	game.execute(1, Command.RIGHT);
	        } else if(e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
	        	game.execute(1, Command.DOWN);
	        } else if(e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
	        	game.execute(1, Command.ROTATE_RIGHT);
	        } else if(e.getKeyCode() == KeyEvent.VK_UP) {
	        	game.execute(1, Command.ROTATE_LEFT);
	        } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
	        	game.execute(1, Command.DROP);
	        }
        }

        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    public void keyTyped(KeyEvent e) { }
    
    public void keyReleased(KeyEvent e) { }
}
