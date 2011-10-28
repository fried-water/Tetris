/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.ai;

import tetris.control.HeuristicAI;
import tetris.control.GameState;

/**
 *
 * @author User
 */
public class LowestAI extends HeuristicAI {

    @Override
    protected double heuristic(GameState state) {
        int[][] board = state.getBoard();
        double value = 0;

        for(int y = 0; y < board[0].length; y++) {
            for(int x = 0; x < board.length; x++) {
                if(board[x][y] > 1) {
                    value += y;
                }
            }
        }
        
        return value;
    }

}
