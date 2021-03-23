package pieces;

import main.board;
import main.point;

import static main.chess_two.check;

/**
 * Represents a king piece
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class king extends piece {
    /**
     * Creates an instance of king
     *
     * @param isBlack color of king
     */
    public king(boolean isBlack) {
        super("K", isBlack);
        first_move = true;
    }

    /**
     * Creates a copy of a given piece.
     *
     * @return The new piece.
     */
    @Override
    public piece copy() {
        king newPiece = new king(this.isBlack);
        newPiece.first_move = this.first_move;
        newPiece.promo = this.promo;

        return newPiece;
    }

    /**
     * Check if a move for the king on a given board valid or not
     *
     * @param game        A chess board with a given state
     * @param origin      The origin of the move
     * @param destination The destination of the move
     * @return
     */
    @Override
    public boolean valid_move(board game, point origin, point destination) {

        piece[][] board = game.b;
        // coordinates for origin and destination
        int originX = origin.getX();
        int originY = origin.getY();
        int destX = destination.getX();
        int destY = destination.getY();

        // number of spaces moved
        int spacesX = Math.abs(originX - destX);
        int spacesY = Math.abs(originY - destY);

        // check to make sure not landing on same color piece
        if (board[destX][destY] != null && board[destX][destY].getIsBlack() == getIsBlack())
            return false;

        if ((spacesX == 1 && spacesY == 0) || (spacesX == 0 && spacesY == 1) || (spacesX == 1 && spacesY == 1)) {
            first_move = false;
            board newGame = game.copyBoard();
            //simulate move
            newGame.b[destination.getX()][destination.getY()] = newGame.b[origin.getX()][origin.getY()];
            newGame.b[origin.getX()][origin.getY()] = null;

            //if move leads to check
            point kingPos = null;
            for (int i = 0; i < 8; i++) { //set enpass = false
                for (int j = 0; j < 8; j++) {
                    if (newGame.b[i][j] != null && newGame.b[i][j] instanceof king && (newGame.b[i][j].getIsBlack() != newGame.b[origin.getY()][origin.getY()].isBlack)) {
                        kingPos = new point(i, j);
                        if (check(newGame, kingPos)) {
                            //System.out.println("Cannot make this move because it puts your king in check: King");
                            return false;
                        }
                    }
                }
            }

            return true;
        }
        // implement castling
        if (spacesY == 2 && spacesX == 0 && first_move) {
            //check for check; if check can't castle
            point kingPos1 = null;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (game.b[i][j] != null && game.b[i][j] instanceof king && (game.b[i][j].getIsBlack() == game.b[origin.getX()][origin.getY()].isBlack)) {
                        kingPos1 = new point(i, j);
                        if (check(game, kingPos1)) {
                            return false;
                        }
                    }
                }
            }


            piece rightRook = board[originX][7];
            piece leftRook = board[originX][0];
            if (originY > destY) {
                if (leftRook != null && leftRook instanceof rook
                        && leftRook.isFirst_move() && leftRook.getIsBlack() == getIsBlack()) {
                    for (int i = originY - 1; i > 0; i--) {
                        if (board[originX][i] != null)
                            return false;
                    }
                    first_move = false;
                    board[originX][originY - 1] = board[originX][0];
                    board[originX][originY - 1].first_move = false;
                    board[originX][0] = null;


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
                                    board[originX][0] = board[originX][originY - 1]; //undo castle
                                    return false;
                                }
                            }
                        }
                    }
                    return true;
                }
            } else {
                if (rightRook != null && rightRook instanceof rook
                        && rightRook.isFirst_move() && rightRook.getIsBlack() == getIsBlack()) {
                    for (int i = originY + 1; i < 7; i++) {
                        if (board[originX][i] != null)
                            return false;
                    }
                    first_move = false;
                    board[originX][originY + 1] = board[originX][7];
                    board[originX][originY + 1].first_move = false;
                    board[originX][7] = null;

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
                                    //system.out.println("Cannot make this move because it puts your king in check: King");
                                    return false;
                                }
                            }
                        }
                    }
                    return true;
                }
            }
        }

        return false;
    }
}
