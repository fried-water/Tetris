/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import tetris.core.Player;
import tetris.util.GameAccessor;

/**
 *
 * @author User
 */
public class IncomingLinesPanel extends JPanel {
    private final static int PANEL_HEIGHT = 20;
    private final static int TILE_SIZE = 32;

    private int player;

    public IncomingLinesPanel(int player) {
        this.player = player;

        setPreferredSize(new Dimension((int)(0.25 * TILE_SIZE),
                PANEL_HEIGHT * TILE_SIZE));
    }

    @Override
    public void paintComponent(Graphics g) {
        Player p = GameAccessor.getGame().getPlayer(player);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, (int)(0.25 * TILE_SIZE), PANEL_HEIGHT * TILE_SIZE);

        g.setColor(Color.ORANGE);
        g.fillRect(1, (PANEL_HEIGHT - p.getIncomingLines() - p.getNumIndestructableLines()) * TILE_SIZE, (int)(0.25 * TILE_SIZE) - 2,
                p.getIncomingLines() * TILE_SIZE);

        g.setColor(Color.BLACK);
        g.drawRect(0, -1, (int)(0.25 * TILE_SIZE) - 1, PANEL_HEIGHT * TILE_SIZE);
        g.fillRect(0, (PANEL_HEIGHT - p.getNumIndestructableLines()) * TILE_SIZE, (int)(0.25 * TILE_SIZE),
                p.getNumIndestructableLines() * TILE_SIZE);


        
        
    }
}
