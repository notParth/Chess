package main;

import pieces.*;

public class board {

    // 2D array
    public piece[][] b;

    /**
     * Class Constructor setting up default chessboard with white/black pieces.
     */
    public board() {
        b = new piece[8][8];

        // setting the white pieces
        b[0][0] = new rook(false);
        b[0][1] = new knight(false);
        b[0][2] = new bishop(false);
        b[0][4] = new king(false);
        b[0][3] = new queen(false);
        b[0][5] = new bishop(false);
        b[0][6] = new knight(false);
        b[0][7] = new rook(false);
        for (int i = 0; i < 8; i++) {
            b[1][i] = new pawn(false);
        }

        // setting the black pieces
        b[7][0] = new rook(true);
        b[7][1] = new knight(true);
        b[7][2] = new bishop(true);
        b[7][4] = new king(true);
        b[7][3] = new queen(true);
        b[7][5] = new bishop(true);
        b[7][6] = new knight(true);
        b[7][7] = new rook(true);
        for (int i = 0; i < 8; i++) {
            b[6][i] = new pawn(true);
        }
    }

    /**
     * Displays board in terminal. Black spaces are represented by ##, white spaces are blank. Pieces are represented as color + name combination.
     */
    public void display() {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                if (b[i][j] == null) {
                    if (i % 2 == 0) {
                        if (j % 2 == 0)
                            System.out.print("## ");
                        else
                            System.out.print("   ");
                    } else {
                        if (j % 2 == 0)
                            System.out.print("   ");
                        else
                            System.out.print("## ");
                    }
                } else {
                    char color = ((b[i][j].getIsBlack()) ? 'b' : 'w');
                    String name = b[i][j].getName();
                    System.out.print(color + name + " ");
                }
            }
            System.out.println((i + 1));
        }
        System.out.println(" a  b  c  d  e  f  g  h\n");
    }

    /**
     * Gets the piece at a given point.
     *
     * @param p The target point on the chessboard.
     * @return The piece currently at point p.
     */
    public piece getPiece(point p) {
        //System.out.println(b[p.getX()][p.getY()]); debug
        return b[p.getX()][p.getY()];
    }

    /**
     * Makes a copy of the board and its pieces.
     *
     * @return The new board.
     */
    public board copyBoard() {
        board newGame = new board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.b[i][j] == null) {
                    newGame.b[i][j] = null;
                } else {
                    newGame.b[i][j] = this.b[i][j].copy();
                }
            }
        }
        return newGame;
    }

    public boolean isCheckmate() {
        return false; //implement
    }
}
