/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import tetris.control.KeyController;
import tetris.core.Game;

/**
 *
 * @author User
 */
public class TetrisFrame extends JFrame {

    public TetrisFrame(Game game) {
        setTitle("Tetris");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setBackground(Color.BLACK);

        Container c = getContentPane();

        c.setLayout(new GridBagLayout());

        populate(c, game);
        pack();
        setVisible(true);

        this.addKeyListener(new KeyController(game));

        new Thread(new Runnable(){
            public void run() {
                while(true)
                    repaint();
            }
        }).start();
    }

    private void populate(Container frame, Game game) {
        GridBagConstraints c = new GridBagConstraints();

        int x = 0;

        modifyConstraints(c, x, 0, 10, 20);
        frame.add(new BoardPanel(game.getPlayer(0)), c);

        x += 10;

        if(game.getNumPlayers() == 2) {
            modifyConstraints(c, x++, 0, 1, 20);
            frame.add(new IncomingLinesPanel(game.getPlayer(0)), c);
        }

        modifyConstraints(c, x, 0, 5, 4);
        frame.add(new NextPiecePanel(game.getPlayer(0)), c);

        modifyConstraints(c, x, 4, 5, 6);
        frame.add(new ScorePanel(game.getPlayer(0)), c);

        if(game.getNumPlayers() == 2) {
            modifyConstraints(c, x, 10, 5, 4);
            frame.add(new NextPiecePanel(game.getPlayer(1)), c);

            modifyConstraints(c, x, 14, 5, 6);
            frame.add(new ScorePanel(game.getPlayer(1)), c);

            x+=5;

            modifyConstraints(c, x, 0, 1, 20);
            frame.add(new IncomingLinesPanel(game.getPlayer(1)), c);

            x++;

            modifyConstraints(c, x, 0, 10, 20);
            frame.add(new BoardPanel(game.getPlayer(1)), c);

            
        }
    }

    private void modifyConstraints(GridBagConstraints c, int x, int y, int width, int height) {
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridheight = height;
    }
}
