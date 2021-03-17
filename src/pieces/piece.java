package pieces;
import main.*;
/*
 * @author Parth Patel
 * @author Amanda Kang
 */

import java.util.List;

public abstract class piece {

    // 'p' for pawn, 'Q' for queen etc...
    char name;
    // True for a black piece, False for a white piece
    boolean isBlack;
    // Check if piece has made a first move yet
    boolean first_move;
    // Check if pawn eligible for promotion
    char promo;
    // Check if pawn eligible for enpassant
    boolean enpass;
    // check if king eligible for castling
    boolean castling;

    public piece(char name, boolean isBlack) {
        this.name = name;
        this.isBlack = isBlack;
    }

    public boolean getIsBlack(){
        return this.isBlack;
    }
    public char getName() { return this.name; }
    public boolean isFirst_move() { return this.first_move; }
    public char getPromo() { return this.promo; }
    public boolean getEnpass() { return  this.enpass; }
    public boolean getCastling() {return this.castling; }

    // This methods validates whether a move on a given board for a given piece legal or not
    public abstract boolean valid_move(piece board[][], point origin, point destination);
}