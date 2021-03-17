package pieces;
import main.*;
/*
 * @author Parth Patel
 * @author Amanda Kang
 */

public class pawn extends piece{

    // used for enpassant
    boolean double_step;

    public pawn(boolean isBlack) {
        super("pawn", isBlack);
        first_move = true;
        double_step = false;
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

        // check to make sure same colored pieces do not interact
        if (board[destX][destY]!=null && board[destX][destY].getIsBlack() == getIsBlack()) {
            enpass = false;
            return false;
        }

        // moving up or down spaces
        if (spacesY == 0 && board[destX][destY] == null) {
            if(spacesX == 1) {
                first_move = false;
                // promotion check
                if ((board[originX][originY].getIsBlack() && destX == 0)
                        || (!board[originX][originY].getIsBlack() && destX == 7))
                    // figure out
                enpass = false;
                return true;

            }
            if (spacesX == 2 && first_move) {
                int step = (originX + destX) / 2;
                // check if middle cell is empty
                if (board[step][originY] == null) {
                    first_move = false;
                    double_step = true;
                    enpass = false;
                    return true;
                }
            }
        }

        // check for diagonal capture + enpassant
        if (spacesX == 1 && spacesY == 1) {
            if(board[destX][destY]!=null && board[destX][destY].getIsBlack()!=getIsBlack()) {
                // promotion check
                if ((board[originX][originY].getIsBlack() && destX == 0)
                        || (!board[originX][originY].getIsBlack() && destX == 7))
                    // figure out
                enpass = false;
                return true;
            }
            // en passant
            piece adjacent = board[destX][originY];
            if (adjacent instanceof pawn && adjacent.getIsBlack()!=getIsBlack()
                    && ((pawn)adjacent).double_step){
                enpass = true;
                return true;
            }
        }
        enpass = false;
        return false;
    }
    public piece promotion() {
        // refer to the promotion variable to figure out which piece to promote to
        if (promo == 'R')
            return new rook(getIsBlack());
        else if (promo == 'N')
            return  new knight(getIsBlack());
        else if (promo == 'B')
            return new bishop(getIsBlack());
        else
            return new queen(getIsBlack());
    }
}
