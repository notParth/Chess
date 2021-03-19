package pieces;

import main.point;

public class queen extends piece{

    public queen(boolean isBlack){ super("Q", isBlack); }
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
            return true;
        }
        // movement is unidirectional
        else {
            if (spacesX == 0 && spacesY > 0) {
                // moving right
                if (originY < destY) {
                    for (int i = originY+1; i < destY; i++)
                        if (board[originX][i] != null)
                            return false;
                }
                // moving left
                else {
                    for (int i = originY-1; i > destY; i--)
                        if (board[originX][i] != null)
                            return false;
                }

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
                    for (int i = originX-11; i > destX; i--)
                        if (board[i][originY] != null)
                            return false;
                }
                return true;
            }
        }
        return false;
    }
}
