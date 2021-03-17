package main;

import pieces.piece;

import java.util.Scanner;

public class chess {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("Start game? (yes/no)");
            if (s.nextLine().equals("yes")) {
                board game = new board();
                int moveCount = 1;
                while (!game.isCheckmate()) {
                    boolean moveInProgress = true;
                    while (moveInProgress) {
                        boolean capture = false;
                        //get user input
                        System.out.println("Move "+moveCount+"--Player 1 (white), please enter your move in the format origin,destination' (e.g. a2,a4):");
                        String move = s.nextLine();
                        String[] points = move.split(",");
                        point origin = new point(points[0]);
                        point destination = new point(points[1]);
                        System.out.println(origin + "" + destination);

                        //check if existing piece at origin
                        if (game.getPiece(origin) == null || game.getPiece(origin).getIsBlack()) {
                            System.out.println("This move is not valid; a white piece does not exist at " + origin + ". Please re-enter your move:");
                            continue;
                        }

                        //check if existing enemy piece at destination
                        if(game.getPiece(destination) !=null && game.getPiece(destination).getIsBlack()){
                            capture = true;
                        }

                        //check if move is valid
                        boolean isValid = game.b[origin.getX()][origin.getY()].valid_move(game.b, origin, destination);
                        System.out.println("isValid: "+isValid);//debug
                        if(!isValid){
                            System.out.println("This move is not valid.");
                            continue;
                        }

                        //print result of move (note: implement capture)
                        String c = capture ? "; captured "+game.getPiece(destination) : "";
                        System.out.println("Successfully moved "+game.getPiece(origin)+" from "+origin+" to " +destination+c);
                        moveInProgress = false;
                        moveCount++;
                    }
                    moveInProgress = true;
                    while (moveInProgress){
                        boolean capture = false;
                        System.out.println("Player 1 (black), please enter your move in the format origin,destination' (e.g. a2,a4):");
                        moveInProgress = false;
                    }

                    }
                }
            }
        }


    }
