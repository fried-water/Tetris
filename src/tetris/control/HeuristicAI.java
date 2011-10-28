/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author User
 */
public abstract class HeuristicAI extends AIController {

    protected abstract double heuristic(GameState state);

    @Override
    protected final void process() {
        executeCommands(getBestMove(evaluateAllPossibilities()));
    }

    private void executeCommands(int[] commands) {
        for(int i = 0; i < commands[0]; i++) {
            this.addCommand(Command.ROTATE_LEFT);
        }

        if(commands[1] >= 0) {
            for(int i = 0;i < commands[1];i++) {
                this.addCommand(Command.RIGHT);
            }
        } else {
            for(int i = 0;i < -commands[1];i++) {
                this.addCommand(Command.LEFT);
            }
        }
 
        this.addCommand(Command.DROP);
    }

    private List<Map<Integer, Double>> evaluateAllPossibilities() {
        GameState state = getCurrentGameState();
        List<Map<Integer, Double>> values = new ArrayList<Map<Integer, Double>>();

        for(int i = 0;i < 4; i++) {
            values.add(new HashMap<Integer, Double>());

            revertAndRotateTo(i, state);

            values.get(i).put(0, dropAndEvaluate(state));

            for(int x = 1; state.canRight(); x++) {
                state.simulateRight();
                values.get(i).put(x, dropAndEvaluate(state));
            }

            revertAndRotateTo(i, state);

            for(int x = 1; state.canLeft(); x++) {
                state.simulateLeft();
                values.get(i).put(-x, dropAndEvaluate(state));
            }
        }

        return values;
    }

    private int[] getBestMove(List<Map<Integer, Double>> values) {
        int[] maxValues = new int[2];
        double max = Double.MIN_VALUE;

        for(int rot = 0; rot < values.size(); rot++) {
            for(int mov : values.get(rot).keySet()) {
                double temp = values.get(rot).get(mov);
                if(temp > max) {
                    max = temp;
                    maxValues[0] = rot;
                    maxValues[1] = mov;
                }
            }
        }

        return maxValues;
    }

    private double dropAndEvaluate(GameState state) {
        state.simulateDrop();
        double res = heuristic(state);
        state.undo();

        return res;
    }

    private void revertAndRotateTo(int amount, GameState state) {
        state.revert();
        for(int i = 0; i < amount; i++) {
            state.simulateRotateLeft();
        }
    }
}
