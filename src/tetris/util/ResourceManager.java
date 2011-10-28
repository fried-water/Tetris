/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author User
 */
public class ResourceManager {

    private final static int NUM_PICS = 8;
    private final static String IMG_PATH = "img/#.bmp";

    private static Map<Integer, BufferedImage> images = new HashMap<Integer, BufferedImage>();

    public static void loadResources() {
        String filename;

        for(int i = 1; i <= NUM_PICS; i++) {
            filename = IMG_PATH.replace("#", "" + i);
            try {
                images.put(i, ImageIO.read(new File(filename)));
            } catch (IOException e) {

                URL url = new ResourceManager().getClass().getClassLoader().getResource(filename);
                try {
                    images.put(i, ImageIO.read(url));
                } catch (IOException ex) {
                    System.out.println("FAILURE");
                }
            }
        }
        generateTransparencies();
    }

    private static void generateTransparencies() {
        for (int i = 1; i< NUM_PICS; i++) {
            images.put(i + 10, generateTransparency(images.get(i)));
        }
    }

    private static BufferedImage generateTransparency(BufferedImage image) {
        BufferedImage temp;

        temp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for(int y = 0; y < temp.getHeight(); y++) {
            for (int x = 0; x < temp.getWidth(); x++) {
                if(x == 0 || y == 0 || x == temp.getWidth() - 1 ||
                        y == temp.getHeight() - 1) {
                    temp.setRGB(x, y, 0x00000000);
                } else {
                    temp.setRGB(x, y, image.getRGB(x, y) + 0x40000000);
                }
            }
        }

        return temp;
    }

    public static BufferedImage getImage(int num) {
        return images.get(num);
    }
}
