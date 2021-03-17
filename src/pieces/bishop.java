package pieces;

import main.point;

public class bishop extends piece{

    public bishop(boolean isBlack){ super('B', isBlack); }
    @Override
    public boolean valid_move(piece[][] board, point origin, point destination) {
        return false;
    }
}
