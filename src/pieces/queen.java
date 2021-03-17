package pieces;

import main.point;

public class queen extends piece{

    public queen(boolean isBlack){ super("queen", isBlack); }
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
        if (board[destX][destY].getIsBlack() == getIsBlack())
            return false;

        // check to make sure the movement is diagonal
        if (spacesX == spacesY) {
            // check to make sure the path is empty

            // movement is top and right
            if (destX > originX && destY > originY) {
                for (int i = originX, j = originY; i < destX && j < destY; i++, j++)
                    if (board[i][j] != null)
                        return false;
            }

            // movement is top and left
            if (destX > originX && destY < originY) {
                for (int i = originX, j = originY; i < destX && j > destY; i++, j--)
                    if (board[i][j] != null)
                        return false;
            }

            // movement is bottom and left
            if (destX < originX && destY < originY) {
                for (int i = originX, j = originY; i > destX && j > destY; i--, j--)
                    if (board[i][j] != null)
                        return false;
            }

            // movement is bottom and right
            if (destX < originX && destY > originY) {
                for (int i = originX, j = originY; i > destX && j < destY; i--, j++)
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
                    for (int i = originY; i < destY; i++)
                        if (board[originX][i] != null)
                            return false;
                }
                // moving left
                else {
                    for (int i = originY; i > destY; i--)
                        if (board[originX][i] != null)
                            return false;
                }

                return true;
            }
            if (spacesX > 0 && spacesY == 0) {
                // moving up
                if (originX < destX) {
                    for (int i = originX; i < destX; i++)
                        if (board[i][originY] != null)
                            return false;
                }
                // moving down
                else {
                    for (int i = originX; i > destX; i--)
                        if (board[i][originY] != null)
                            return false;
                }
                return true;
            }
        }
        return false;
    }
}
