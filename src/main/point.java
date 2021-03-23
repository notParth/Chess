package main;


/**
 * Represents a point on the board
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class point {
    int x;
    int y;


    /**
     * Creates an instance of point.
     *
     * @param x the x-coordinate of the point.
     * @param y the y-coordinate of the point.
     */
    public point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates an instance of point.
     *
     * @param s the string denoting a location on the chessboard (e.g. a2)
     */
    public point(String s) {
        this.x = s.charAt(1) - 49;
        this.y = s.charAt(0) - 97;
    }

    /**
     * Gets the x coordinate.
     *
     * @return the x coordinate.
     */
    public int getX() {
        return this.x;
    }


    /**
     * Gets the y coordinate.
     *
     * @return the y coordinate.
     */
    public int getY() {
        return this.y;
    }


    /**
     * Converts a point to String form.
     *
     * @return A String denoting the given point on the chessboard (e.g. a2 (1,0)
     */
    @Override
    public String toString() {
        return "" + (char) (y + 97) + (char) (x + 49) + " (" + x + "," + y + ")";
    }
}
