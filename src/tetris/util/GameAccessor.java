/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.util;

import tetris.core.*;
import tetris.option.Options;

/**
 *
 * @author User
 */
public class GameAccessor {
    private static Game game;

    private GameAccessor() { }

    public synchronized static void generateGame(Options options) {
        game = new Game(options);
    }

    public synchronized static Game getGame() {
        return game;
    }
}
