/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import tetris.util.ResourceManager;
import tetris.util.GameAccessor;
import tetris.piece.PieceDef;

/**
 *
 * @author User
 */
public class NextPiecePanel extends JPanel {

    private final static int PANEL_WIDTH = 5;
    private final static int PANEL_HEIGHT = 4;
    private final static int TILE_SIZE = 32;

    private int player;

    public NextPiecePanel(int player) {
        this.player = player;

        setPreferredSize(new Dimension(PANEL_WIDTH * TILE_SIZE,
                PANEL_HEIGHT * TILE_SIZE));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(224, 224, 224));
        g.fillRect(0, 0, PANEL_WIDTH * TILE_SIZE, PANEL_HEIGHT * TILE_SIZE);

        g.setColor(Color.BLACK);
        g.drawRect(-1, -1, PANEL_WIDTH * TILE_SIZE + 1, PANEL_HEIGHT * TILE_SIZE);
        drawPiece(g);
    }

    private void drawPiece(Graphics g) {
        int[][] data = GameAccessor.getGame().getPlayer(player).getNextPiece().getData(0);
        int num = GameAccessor.getGame().getPlayer(player).getNextPiece().getNum();

        for(int y = 0;y < data[0].length; y++) {
            for(int x = 0;x < data.length; x++) {
                int extraX = TILE_SIZE;

                if(PieceDef.I.getNum() == num) {
                    extraX -= TILE_SIZE / 2;
                } else if(PieceDef.O.getNum() == num) {
                    extraX -= TILE_SIZE / 2;
                }

                int extraY = PieceDef.I.getNum() == num? TILE_SIZE / 2 : 0;

                 if(data[y][x] != 0) {
                    g.drawImage(ResourceManager.getImage(data[y][x]),
                            x * TILE_SIZE + extraX, y * TILE_SIZE + extraY, this);
                 }
            }
        }
    }
}
