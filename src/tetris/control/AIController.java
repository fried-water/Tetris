/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.control;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import tetris.core.Player;

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
        } catch(Exception e) {}
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

        newPiece = true;

        cv.signal();

        lock.unlock();
    }

    private void sleepUntilNewPiece() {
        lock.lock();

        if(!newPiece) {
            cv.awaitUninterruptibly();
        }
        
        newPiece = false;

        lock.unlock();
    }

    private void mainLoop() {
        while(!player.hasLost()) {
            process();
            sleepUntilNewPiece();
        }
    }
}
