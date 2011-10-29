/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.control;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import tetris.core.Player;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public abstract class AIController {

    private Player player;

    private final ReentrantLock lock;
    private final Condition cv;

    private int delay;

    private boolean newPiece;

    public AIController() {
        lock = new ReentrantLock();
        cv = lock.newCondition();

        newPiece = false;
    }

    protected abstract void process();

    public final void setDelay(int delay) {
        this.delay = delay;
    }

    protected final GameState getCurrentGameState() {
        return player.getGameState();
    }

    protected final void addCommand(Command command) {
        try {
            Thread.sleep(delay);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Uncaught exception while sleeping: " + e.toString());
            e.printStackTrace();
        }
        player.addCommand(command);
    }

    public final void start(Player player) {
        this.player = player;

        new Thread(new Runnable() {
            public void run() {
                mainLoop();
            }
        }).start();
    }

    public final void newPiece() {
        lock.lock();
        try {
            newPiece = true;

            cv.signal();
        } finally {
            lock.unlock();
        }
    }

    private void sleepUntilNewPiece() {
        lock.lock();

        try {

            if(!newPiece) {
                cv.awaitUninterruptibly();
            }
        
            newPiece = false;
        } finally {
            lock.unlock();
        }
    }

    private void mainLoop() {
        while(!player.hasLost()) {
            try {
                process();
            } catch(Exception e) {
                player.kill();
                JOptionPane.showMessageDialog(null, "Uncaught exception during AI: " + e.toString());
            }
            sleepUntilNewPiece();
        }
    }
}
