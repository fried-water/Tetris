/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.piece;


/**
 *
 * @author User
 */
public class Piece {
    private final static int NUM_ORIENTATIONS = 4;

    private final PieceDef def;
    private int orientation;
    private int x, y;

    public Piece(PieceDef def, int x, int y) {
        this.def = def;
        this.x = x;
        this.y = y;

        orientation = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[][] getData(int orientation) {
        return def.get(orientation);
    }

    public int[][] getData() {
        return def.get(orientation);
    }

    public int getNum() {
        return def.getNum();
    }

    public void rotateLeft() {
        orientation = (orientation + NUM_ORIENTATIONS - 1) % NUM_ORIENTATIONS;
    }

    public void rotateRight() {
        orientation = (orientation + 1) % NUM_ORIENTATIONS;
    }

    public void moveDown() {
        y++;
    }

    public void moveRight() {
        x++;
    }

    public void moveLeft() {
        x--;
    }

    public void drop(int[][] board) {
        int spacesDown = howManyDown(board);

        for(int i=0;i< spacesDown;i++) {
            moveDown();
        }
    }

    public boolean canStick(int[][] board) {
        int[][] pieceArray = def.get(orientation);
        boolean successful = true;

        for(int i = 0;i < pieceArray.length;i++) {
            for(int p = 0;p < pieceArray[0].length;p++) {
                if((x + i >= 0 && x + i < board.length &&
                        y + p >= 0 && y + p < board[0].length)) {
                    if(pieceArray[p][i] != 0 && board[x + i][y + p] != 0) {
                        successful = false;
                    }
                }
            }
        }

        return successful;
    }

    public void stick(int[][] board) {
        int[][] pieceArray = def.get(orientation);
        
        for(int i = 0;i < pieceArray.length;i++) {
            for(int p = 0;p < pieceArray[0].length;p++) {
                if((x + i >= 0 && x + i < board.length &&
                        y + p >= 0 && y + p < board[0].length)) {
                    if(pieceArray[p][i] != 0) {
                        board[x + i][y + p] = pieceArray[p][i];
                    }
                }
            }
        }
    }

    public int[][] merge(int[][] board, boolean previews) {
        int[][] res = new int[board.length][board[0].length];
        int[][] pieceArray = def.get(orientation);

        // Copy board to new board
        for(int i = 0;i < res.length;i++) {
            for(int p = 0;p < res[0].length;p++) {
                res[i][p] = board[i][p];
            }
        }

        if(previews) {
            int spacesDown = howManyDown(board);

            // Copy piece to new board
            for(int p = 0;p < pieceArray[0].length;p++) {
                for(int i = 0;i < pieceArray.length;i++) {
                    if((x + i >= 0 && x + i < board.length &&
                            y + p + spacesDown >= 0 && y + p + spacesDown < board[0].length)) {
                        if(pieceArray[p][i] != 0) {
                            res[x + i][y + p + spacesDown] = pieceArray[p][i] + 10;
                        }
                    }
                }
            }
        }

        // Copy piece to new board
        for(int p = 0;p < pieceArray[0].length;p++) {
            for(int i = 0;i < pieceArray.length;i++) {
                if((x + i >= 0 && x + i < board.length &&
                        y + p >= 0 && y + p < board[0].length)) {
                    if(pieceArray[p][i] != 0) {
                        res[x + i][y + p] = pieceArray[p][i];
                    }
                }
            }
        }

        return res;
    }

    public boolean canRotateLeft(int[][] board) {
        return canRotate(board, (orientation + NUM_ORIENTATIONS - 1) % NUM_ORIENTATIONS);
    }

    public boolean canRotateRight(int[][] board) {
        return canRotate(board, (orientation + 1) % NUM_ORIENTATIONS);
    }

    public boolean canDown(int[][] board) {
        int[][] pieceArray = def.get(orientation);
        
        for(int i = 0;i < pieceArray.length;i++) {
            for(int p = 0;p < pieceArray[0].length;p++) {
                if((x + i < 0 || x + i >= board.length ||
                        y + p + 1 < 0 || y + p + 1 >= board[0].length) &&
                        pieceArray[p][i] != 0) {
                    return false;
                }
                else if(pieceArray[p][i] != 0 && board[x + i][ y + p + 1] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean canRight(int[][] board) {
        int[][] pieceArray = def.get(orientation);

        for(int i = 0;i < pieceArray.length;i++) {
            for(int p = 0;p < pieceArray[0].length;p++) {
                if((x + i + 1 < 0 || x + i + 1 >= board.length ||
                        y + p < 0 || y + p >= board[0].length) &&
                        pieceArray[p][i] != 0) {
                    return false;
                }
                else if(pieceArray[p][i] != 0 && board[x + i + 1][ y + p] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean canLeft(int[][] board) {
        int[][] pieceArray = def.get(orientation);

        for(int i = 0;i < pieceArray.length;i++) {
            for(int p = 0;p < pieceArray[0].length;p++) {
                if((x + i - 1 < 0 || x + i - 1 >= board.length ||
                        y + p < 0 || y + p >= board[0].length) &&
                        pieceArray[p][i] != 0) {
                    return false;
                }
                else if(pieceArray[p][i] != 0 && board[x + i - 1][ y + p] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean canRotate(int[][] board, int orientation) {
        int[][] pieceArray = def.get(orientation);

        for(int i = 0;i < pieceArray.length;i++) {
            for(int p = 0;p < pieceArray[0].length;p++) {
                if((x + i < 0 || x + i >= board.length ||
                        y + p < 0 || y + p >= board[0].length) &&
                        pieceArray[p][i] != 0) {
                    return false;
                }
                else if(pieceArray[p][i] != 0 && board[x + i][ y + p] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public int howManyDown(int[][] board) {
        int[][] pieceArray = def.get(orientation);

        int counter = 0;

        while(true) {
            counter++;
            for(int i = 0;i < pieceArray.length;i++) {
                for(int p = 0;p < pieceArray[0].length;p++) {
                    if((x + i < 0 || x + i >= board.length ||
                            y + p + counter < 0 || y + p + counter >= board[0].length) &&
                            pieceArray[p][i] != 0) {
                        return counter - 1;
                    }
                    else if(pieceArray[p][i] != 0 && board[x + i][ y + p + counter] != 0) {
                        return counter - 1;
                    }
                }
            }
        }
    }

    @Override
    public Piece clone() {
        Piece clone = new Piece(def, x, y);
        clone.orientation = orientation;

        return clone;
    }
}
