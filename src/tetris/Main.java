/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import tetris.util.ResourceManager;
import tetris.util.GameAccessor;
import tetris.gui.TetrisFrame;
import tetris.option.Options;

/**
 *
 * @author User
 */
public class Main {
    public static void main(String[] args) {
        ResourceManager.loadResources();
        GameAccessor.generateGame(new Options());
        new TetrisFrame();
        GameAccessor.getGame().start();
    }
}
