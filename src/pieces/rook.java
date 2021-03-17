package pieces;

import main.point;

public class rook extends piece{
    public rook(boolean isBlack){ super('R', isBlack); }
    @Override
    public boolean valid_move(piece[][] board, point origin, point destination) {
        return false;
    }
}
