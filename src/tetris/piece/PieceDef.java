/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.piece;


/**
 *
 * @author User
 */
public enum PieceDef {
    I(PieceConstants.I_ARRAY, 1),
    L(PieceConstants.L_ARRAY, 2),
    J(PieceConstants.J_ARRAY, 3),
    O(PieceConstants.O_ARRAY, 4),
    T(PieceConstants.T_ARRAY, 5),
    S(PieceConstants.S_ARRAY, 6),
    Z(PieceConstants.Z_ARRAY, 7);

    public final static int AMOUNT = 7;

    private final int[][][] data;
    private final int num;

    private PieceDef(int[][][] data, int num) {
        this.data = data;
        this.num = num;

        for(int i = 0; i < data.length; i++) {
            for(int p = 0; p < data[0].length; p++) {
                for(int q = 0; q < data[0][0].length; q++) {
                    data[i][p][q] *= num;
                }
            }
        }
    }

    public int[][] get(int orientation) {
        return data[orientation];
    }

    public int getNum() {
        return num;
    }

    public static PieceDef getRandomPiece() {
        return getPiece((int)(Math.random() * 7 + 1));
    }

    public static PieceDef getPiece(int num) {
        switch(num) {
            case 1:
                return I;
            case 2:
                return L;
            case 3:
                return J;
            case 4:
                return O;
            case 5:
                return T;
            case 6:
                return S;
            case 7:
                return Z;
            default:
                System.out.println("Error asking for piece: " + num);
                return null;
        }
    }
}
