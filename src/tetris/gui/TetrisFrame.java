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
import tetris.util.GameAccessor;

/**
 *
 * @author User
 */
public class TetrisFrame extends JFrame {

    public TetrisFrame() {
        setTitle("Tetris");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setBackground(Color.BLACK);

        Container c = getContentPane();

        c.setLayout(new GridBagLayout());

        populate(c);
        pack();
        setVisible(true);

        this.addKeyListener(new KeyController());

        new Thread(new Runnable(){
            public void run() {
                while(true)
                    repaint();
            }
        }).start();
    }

    private void populate(Container frame) {
        GridBagConstraints c = new GridBagConstraints();

        int x = 0;

        modifyConstraints(c, x, 0, 10, 20);
        frame.add(new BoardPanel(0), c);

        x += 10;

        if(GameAccessor.getGame().getNumPlayers() == 2) {
            modifyConstraints(c, x++, 0, 1, 20);
            frame.add(new IncomingLinesPanel(0), c);
        }

        modifyConstraints(c, x, 0, 5, 4);
        frame.add(new NextPiecePanel(0), c);

        modifyConstraints(c, x, 4, 5, 6);
        frame.add(new ScorePanel(0), c);

        if(GameAccessor.getGame().getNumPlayers() == 2) {
            modifyConstraints(c, x, 10, 5, 4);
            frame.add(new NextPiecePanel(1), c);

            modifyConstraints(c, x, 14, 5, 6);
            frame.add(new ScorePanel(1), c);

            x+=5;

            modifyConstraints(c, x, 0, 1, 20);
            frame.add(new IncomingLinesPanel(1), c);

            x++;

            modifyConstraints(c, x, 0, 10, 20);
            frame.add(new BoardPanel(1), c);

            
        }
    }

    private void modifyConstraints(GridBagConstraints c, int x, int y, int width, int height) {
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridheight = height;
    }
}
