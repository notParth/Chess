package main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pieces.king;
import pieces.pawn;
import pieces.piece;

/**
 * Handles the chess game
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class Chess {
    board game;


    /**
     * Sets up board and initializes game.
     */
    public Chess() {
        game = new board();
        start();
    }

    /**
     * Runs the game.
     */
    public void start() {
        Scanner scn = new Scanner(System.in);
        String input;
        String pattern_one = "^([a-h][1-8])\\s([a-h][1-8])$";
        String pattern_two = "^([a-h][1-8])\\s([a-h][1-8])\\s([RNBQ])$";
        String pattern_three = "^([a-h][1-8])\\s([a-h][1-8])\\sdraw\\?$";
        Pattern input_parser = Pattern.compile(pattern_one);
        Pattern input_parser_two = Pattern.compile(pattern_two);
        Pattern input_parser_three = Pattern.compile(pattern_three);
        // false = white's turn, true = black's turn
        boolean turn = false;
        // w = white wins, b = black wins, d = draw
        char winner = ' ';
        boolean terminate = false;
        boolean legal_move = true;
        boolean promo = false;
        boolean isCheck = false;
        boolean draw = false;
        while (!terminate) {
            if (legal_move) {
                System.out.println();
                game.display();
            }
            legal_move = false;
            if (!turn)
                System.out.print("White's move: ");
            else
                System.out.print("Black's move: ");
            input = scn.nextLine();
            Matcher m1 = input_parser.matcher(input);
            Matcher m2 = input_parser_two.matcher(input);
            Matcher m3 = input_parser_three.matcher(input);
            String origin = "", destination = "", promotion = "";
            // regular move
            if (m1.matches()) {
                origin = m1.group(1);
                destination = m1.group(2);
            }
            // promotion move
            else if (m2.matches()) {
                origin = m2.group(1);
                destination = m2.group(2);
                promotion = m2.group(3);
                promo = true;
            }
            // draw
            else if (m3.matches()) {
                origin = m3.group(1);
                destination = m3.group(2);
                draw = true;
            }
            // resign
            else if (input.equals("resign")) {
                terminate = true;
                if (!turn)
                    winner = 'b';
                else
                    winner = 'w';
                continue;
            }
            // wrong
            else {
                System.out.println("Illegal move, try again.");
                continue;
            }
            point start = new point(origin);
            point end = new point(destination);
            //debug


            // check if not player operating the correct color piece
            if (game.b[start.getX()][start.getY()] == null || game.b[start.getX()][start.getY()].getIsBlack() != turn) {
                System.out.println("Illegal move, try again.");
                continue;
            }
            if (promo) {
                game.b[start.getX()][start.getY()].setPromo(promotion);
            }

            if (game.b[start.getX()][start.getY()].valid_move(game, start, end)) {
                game.b[start.getX()][start.getY()].setPromo("");
                move_piece(start, end);
                turn = !turn;
                legal_move = true;
                if (draw) {
                    terminate = true;
                    winner = 'd';
                }
            } else {
                System.out.println("Illegal move, try again.");
                continue;
            }

            //    System.out.println("successfully made move");
            // check
            point kingPos = null;
            for (int i = 0; i < 8; i++) { //set enpass = false
                for (int j = 0; j < 8; j++) {
                    if (game.b[i][j] instanceof king && (game.b[i][j].getIsBlack() == turn)) {
                        kingPos = new point(i, j);
                        //       System.out.println(game.b[i][j]+" king at");
                        isCheck = check(game, kingPos);
                        break;
                    }
                }
            }
            //  System.out.println(isCheck+"is check???");

            // checkmate
            if (isCheck) {
                System.out.println("Check");
                terminate = checkmate(game, kingPos);
                if (terminate) {
                    System.out.println("Checkmate");
                    winner = turn ? 'w' : 'b';
                    break;
                }
            }
            // turn double jump to false for enpassant
            // if white's turn, turn black's pawns to false for enpassant and vice versa
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (game.b[i][j] != null && game.b[i][j].getIsBlack() != turn && game.b[i][j] instanceof pawn) {
                        ((pawn) game.b[i][j]).double_step = false;
                    }
                }
            }
        }
        if (winner == 'w')
            System.out.println("White wins");
        else if (winner == 'b')
            System.out.println("Black wins");
        else if (winner == 'd')
            System.out.println("draw");
    }

    /**
     * Moves a piece given its origin and destination coordinates.
     *
     * @param origin The original position of the piece.
     * @param dest   The destination position of the piece.
     */
    public void move_piece(point origin, point dest) {
        game.b[dest.getX()][dest.getY()] = game.b[origin.getX()][origin.getY()];
        game.b[origin.getX()][origin.getY()] = null;
    }

    /**
     * Main method, creates an instance of Chess.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Chess game = new Chess();
    }

    /**
     * Tests if a given king is in check.
     *
     * @param game        The current state of the board.
     * @param destination The location of the given king.
     * @return A boolean value representing if the king is in check (true) or not (false).
     */
    public static boolean check(board game, point destination) {
        piece[][] board = game.b;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && (board[i][j].getIsBlack() != board[destination.getX()][destination.getY()].getIsBlack())) {
                    //System.out.println(board[i][j]);
                    point origin = new point(i, j);
                    if (board[i][j].valid_move(game, origin, destination)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Tests if a given king is in checkmate.
     *
     * @param game        The current state of the board.
     * @param destination The location of the given king.
     * @return A boolean value representing if the king is in checkmate (true) or not (false)
     */
    public static boolean checkmate(board game, point destination) {
        piece[][] board = game.b;
        boolean isBlack = board[destination.getX()][destination.getY()].getIsBlack();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && (board[i][j].getIsBlack() == board[destination.getX()][destination.getY()].getIsBlack())) { //samecolored pieces
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            point hOrig = new point(i, j);
                            point hDest = new point(k, l);
                            if (board[i][j].valid_move(game, hOrig, hDest)) { //valid move
                                board[hDest.getX()][hDest.getY()] = board[hOrig.getX()][hOrig.getY()];
                                board[hOrig.getX()][hOrig.getY()] = null;
                                //find kingpos
                                point kingPos = null;
                                for (int a = 0; a < 8; a++) {
                                    for (int b = 0; b < 8; b++) {
                                        if (board[a][b] instanceof king && (isBlack == board[a][b].getIsBlack())) {
                                            kingPos = new point(a, b);
                                            break;
                                        }
                                    }
                                }
                                boolean isCheck = check(game, kingPos);
                                board[hOrig.getX()][hOrig.getY()] = board[hDest.getX()][hDest.getY()];
                                board[hDest.getX()][hDest.getY()] = null;
                                if (!isCheck) {
                                    return false;
                                }
                            }

                        }
                    }

                }
            }
        }
        return true;
    }
}
