/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.option;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;
import tetris.control.AIController;

/**
 *
 * @author User
 */
public class Options {
    private final static String AI_PACKAGE = "tetris.ai.";
    private final static String OPTIONS_FILE = "options.properties.";

    private Properties options;

    public Options() {
        options = new Properties();
        try {
            options.load(new FileInputStream(new File("options.properties")));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading "+ OPTIONS_FILE);
            System.exit(1);
        }
    }

	public Class<AIController> getAIClass(int pID) {
        String name = options.getProperty(OptionConstants.CONTROL.replace("#", ""+(pID + 1)));
        try {
            return (Class<AIController>) Class.forName(AI_PACKAGE + name);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No AI found for " + AI_PACKAGE + name);
            System.exit(1);

            return null;
        } catch (ClassCastException e) {
        	JOptionPane.showMessageDialog(null, "No AI found for " + AI_PACKAGE + name);
            System.exit(1);

            return null;
        }
    }

    public int getNumPlayers() {
        return Integer.parseInt(options.getProperty(OptionConstants.NUM_PLAYERS));
    }

    public int getStartTime() {
        return Integer.parseInt(options.getProperty(OptionConstants.START_TIME));
    }

    public int getRate() {
        return Integer.parseInt(options.getProperty(OptionConstants.RATE));
    }

    public int getGameDelay() {
        return Integer.parseInt(options.getProperty(OptionConstants.GAME_DELAY));
    }

    public int getLineDelay() {
        return Integer.parseInt(options.getProperty(OptionConstants.LINE_DELAY));
    }

    public int getAIDelay() {
        return Integer.parseInt(options.getProperty(OptionConstants.AI_DELAY));
    }

    public boolean isPreviews() {
        return "true".equalsIgnoreCase(options.getProperty(OptionConstants.PREVIEWS));
    }

    public boolean isIndestructibles() {
        return "true".equalsIgnoreCase(options.getProperty(OptionConstants.INDESTRUCTIBLES));
    }

    public boolean isHuman(int pID) {
        return "human".equalsIgnoreCase(
                options.getProperty(OptionConstants.CONTROL.replace("#", ""+(pID + 1))));
    }
}
