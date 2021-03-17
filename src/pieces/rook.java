package pieces;

import main.point;

public class rook extends piece{
    public rook(boolean isBlack){
        super("rook", isBlack);
        first_move = true;
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

        //  check to make sure the move is unidirectional (unnecessary)
        if (spacesX > 0 && spacesY > 0)
            return false;
        // check to make sure not landing on same color piece
        if (board[destX][destY]!=null && board[destX][destY].getIsBlack() == getIsBlack())
            return false;
        // check to make sure no obstacles in between
        if (spacesX == 0 && spacesY > 0) {
            // moving right
            if (originY < destY) {
                for (int i = originY+1; i < destY; i++)
                    if (board[originX][i] != null)
                        return false;
            }
            // moving left
            else {
                for (int i = originY+1; i > destY; i--)
                    if (board[originX][i] != null)
                        return false;
            }
            first_move = false;
            return true;
        }
        if (spacesX > 0 && spacesY == 0) {
            // moving up
            if (originX < destX) {
                for (int i = originX+1; i < destX; i++)
                    if (board[i][originY] != null)
                        return false;
            }
            // moving down
            else {
                for (int i = originX+1; i > destX; i--)
                    if (board[i][originY] != null)
                        return false;
            }
            first_move = false;
            return true;
        }
        return false;
    }
}
