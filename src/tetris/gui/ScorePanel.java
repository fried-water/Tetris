/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;
import tetris.core.Player;

/**
 *
 * @author User
 */
public class ScorePanel extends JPanel {
    private final static int PANEL_WIDTH = 5;
    private final static int PANEL_HEIGHT = 6;
    private final static int TILE_SIZE = 32;

    private Player player;

    public ScorePanel(Player player) {
        this.player = player;

        setPreferredSize(new Dimension(PANEL_WIDTH * TILE_SIZE,
                PANEL_HEIGHT * TILE_SIZE));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(224, 224, 224));
        g.fillRect(0, 0, PANEL_WIDTH * TILE_SIZE, PANEL_HEIGHT * TILE_SIZE);

        g.setColor(Color.BLACK);
        g.drawRect(-1, -1, PANEL_WIDTH * TILE_SIZE + 1 , PANEL_HEIGHT * TILE_SIZE);
        drawScores(g);
    }

    private void drawScores(Graphics g) {
        g.setFont(new Font("SansSerif", Font.BOLD, 18));

        FontMetrics metrics = g.getFontMetrics();

        g.drawString("Lines", 5, 25);
        g.drawString("Level", 5, 75);
        g.drawString("Score", 5, 125);
        g.drawString("Time", 5, 175);

        g.drawString("" + player.getNumLines(), 
                TILE_SIZE * PANEL_WIDTH - 10 - metrics.stringWidth("" + player.getNumLines()),
                25);
        g.drawString("" + player.getLevel(), 
                TILE_SIZE * PANEL_WIDTH - 10 - metrics.stringWidth("" + player.getLevel()),
                75);
        g.drawString("" + player.getScore(),
                TILE_SIZE * PANEL_WIDTH - 10 - metrics.stringWidth("" + player.getScore()),
                125);
        g.drawString(player.getTimeString(),
                TILE_SIZE * PANEL_WIDTH - 10 - metrics.stringWidth("" + player.getTimeString()),
                175);
    }
}
