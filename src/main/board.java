package main;
import pieces.*;
public class board {

    // 2D array
    public piece[][] b;

    public board() {
        b = new piece[8][8];

        // setting the white pieces
        b[0][0] = new rook(false);
        b[1][0] = new knight(false);
        b[2][0] = new bishop(false);
        b[3][0] = new king(false);
        b[4][0] = new queen(false);
        b[5][0] = new bishop(false);
        b[6][0] = new knight(false);
        b[7][0] = new rook(false);
        for(int i=0; i<8; i++){
            b[i][1] = new pawn(false);
        }

        // setting the black pieces
        b[0][7] = new rook(true);
        b[1][7] = new knight(true);
        b[2][7] = new bishop(true);
        b[3][7] = new king(true);
        b[4][7] = new queen(true);
        b[5][7] = new bishop(true);
        b[6][7] = new knight(true);
        b[7][7] = new rook(true);
        for(int i=0; i<8; i++){
            b[i][6] = new pawn(true);
        }
    }

    public piece getPiece(point p){
        //System.out.println(b[p.getX()][p.getY()]); debug
        return b[p.getX()][p.getY()];
    }

    public boolean isCheckmate(){
        return false; //implement
    }
}
