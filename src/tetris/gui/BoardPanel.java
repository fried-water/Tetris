/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;
import tetris.util.ResourceManager;
import tetris.util.GameAccessor;

/**
 *
 * @author User
 */
public class BoardPanel extends JPanel {

    private final static int BOARD_WIDTH = 10;
    private final static int BOARD_HEIGHT = 20;
    private final static int TILE_SIZE = 32;

    private int player;

    public BoardPanel(int player) {
        this.player = player;

        setPreferredSize(new Dimension(BOARD_WIDTH * TILE_SIZE,
                BOARD_HEIGHT * TILE_SIZE));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, BOARD_WIDTH * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE);

        drawBoard(g);
        if(GameAccessor.getGame().getPlayer(player).hasLost()) {
            drawEndGameText(g);
        }
    }

    private void drawBoard(Graphics g) {
        int[][] board = GameAccessor.getGame().getPlayer(player).getMergedBoard();
        List<Integer> lines = GameAccessor.getGame().getPlayer(player).getLinesToBeRemoved();

        for(int y = 1; y < board[0].length; y++) {
            for(int x = 0; x < board.length; x++) {
                if(board[x][y] == 9) {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, (y - 1) * TILE_SIZE - 1, BOARD_WIDTH * TILE_SIZE, 2);
                    
                    g.setColor(Color.GRAY);
                    g.fillRect(0, (y - 1) * TILE_SIZE + 1, BOARD_WIDTH * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE);

                    break;
                }
                else if(board[x][y] != 0) {
                    g.drawImage(ResourceManager.getImage(board[x][y]), 
                            x * TILE_SIZE - 1, (y - 1) * TILE_SIZE - 1, this);
                }
            }

            if(board[0][y] == 9) {
                break;
            }
        }

        if(lines != null) {
            if(System.currentTimeMillis() % 100 > 50) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.WHITE);
            }
            for(int y = 1; y < board[0].length; y++) {
                if(lines.contains(y)) {
                    g.fillRect(0, (y - 1) * TILE_SIZE, BOARD_WIDTH * TILE_SIZE, TILE_SIZE);
                }
           }
        }
    }

    private void drawEndGameText(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 48));
        
        g.drawString("Game Over", BOARD_WIDTH * TILE_SIZE / 2 - g.getFontMetrics().stringWidth("Game Over")/2, 200);
    }
}
