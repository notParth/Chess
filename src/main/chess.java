package main;
/*
import pieces.king;
import pieces.pawn;
import pieces.piece;

import java.util.Scanner;

public class chess {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("Start game? (yes/no)");
            if (s.nextLine().equals("yes")) {
                board game = new board();
                boolean isCheck = false;
                boolean isCheckmate = false;
                int moveCount = 1;
                while (!isCheckmate) {
                    boolean moveInProgress = true;
                    while (moveInProgress) {
                        boolean capture = false;
                        boolean isBlack = moveCount % 2 != 1;
                        game.display();
                        //get user input
                        String color = isBlack ? "black" : "white";
                        System.out.println("Move "+moveCount+"--Player 1 ("+color+"), please enter your move in the format origin,destination' (e.g. a2,a4):");
                        String move = s.nextLine();
                        String[] points = move.split(",");
                        point origin = new point(points[0]);
                        point destination = new point(points[1]);
                        System.out.println("User is trying to move "+ game.getPiece(origin)+" from "+origin + " to " + destination);

                        //check if move is in bounds of board
                        if(origin.getX()<0 || origin.getX()>7 || origin.getY()<0 || origin.getY()>7){
                            System.out.println("This move is not valid; it is out of bounds. Please re-enter your move.");
                            continue;
                        }

                        //check if existing piece at origin
                        if (game.getPiece(origin) == null || (game.getPiece(origin).getIsBlack()!=isBlack)) {
                            System.out.println("This move is not valid; a white piece does not exist at " + origin + ". Please re-enter your move.");
                            continue;
                        }

                        //check for capture
                        if(game.getPiece(destination) !=null && (game.getPiece(destination).getIsBlack()!=isBlack)){ //if existing enemy piece at destination
                            capture = true;
                        }

                        boolean enpass = false; //handle en passant
                        piece adjacent = game.b[origin.getX()][destination.getY()]; //if en passant
                        if(adjacent instanceof pawn ){ // && adjacent.getEnpass()
                            enpass = true;
                        }

                        //check if move is valid
                        boolean isValid = game.b[origin.getX()][origin.getY()].valid_move(game.b, origin, destination);
                        if(!isValid){
                            System.out.println("This move is not valid. Please re-enter your move.");
                            continue;
                        }

                        if(game.b[origin.getX()][destination.getY()]!=null){
                            enpass = false;
                        }

                        //print result of move (note: implement capture)
                        String c = capture ? "; captured "+game.getPiece(destination) : "";
                        c = enpass ? "; enpass: captured "+(game.getPiece(origin).getIsBlack() ? "white p" : "black p") : c;
                        System.out.println("Successfully moved "+game.getPiece(origin)+" from "+origin+" to " +destination+c+"\n");

                        //move piece
                        game.b[destination.getX()][destination.getY()] = game.b[origin.getX()][origin.getY()];
                        game.b[origin.getX()][origin.getY()] = null;


                        //check
                        point kingPos = null;
                        for(int i=0; i<8; i++){ //set enpass = false
                            for(int j=0; j<8; j++){
                                if(game.b[i][j] instanceof king && (game.b[i][j].getIsBlack()!=isBlack)){
                                    kingPos = new point(i,j);
                                    isCheck = check(game.b, kingPos);
                                    break;
                                }
                            }
                        }

                        //checkmate
                        if(isCheck){
                            System.out.println("Check!!");
                            isCheckmate = checkmate(game.b, kingPos);
                            if(isCheckmate){
                                System.out.println("Checkmate!! Game over, "+color+" wins!!");
                                break;
                            }
                        }



                        //set vars
                        for(int i=0; i<8; i++){ //set enpass = false
                            for(int j=0; j<8; j++){
                                if(game.b[i][j] instanceof pawn && (game.b[i][j].getIsBlack()!=isBlack)){
                                    // game.b[i][j].enpass = false;
                                }
                            }
                        }
                        moveInProgress = false;
                        moveCount++;
                    }


                }
            }
        }
    }

    public static boolean check(piece[][] board, point destination){
        for(int i=0; i<8; i++){ //set enpass = false
            for(int j=0; j<8; j++){
                if(board[i][j] != null && (board[i][j].getIsBlack() != board[destination.getX()][destination.getY()].getIsBlack())){
                    point origin = new point(i,j);
                    if(board[i][j].valid_move(board, origin, destination)){
                        //System.out.println("CHECK!!!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkmate(piece[][]board, point destination){
        boolean isBlack = board[destination.getX()][destination.getY()].getIsBlack();
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j] != null && (board[i][j].getIsBlack() == board[destination.getX()][destination.getY()].getIsBlack())){ //samecolored pieces
                    for(int k=0; k<8; k++){
                        for(int l=0; l<8; l++){
                            point hOrig = new point(i,j);
                            point hDest = new point(k,l);
                            if(board[i][j].valid_move(board, hOrig, hDest)){ //valid move
                                board[hDest.getX()][hDest.getY()] = board[hOrig.getX()][hOrig.getY()];
                                board[hOrig.getX()][hOrig.getY()] = null;
                                //find kingpos
                                point kingPos = null;
                                for(int a=0; a<8; a++){ //set enpass = false
                                    for(int b=0; b<8; b++){
                                        if(board[a][b] instanceof king && (isBlack==board[a][b].getIsBlack())){
                                            kingPos = new point(a,b);
                                            break;
                                        }
                                    }
                                }
                                boolean isCheck = check(board, kingPos);
                                board[hOrig.getX()][hOrig.getY()] = board[hDest.getX()][hDest.getY()];
                                board[hDest.getX()][hDest.getY()] = null;
                                if(!isCheck){
                                    System.out.println("Escaped checkmate with "+board[hOrig.getX()][hOrig.getY()]+" from "+hOrig+" to " +hDest);
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
*/