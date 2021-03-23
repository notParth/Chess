package pieces;

import main.board;
import main.point;

import static main.chess_two.check;

/**
 * Represents a knight piece
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class knight extends piece {
    /**
     * Creates an instance of knight
     *
     * @param isBlack the color of knight
     */
    public knight(boolean isBlack) {
        super("N", isBlack);
    }

    /**
     * Creates a copy of a given piece.
     *
     * @return The new piece.
     */
    public piece copy() {
        knight newPiece = new knight(this.isBlack);
        newPiece.first_move = this.first_move;
        newPiece.promo = this.promo;

        return newPiece;
    }


    /**
     * Check if a move for the knight on a given board valid or not
     *
     * @param game        A chess board with a given state
     * @param origin      The origin of the move
     * @param destination The destination of the move
     * @return
     */
    @Override
    public boolean valid_move(board game, point origin, point destination) {


        piece[][] board = game.b;

        board newGame = game.copyBoard();
        //simulate move
        newGame.b[destination.getX()][destination.getY()] = newGame.b[origin.getX()][origin.getY()];
        newGame.b[origin.getX()][origin.getY()] = null;

        //if move leads to check
        point kingPos = null;
        for (int i = 0; i < 8; i++) { //set enpass = false
            for (int j = 0; j < 8; j++) {
                if (newGame.b[i][j] != null && newGame.b[i][j] instanceof king && (newGame.b[i][j].getIsBlack() == game.b[origin.getX()][origin.getY()].isBlack)) {
                    kingPos = new point(i, j);
                    if (check(newGame, kingPos)) {
                        //System.out.println("Cannot make this move because it puts your king in check: Knight");
                        return false;
                    }
                }
            }
        }

        // coordinates for origin and destination
        int originX = origin.getX();
        int originY = origin.getY();
        int destX = destination.getX();
        int destY = destination.getY();

        // number of spaces moved
        int spacesX = Math.abs(originX - destX);
        int spacesY = Math.abs(originY - destY);

        // check if landing on the same color piece
        if (board[destX][destY] != null && board[destX][destY].getIsBlack() == getIsBlack())
            return false;

        // valid movement
        if (spacesX == 1 && spacesY == 2) {
            return true;
        }
        return spacesX == 2 && spacesY == 1;
    }
}
