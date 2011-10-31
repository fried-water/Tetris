/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.core;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import tetris.control.AIController;
import tetris.piece.Piece;
import tetris.control.Command;
import tetris.control.GameState;
import tetris.option.Options;

/**
 *
 * @author User
 */
public class Player {
    private final static int WIDTH = 10;
    private final static int HEIGHT = 21;

    private Game game;
    private int pID;
    
    private int[][] board;
    private int pieceCounter;
    private Piece currentPiece;
    private Piece nextPiece;
    private List<Command> commands;
    private boolean drop;

    private boolean lost;
    
    private int numLines;
    private int level;
    private int score;

    private long startTime;
    private long endTime;

    private int incomingLines;
    private int indestructibleLinesAdded;

    private GameState state;
    private boolean newPiece;

    private int gameDelay;
    private int lineDelay;

    private boolean previews;

    private boolean indestructibles;
    private int indestructibleStartTime;
    private int indestructibleRate;

    private List<Integer> linesToBeRemoved;

    private AIController ai;

    public Player(Game game, int pID, Options options) {
        this.game = game;
        this.pID = pID;

        lost = false;
        drop = false;

        numLines = 0;
        level = 0;
        score = 0;

        incomingLines = 0;
        indestructibleLinesAdded = 0;

        commands = new ArrayList<Command>();

        board = new int[WIDTH][HEIGHT];
        pieceCounter = 0;

        nextPiece = new Piece(game.getNextPiece(pieceCounter++), 3, 0);

        fetchNewPiece();
        generateGameState();

        gameDelay = options.getGameDelay();
        lineDelay = options.getLineDelay();

        previews = options.isPreviews();

        indestructibles = options.isIndestructibles();
        indestructibleStartTime = options.getStartTime();
        indestructibleRate = options.getRate();

        if(!options.isHuman(pID)) {
            try {
                ai = options.getAIClass(pID).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error instantiating AI for player " + pID);
                System.exit(1);
            }
            ai.setDelay(options.getAIDelay());
        }
    }

