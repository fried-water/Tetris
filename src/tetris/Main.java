/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import tetris.util.ResourceManager;
import tetris.core.Game;
import tetris.gui.TetrisFrame;
import tetris.option.Options;

/**
 *
 * @author User
 */
public class Main {
    public static void main(String[] args) {
    	Game game = new Game(new Options());
        ResourceManager.loadResources();
        new TetrisFrame(game);
        game.start();
    }
}
