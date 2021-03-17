package pieces;

import main.point;

public class king extends piece{

    public king(boolean isBlack) { super('K', isBlack); }
    @Override
    public boolean valid_move(piece[][] board, point origin, point destination) {
        return false;
    }
}
