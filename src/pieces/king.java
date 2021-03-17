package pieces;

import main.point;

public class king extends piece{

    public king(boolean isBlack) {
        super("king", isBlack);
        first_move = true;
        castling = true;
    }
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
        if (board[destX][destY].getIsBlack() == getIsBlack())
            return false;

        if ((spacesX == 1 && spacesY == 0) || (spacesX == 0 && spacesY == 1) || (spacesX == 1 && spacesY == 1)){
            first_move = false;
            castling = false;
            return true;
        }
        // implement castling
        return false;
    }
}
