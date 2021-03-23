package pieces;

import main.*;

import static main.chess_two.check;

/**
 * Represents a pawn piece
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class pawn extends piece {

    // used for enpassant
    boolean double_step;

    /**
     * Creates an instance of pawn
     *
     * @param isBlack color of the pawn
     */
    public pawn(boolean isBlack) {
        super("p", isBlack);
        first_move = true;
        double_step = false;
    }

    /**
     * Creates a copy of a given piece.
     *
     * @return The new piece.
     */
    @Override
    public piece copy() {
        pawn newPiece = new pawn(this.isBlack);
        newPiece.first_move = this.first_move;
        newPiece.promo = this.promo;
        newPiece.double_step = this.double_step;

        return newPiece;
    }

    /**
     * Check if a move for the pawn on a given board valid or not
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

        // check for diagonal capture + enpassant
        if (spacesX == 1 && spacesY == 1) {
            if (board[destX][destY] != null && board[destX][destY].getIsBlack() != getIsBlack()) {
                // promotion check
                piece tempPawnProm = board[originX][originY];
                if ((board[originX][originY].getIsBlack() && destX == 0)
                        || (!board[originX][originY].getIsBlack() && destX == 7))
                    board[originX][originY] = promotion();
                first_move = false;

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
                                board[originX][originY] = tempPawnProm;
                                //System.out.println("Cannot make this move because it puts your king in check: Pawn");
                                return false;
                            }
                        }
                    }
                }

                return true;
            }
            // en passant
            piece adjacent = board[originX][destY];
            if (adjacent != null && adjacent instanceof pawn && adjacent.getIsBlack() != getIsBlack() && ((pawn) adjacent).double_step) {
                piece tempPawnProm = board[originX][originY];
                piece tempPawnEP = board[originX][destY];
                board[originX][destY] = null;
                if ((board[originX][originY].getIsBlack() && destX == 0) || (!board[originX][originY].getIsBlack() && destX == 7)) {
                    board[originX][originY] = promotion();
                }

                first_move = false;


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
                                board[originX][originY] = tempPawnProm;
                                board[originX][destY] = tempPawnEP; //restore captured en passant pawn
                                //System.out.println("Cannot make this move because it puts your king in check: Pawn");
                                return false;
                            }
                        }
                    }
                }

                return true;
            }
        }

        // check to make sure same colored pieces do not interact
        if (board[destX][destY] != null && board[destX][destY].getIsBlack() == getIsBlack()) {
            return false;
        }

        // moving up or down spaces
        if (spacesY == 0 && board[destX][destY] == null) {//cant move backwards
            if ((originX - destX < 0) && board[originX][originY].getIsBlack()) {
                return false;
            } else if (originX - destX > 0 && !board[originX][originY].getIsBlack()) {
                return false;
            }
            if (spacesX == 1) {
                first_move = false;
                // promotion check
                piece tempPawnProm = board[originX][originY];
                if ((board[originX][originY].getIsBlack() && destX == 0)
                        || (!board[originX][originY].getIsBlack() && destX == 7))
                    board[originX][originY] = promotion();


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
                                board[originX][originY] = tempPawnProm; //undo promotion
                                //System.out.println("Cannot make this move because it puts your king in check: Pawn");
                                return false;
                            }
                        }
                    }
                }
                return true;

            }
            if (spacesX == 2 && first_move) {
                int step = (originX + destX) / 2;
                // check if middle cell is empty
                if (board[step][originY] == null) {
                    first_move = false;
                    double_step = true;

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
                                    //System.out.println("Cannot make this move because it puts your king in check: Pawn");
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

    /**
     * This method is used for promotion of a pawn
     *
     * @return the piece the player wants to promote the pawn to
     */
    public piece promotion() {
        // refer to the promotion variable to figure out which piece to promote to
        if (promo.equals("R"))
            return new rook(getIsBlack());
        else if (promo.equals("N"))
            return new knight(getIsBlack());
        else if (promo.equals("B"))
            return new bishop(getIsBlack());
        else
            return new queen(getIsBlack());
    }
}
