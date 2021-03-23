package pieces;

import main.point;

/**
 * Represents a bishop piece
 * @author Parth Patel
 * @author Amanda Kang
 */

public class bishop extends piece{
    /**
     * Creates an instance of bishop
     * @param isBlack the color of bishop
     */
    public bishop(boolean isBlack){
        super("B", isBlack);
    }

    /**
     * Check if a move for the bishop on a given board valid or not
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

        // check to make sure not attacking same color piece
        if (board[destX][destY]!=null && board[destX][destY].getIsBlack() == getIsBlack())
            return false;

        // check to make sure the movement is diagonal
        if (spacesX == spacesY) {
            // check to make sure the path is empty

            // movement is top and right
            // movement is top and right
            if (destX > originX && destY > originY) {
                for (int i = originX+1, j = originY+1; i < destX && j < destY; i++, j++)
                    if (board[i][j] != null)
                        return false;
            } else if (destX > originX && destY < originY) { // movement is top and left
                for (int i = originX+1, j = originY-1; i < destX && j > destY; i++, j--)
                    if (board[i][j] != null)
                        return false;
            }else if (destX < originX && destY < originY) {// movement is bottom and left
                for (int i = originX-1, j = originY-1; i > destX && j > destY; i--, j--)
                    if (board[i][j] != null)
                        return false;
            }else {// movement is bottom and right
                for (int i = originX-1, j = originY+1; i > destX && j < destY; i--, j++)
                    if (board[i][j] != null)
                        return false;
            }

            return  true;
        }
        return false;
    }
}
