/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.core;

import java.util.ArrayList;
import tetris.piece.PieceDef;
import java.util.List;
import tetris.control.Command;
import tetris.option.Options;

/**
 *
 * @author User
 */
public class Game {

    private int num_players;
    private Player[] players;
    private List<PieceDef> pieces;

    public Game(Options options) {
        int pID = 0;

        this.num_players = options.getNumPlayers();
        players = new Player[num_players];

        pieces = new ArrayList<PieceDef>();

        for(int i=0;i<num_players;i++) {
            players[i] = new Player(this, pID++, options);
        }
    }

    public int getNumPlayers() {
        return players.length;
    }

    synchronized void addLines(int player, int lines) {
        if(num_players == 2){
            players[1 - player].addExtraLines(lines);
        }
    }

    public synchronized PieceDef getNextPiece(int num) {
        if(num == pieces.size()) {
            pieces.add(PieceDef.getRandomPiece());
        }

        return pieces.get(num);
    }

    public synchronized Player getPlayer(int pID) {
        return players[pID];
    }

    public synchronized void execute(int player, Command command) {
        players[player].addCommand(command);
    }

    public void start() {
        for(int i=0;i<num_players;i++) {
            players[i].start();
        }
    }
}
