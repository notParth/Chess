package pieces;

import main.point;

/**
 * Represents a king piece
 * @author Parth Patel
 * @author Amanda Kang
 */

public class king extends piece{
    /**
     * Creates an instance of king
     * @param isBlack color of king
     */
    public king(boolean isBlack) {
        super("K", isBlack);
        first_move = true;
    }

    /**
     * Check if a move for the king on a given board valid or not
     * @param board A chess board with a given state
     * @param origin The origin of the move
     * @param destination The destination of the move
     * @return
     */
    @Override
    public boolean valid_move(piece[][] board, point origin, point destination) {
        // coordinates for origin and destination
        int originX = origin.getX();
        int originY = origin.getY();
        int destX = destination.getX();
        int destY = destination.getY();

        // number of spaces moved
        int spacesX = Math.abs(originX - destX);
        int spacesY = Math.abs(originY - destY);

        // check to make sure not landing on same color piece
        if (board[destX][destY]!=null && board[destX][destY].getIsBlack() == getIsBlack())
            return false;

        if ((spacesX == 1 && spacesY == 0) || (spacesX == 0 && spacesY == 1) || (spacesX == 1 && spacesY == 1)){
            first_move = false;
            return true;
        }
        // implement castling
        if (spacesY == 2 && spacesX == 0 && first_move){
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
                    board[originX][originY-1] = board[originX][0];
                    board[originX][originY-1].first_move = false;
                    board[originX][0] = null;
                    return true;
                }
            }
            else {
                if (rightRook != null && rightRook instanceof rook
                        && rightRook.isFirst_move() && rightRook.getIsBlack() == getIsBlack()) {
                    for (int i = originY + 1; i < 7; i++) {
                        if (board[originX][i] != null)
                            return false;
                    }
                    first_move = false;
                    board[originX][originY+1] = board[originX][7];
                    board[originX][originY+1].first_move = false;
                    board[originX][7] = null;
                    return true;
                }
            }
        }

        return false;
    }
}