    public int getNumLines() {
        return numLines;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public boolean hasLost() {
        return lost;
    }

    public void kill() {
        lost = true;
        endTime = System.currentTimeMillis();
    }

    public int[][] getMergedBoard() {
        return currentPiece.merge(board, previews);
    }

    public int[][] getBoard() {
        return board;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public Piece getNextPiece() {
        return nextPiece;
    }

    public int getIncomingLines() {
        return incomingLines;
    }

    public int getNumIndestructableLines() {
        return indestructibleLinesAdded;
    }

    private void fetchNewPiece() {
        currentPiece = nextPiece;
        nextPiece = new Piece(game.getNextPiece(pieceCounter++), 3, 0);
        newPiece = true;
    }

    private List<Integer> lines() {
        List<Integer> lines = new ArrayList<Integer>();
        boolean full;

        for(int y = 0; y < HEIGHT - indestructibleLinesAdded; y++) {
            full = true;

            for(int x = 0; x < WIDTH; x++) {
                if(board[x][y] == 0) {
                    full = false;
                    break;
                }
            }

            if(full) {
                lines.add(y);
            }
        }

        return lines;
    }

    public List<Integer> getLinesToBeRemoved() {
        return linesToBeRemoved;
    }

    private void removeLine(int line) {
        for(int y = line; y > 0; y--) {
            for(int x = 0; x < WIDTH; x++) {
                board[x][y] = board[x][y-1];
            }
        }
    }

    private void removeLines(List<Integer> lines) {
        numLines += lines.size();

        if(lines.size() > 1) {
            score += Math.pow(lines.size(), 2) * 100;
        } else {
            score += lines.size() * 100;
        }

        if(numLines / 10 > level) {
            level = Math.min(12, level + 1);
            score += level * 2000;
        }

        if(lines.size() > 0) {
            linesToBeRemoved = lines;
            try {
                Thread.sleep(lineDelay);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Uncaught exception while sleeping: " + e.toString());
                e.printStackTrace();
            }
            linesToBeRemoved = null;
        }

        for(int line : lines) {
            removeLine(line);
        }

        if(lines.size() > 0) {
            int linesToSend = lines.size() - 1 + lines.size() / 4;

            if(linesToSend > incomingLines) {
                linesToSend -= incomingLines;
                incomingLines = 0;

                game.addLines(pID, linesToSend);
            } else {
                incomingLines -= linesToSend;
            }
            
        }
    }

    public String getTimeString() {
        long timePast = lost? endTime - startTime : System.currentTimeMillis() - startTime;
        return (timePast / 1000) + "." + (timePast / 100 % 10);
    }

    private void addLinesToBoard(int amount) {
        int random;

        for(int y = amount;y < HEIGHT - indestructibleLinesAdded;y++) {
            for(int x = 0;x < board.length;x++) {
                board[x][y - amount] = board[x][y];
            }
        }

        for(int y = HEIGHT - indestructibleLinesAdded - amount;y < HEIGHT - indestructibleLinesAdded;y++) {
            if (y < 0) y = 0;
            random = (int) (Math.random() * board.length);

            for(int x = 0;x < board.length;x++) {
                if(x != random) {
                    board[x][y] = 8;
                } else {
                    board[x][y] = 0;
                }
            }
        }
    }

    private void addIndestructibleLine() {
        if(System.currentTimeMillis() - startTime > 
                indestructibleStartTime + indestructibleRate * indestructibleLinesAdded) {
            indestructibleLinesAdded++;

            for(int y = 1;y < HEIGHT; y++) {
                for(int x = 0;x < WIDTH; x++) {
                    board[x][y - 1] = board[x][y];
                }
            }

            for(int x = 0;x < WIDTH; x++) {
                board[x][HEIGHT - 1] = 9;
            }
        }
    }

    public synchronized boolean hasNewPiece() {
        newPiece = !newPiece;

        return !newPiece;
    }

    public synchronized GameState getGameState() {
        return state;
    }

    private synchronized void generateGameState() {
        state = new GameState(board, currentPiece);
    }

    public synchronized void addExtraLines(int amount) {
        incomingLines += amount;
    }

    private synchronized int getExtraLines() {
        int amount = incomingLines;
        incomingLines = 0;

        return amount;
    }

    public synchronized void addCommand(Command command) {
        commands.add(command);
    }

    public synchronized boolean hasCommand() {
        return !commands.isEmpty();
    }

    public synchronized Command nextCommand() {
        return commands.remove(0);
    }

    public synchronized void clearCommands() {
        commands.clear();
    }

    private void executeCommand(Command command) {
        if(command == Command.LEFT) {
            if(currentPiece.canLeft(board)) {
                currentPiece.moveLeft();
            }
        } else if(command == Command.RIGHT) {
            if(currentPiece.canRight(board)) {
                currentPiece.moveRight();
            }
        } else if(command == Command.DOWN) {
            if(currentPiece.canDown(board)) {
                score += 1;
                currentPiece.moveDown();
            }
        } else if(command == Command.ROTATE_RIGHT) {
            if(currentPiece.canRotateRight(board)) {
                currentPiece.rotateRight();
            }
        } else if(command == Command.ROTATE_LEFT) {
            if(currentPiece.canRotateLeft(board)) {
                currentPiece.rotateLeft();
            }
        } else if(command == Command.DROP) { 
            while(currentPiece.canDown(board)) {
                score += 2;
                currentPiece.moveDown();
            }
            drop = true;
        }
    }

    void start() {
        startTime = System.currentTimeMillis();
        new Thread(new Runnable() {
            public void run() {
                mainLoop();
            }       
        }).start();

        if(ai != null) {

            ai.start(this);
        }
    }

    private void mainLoop() {
        int counter = 0;
        while(!lost) {
            if(!drop && hasCommand()) {
                executeCommand(nextCommand());
            }

            if(drop || game.getNumPlayers() == 1 && counter++ == 135 - level * 10 ||
                    game.getNumPlayers() == 2 && counter++ == 85) {
                
                counter = 0;

                if(currentPiece.canDown(board)) {
                    currentPiece.moveDown();
                    if(indestructibles && currentPiece.canDown(board)) {
                        addIndestructibleLine();
                    }
                } else {
                    drop = false;
                    clearCommands();
                    currentPiece.stick(board);
                    removeLines(lines());

                    fetchNewPiece();

                    addLinesToBoard(getExtraLines());

                    if(!currentPiece.canStick(board)) {
                        kill();
                        break;
                    }

                    generateGameState();

                    if(ai != null) {
                        ai.newPiece();
                    }
                }

                generateGameState();
            }
            try {
                Thread.sleep(gameDelay);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Uncaught exception while sleeping: " + e.toString());
                e.printStackTrace();
            }
        }
    }
}
