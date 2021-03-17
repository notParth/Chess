package pieces;

import main.point;

public class knight extends piece{

    public knight(boolean isBlack){ super('N', isBlack); }
    @Override
    public boolean valid_move(piece[][] board, point origin, point destination) {
        return false;
    }
}
