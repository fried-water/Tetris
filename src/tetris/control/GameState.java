/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.control;

import java.util.ArrayList;
import java.util.List;
import tetris.piece.Piece;

/**
 *
 * @author User
 */
public class GameState {
    private int[][] board;
    
    private Piece current;
    private List<Piece> undoStack;

    public GameState(int[][] board, Piece piece) {
        this.board = new int[board.length][board[0].length];


        for(int y = 0;y < board[0].length;y++) {
            for(int x = 0;x < board.length;x++) {
                if(board[x][y] == 9) {
                    this.board[x][y] = -1;
                } else if(board[x][y] > 0) {
                    this.board[x][y] = 1;
                }
            }
        }

        current = piece.clone();

        undoStack = new ArrayList<Piece>();
    }

    public void revert() {  
        if(undoStack.size() > 0) {
            current = undoStack.get(0);
            undoStack.clear();
        }
    }

    public void undo() {
        if(undoStack.size() > 0) {
            current = undoStack.remove(undoStack.size() - 1);
        }
    }

    public int[][] getBoard() {
        int[][] merged = new int[board.length][board[0].length];

        for(int y = 0;y < board[0].length;y++) {
            for(int x = 0;x < board.length;x++) {
                if(board[x][y] != 0) {
                    merged[x][y] = board[x][y];
                }
            }
        }

        for(int p = 0;p < current.getData()[0].length;p++) {
            for(int i = 0;i < current.getData().length;i++) {
                if((current.getX() + i >= 0 && current.getX() + i < board.length &&
                        current.getY() + p >= 0 && current.getY() + p < board[0].length)) {
                    if(current.getData()[p][i] != 0) {
                        merged[current.getX() + i][current.getY() + p] = 2;
                    }
                }
            }
        }

        return merged;
    }

    public int getNumberOfLines() {
        int[][] res = getBoard();
        int lineCounter = 0;

        for(int y = 0; y < res[0].length; y++) {
            for(int x = 0; x < res.length; x++) {
                if(res[x][y] <= 0) {
                    break;
                } else if(x == res.length - 1) {
                    lineCounter++;
                    break;
                }
            }
        }

        return lineCounter;
    }

    public int[][] getBoardWithLinesRemoved() {
        int[][] res = getBoard();

        for(int y = 0; y < res[0].length; y++) {
            for(int x = 0; x < res.length; x++) {
                if(res[x][y] <= 0) {
                    break;
                } else if(x == res.length - 1) {
                    removeLine(res, y);
                    y--;
                    break;
                }
            }
        }

        return res;
    }

    public boolean simulateLeft() {
        if(canLeft()) {
            undoStack.add(current.clone());
            current.moveLeft();

            return true;
        }

        return false;
    }

    public boolean simulateRight() {
        if(canRight()) {
            undoStack.add(current.clone());
            current.moveRight();

            return true;
        }

        return false;
    }

    public boolean simulateDown() {
        if(canDown()) {
            undoStack.add(current.clone());
            current.moveDown();

            return true;
        }

        return false;
    }

    public boolean simulateRotateLeft() {
        if(canRotateLeft()) {
            undoStack.add(current.clone());
            current.rotateLeft();

            return true;
        }

        return false;
    }

    public boolean simulateRotateRight() {
        if(canRotateRight()) {
            undoStack.add(current.clone());
            current.rotateRight();

            return true;
        }

        return false;
    }

    public void simulateDrop() {
        undoStack.add(current.clone());
        current.drop(board);
    }

    public boolean canLeft() {
        return current.canLeft(board);
    }

    public boolean canRight() {
        return current.canRight(board);
    }

    public boolean canDown() {
        return current.canDown(board);
    }

    public boolean canRotateLeft() {
        return current.canRotateLeft(board);
    }

    public boolean canRotateRight() {
        return current.canRotateRight(board);
    }

    private void removeLine(int[][] board, int line) {
        for(int y = line+1; y < board[0].length; y++) {
            for(int x = 0; x < board.length; x++) {
                board[x][y - 1] = board[x][y];
            }
        }

        for(int x = 0; x < board.length; x++) {
            board[x][board[0].length - 1] = 0;
        }
    }
}
