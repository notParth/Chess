package pieces;
import main.*;

/**
 * Represent a chess piece
 * @author Parth Patel
 * @author Amanda Kang
 */

public abstract class piece {

    // 'p' for pawn, 'Q' for queen etc...
    String name;
    // True for a black piece, False for a white piece
    boolean isBlack;
    // Check if piece has made a first move yet
    boolean first_move;
    // Check if pawn eligible for promotion
    String promo;

    /**
     * Create a piece with a given name and coloe
     * @param name Can be anything from (K)ing, (Q)ueen, (p)awn, k(N)ight, (B)ishop or (R)ook
     * @param isBlack true for black piece and false for white
     */
    public piece(String name, boolean isBlack) {
        this.name = name;
        this.isBlack = isBlack;
    }

    /**
     * @return The color of the piece
     */
    public boolean getIsBlack(){
        return this.isBlack;
    }

    /**
     * @return The name of the piece
     */
    public String getName() { return this.name; }

    /**
     * @return Whether or not the piece has taken its first move.
     */
    public boolean isFirst_move() { return this.first_move; }

    /**
     * Used for promotion of a pawn
     * @return the piece pawn gets promoted to
     */
    public String getPromo() { return this.promo; }

    public void setPromo(String promo) { this.promo = promo; }



    @Override
    public String toString() {
        if (isBlack) {
            return "black " + this.name;
        }
        return "white " + this.name;
    }

    // This methods validates whether a move on a given board for a given piece legal or not

    /**
     * This method validates whether a move on a given board for a given piece is legal or not
     * @param game A chess board with a given state
     * @param origin The origin of the move
     * @param destination The destination of the move
     * @return Whether or not the move is valid
     */
    public abstract boolean valid_move(board game, point origin, point destination);
    public abstract piece copy();
}