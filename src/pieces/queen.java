package pieces;

import main.point;

public class queen extends piece{

    public queen(boolean isBlack){ super('Q', isBlack); }
    @Override
    public boolean valid_move(piece[][] board, point origin, point destination) {
        return false;
    }
}
