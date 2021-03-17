package main;
import pieces.*;
public class board {

    // 2D array
    public piece board[][];

    public board() {
        board = new piece[8][8];

        // setting the black pieces
        board[0][0] = new pawn(true);
    }
}
