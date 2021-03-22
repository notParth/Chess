package main;

import pieces.pawn;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class chess_two {
    board game;


    public chess_two() {
        game = new board();
        start();
    }

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
            else if (m3.matches()){
                origin = m3.group(1);
                destination = m3.group(2);
                terminate = true;
                winner = 'd';
            }
            // resign
            else if (input.equals("resign")){
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
            // check if not player operating the correct color piece
            if (game.b[start.getX()][start.getY()].getIsBlack() != turn) {
                System.out.println("Illegal move, try again.");
                continue;
            }
            if (promo) {
                game.b[start.getX()][start.getY()].setPromo(promotion);
            }
            if (game.b[start.getX()][start.getY()].valid_move(game.b, start, end)) {
                game.b[start.getX()][start.getY()].setPromo("");
                move_piece(start, end);
                turn = !turn;
                legal_move = true;
            }
            else
            {
                System.out.println("Illegal move, try again.");
                continue;
            }
            // check
            // checkmate
        }
        if (winner == 'w')
            System.out.println("White wins");
        else if (winner == 'b')
            System.out.println("Black wins");
        else if (winner == 'd')
            System.out.println("Draw");
    }
    public void move_piece(point origin, point dest) {
        game.b[dest.getX()][dest.getY()] = game.b[origin.getX()][origin.getY()];
        game.b[origin.getX()][origin.getY()] = null;
    }

    public static void main(String[] args) {
        chess_two game = new chess_two();
    }
}
