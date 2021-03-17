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
        b[0][3] = new king(false);
        b[0][4] = new queen(false);
        b[0][5] = new bishop(false);
        b[0][6] = new knight(false);
        b[0][7] = new rook(false);
        for(int i=0; i<8; i++){
            b[1][i] = new pawn(false);
        }

        // setting the black pieces
        b[7][0] = new rook(true);
        b[7][1] = new knight(true);
        b[7][2] = new bishop(true);
        b[7][3] = new king(true);
        b[7][4] = new queen(true);
        b[7][5] = new bishop(true);
        b[7][6] = new knight(true);
        b[7][7] = new rook(true);
        for(int i=0; i<8; i++){
            b[6][i] = new pawn(true);
        }
    }

    /**
     * Gets the piece at a given point.
     * @param p The target point on the chessboard.
     * @return The piece currently at point p.
     */
    public piece getPiece(point p){
        //System.out.println(b[p.getX()][p.getY()]); debug
        return b[p.getX()][p.getY()];
    }

    public boolean isCheckmate(){
        return false; //implement
    }
}
