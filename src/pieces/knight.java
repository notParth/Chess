package pieces;

import main.point;

/**
 * Represents a knight piece
 * @author Parth Patel
 * @author Amanda Kang
 */

public class knight extends piece{
    /**
     * Creates an instance of knight
     * @param isBlack the color of knight
     */
    public knight(boolean isBlack){ super("N", isBlack); }

    /**
     * Check if a move for the knight on a given board valid or not
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

        // check if landing on the same color piece
        if (board[destX][destY]!=null && board[destX][destY].getIsBlack() == getIsBlack())
            return false;

        // valid movement
        if (spacesX == 1 && spacesY == 2){
            return true;
        }
        return spacesX == 2 && spacesY == 1;
    }
}
