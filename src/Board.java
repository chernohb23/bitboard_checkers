import java.util.Objects;

public class Board {
    char[][] board = new char[8][8];     // array that holds the board
    String bitboard1;                            // player 1's bitboard
    String bitboard2;                          // player 2's bitboard
    String bitboard3;                               // bitboard to handle kings
    String turn;                       // variable that tracks turns
    int row1;
    int column1;
    int row2;
    int column2;
    boolean finished = false;                  // variable that determines when or not the game is finished
    public Board(){                                             // constructor, basically the "InitializeBoard" method, sets the board up
        bitboard1 = "00000000000000000000111111111111";
        bitboard2 = "11111111111100000000000000000000";
        bitboard3 = "00000000000000000000000000000000";
        turn = "player1";
        updateGameState();
    }
    public void nextTurn(){                                       // method that changes the turns as each move (if the move is valid)
        if (Objects.equals(turn, "player1")){
            turn = "player2";
        }
        else{
            turn = "player1";
        }
    }
    public void movePieces(String a, String b){         // method that moves the pieces
        boolean moved = false;                              // variable moved determines if a piece has been successfully moved
        char char1 = a.charAt(0);                           // converts input to row and column values, then checks what values they represent
        if(char1 == 'A'){
            row1 = 0;
        }
        else if(char1 == 'B'){
            row1 = 1;
        }
        else if (char1 == 'C'){
            row1 = 2;
        }
        else if (char1 == 'D'){
            row1 = 3;
        }
        else if (char1 == 'E'){
            row1 = 4;
        }
        else if (char1 == 'F'){
            row1 = 5;
        }
        else if (char1 == 'G'){
            row1 = 6;
        }
        else if (char1 == 'H'){
            row1 = 7;
        }
        column1 = Integer.parseInt(String.valueOf(a.charAt(1)));

        char char2 = b.charAt(0);
        if(char2 == 'A'){
            row2 = 0;
        }
        else if(char2 == 'B'){
            row2 = 1;
        }
        else if (char2 == 'C'){
            row2 = 2;
        }
        else if (char2 == 'D'){
            row2 = 3;
        }
        else if (char2 == 'E'){
            row2 = 4;
        }
        else if (char2 == 'F'){
            row2 = 5;
        }
        else if (char2 == 'G'){
            row2 = 6;
        }
        else if (char2 == 'H'){
            row2 = 7;
        }
        column2 = Integer.parseInt(String.valueOf(b.charAt(1)));

        if(board[row1][column1] != '1'){                                                  // makes sure the first input is actually a movable piece, if not, prints message below
            System.out.println("You did not choose a piece on the board to move.");
        }
        else{
            int pos = Utility.getBitPosition(row1, column1);                              // pos is the initial position of the piece
            int pos1 = Utility.getBitPosition(row2, column2);                         // pos1 is the destination position that the piece is trying to move to
            char check;                                                            // same exact stuff but for player 1
            if(Objects.equals(turn, "player2")){                                    // checks if the piece chosen belongs to the player
                check = bitboard2.charAt(Utility.bitSubtraction(31, pos));
                if (check == '1') {
                    if(board[row2][column2] == '1'){                                                         // checks if there's already a piece there, if not, prints message below
                        System.out.println("There is already a piece where you want to move.");
                    }
                    else if (board[row2][column2] != '0'){                                        // checks if it's a playable space, if not, prints message below
                        System.out.println("That is not a valid position to move to.");
                    }
                    else{
                        if(isLegal(pos, pos1)){                               // checks if the move is legal boundary wise
                            if(checkCapture(pos, pos1)){                   // checks if a capture can be made
                                capturePiece(pos, pos1);                      // if capture can be made, then capture will be made
                            }
                            if(checkKing(pos)){                                                               // checks if the piece moving is a king piece
                                int intdecimal = Integer.parseInt(Utility.toDecimalString(bitboard3));        // if the piece moving is a king, the move will be reflected on the king board for tracking
                                    int clear = Utility.clearBit(intdecimal, pos);
                                    int set = Utility.setBit(clear, pos1);
                                    bitboard3 = Utility.toBinaryString(set);
                            }
                            int intdecimal = Integer.parseInt(Utility.toDecimalString(bitboard2));         // regardless of whether the piece is a king, the player 1 and player 2 boards will be modified to reflect the move
                            int clear = Utility.clearBit(intdecimal, pos);                              // clear the initiate position
                            int set = Utility.setBit(clear, pos1);                                        // set the new position that the piece is moving to
                            bitboard2 = Utility.toBinaryString(set);

                            potentialKing(pos1);                    // checks if the position that the piece has just moved to would turn the piece into a king piece
                            updateGameState();               // updates the board and checks for game ending conditions
                            moved = true;                 // once the piece has been moved and the board has been updated, moved becomes true
                        }
                        else{
                            System.out.println("The position you are trying to move to is out of bounds.");          // if the move is not legal boundary wise then this message prints
                        }
                    }
                } else {
                    System.out.println("The piece you are trying to move does not belong to you.");             // if the piece trying to be moved does not belong to the player then this message prints
                }
            }
            else{                                                                                      // same stuff but for player 1
                check = bitboard1.charAt(Utility.bitSubtraction(31, pos));
                if (check == '1') {
                    if(board[row2][column2] == '1'){
                        System.out.println("There is already a piece where you want to move.");
                    }
                    else if (board[row2][column2] != '0'){
                        System.out.println("That is not a valid position to move to.");
                    }
                    else{
                        if(isLegal(pos, pos1)){
                            if(checkCapture(pos, pos1)){
                                if(checkCapture(pos, pos1)){
                                    capturePiece(pos, pos1);
                                }
                            }
                            if(checkKing(pos)){
                                int intdecimal = Integer.parseInt(Utility.toDecimalString(bitboard3));
                                int clear = Utility.clearBit(intdecimal, pos);
                                int set = Utility.setBit(clear, pos1);
                                bitboard3 = Utility.toBinaryString(set);
                            }
                            int intdecimal = Integer.parseInt(Utility.toDecimalString(bitboard1));
                            int clear = Utility.clearBit(intdecimal, pos);
                            int set = Utility.setBit(clear, pos1);
                            bitboard1 = Utility.toBinaryString(set);

                            potentialKing(pos1);
                            updateGameState();
                            moved = true;
                        }
                        else{
                            System.out.println("The position you are trying to move to is out of bounds.");
                        }
                    }
                } else {
                    System.out.println("The piece you are trying to move does not belong to you.");
                }
            }
            if(moved){                      // if the move was successful, then the turn switches
                nextTurn();
            }
        }
    }
    public boolean isLegal(int pos, int pos1){                       // this method checks if the move trying to be made is legal in terms of boundary (too far away, not on board, backwards, ect.), takes in initial position and destination position
        boolean legal = false;                     // legal variable, which will determine is the move is legal, is to be returned
        boolean weirdo = (pos == 5 || pos == 6 || pos == 7 || pos == 13 || pos == 14 || pos == 15 || pos == 21 || pos == 22 || pos == 23);           // had to make this a variable because I repeated this same condition at some point later
        if(checkKing(pos)){                          // first checks if the piece moving is a king piece because kings are allowed to move both forwards and backwards
            if(checkCapture(pos, pos1)){        // checks if a capture can be made, if they can, then legal is automatically true
                legal = true;
            }
            else if(pos == 3){                                                // the rest of the code is checking every possible legal combination of the initial position value and the destination value for kings, who can move forward and backward
                legal = pos1 == Utility.bitAddition(pos, 4);
            }
            else if(pos == 28){
                legal = pos1 == Utility.bitSubtraction(pos, 4);
            }
            else if(pos == 4 || pos == 11 || pos == 12 || pos == 19 || pos == 20 || pos == 27){
                legal = pos1 == Utility.bitAddition(pos, 4) || pos1 == Utility.bitSubtraction(pos, 4);
            }
            else if(pos == 0 || pos == 1 || pos == 2){
                legal = (pos1 == Utility.bitAddition(pos, 4) || pos1 == Utility.bitAddition(pos, 5));
            }
            else if(pos == 29 || pos == 30 || pos == 31){                                                                  // every possible legal choice is hard-coded (if that's the right word) and if any of the boolean values match the user's input, then the legal variable is returned true
                legal = (pos1 == Utility.bitSubtraction(pos, 4) || pos1 == Utility.bitSubtraction(pos, 5));
            }
            else if(weirdo){
                legal = (pos1 == Utility.bitAddition(pos, 3) || pos1 == Utility.bitAddition(pos, 4))
                        || (pos1 == Utility.bitSubtraction(pos, 4) || pos1 == Utility.bitSubtraction(pos, 5));
            }
            else if(pos == 8 || pos == 9 || pos == 10 || pos == 16 || pos == 17 || pos == 18 || pos == 24 || pos == 25 || pos == 26){
                legal = (pos1 == Utility.bitAddition(pos, 4) || pos1 == Utility.bitAddition(pos, 5))
                        || (pos1 == Utility.bitSubtraction(pos, 3) || pos1 == Utility.bitSubtraction(pos, 4));
            }
        }
        else if(Objects.equals(turn, "player1")){                                // same stuff but this time only checking for combinations that are valid for player 1, who can only move one direction (until their pieces become king)
            if(checkCapture(pos, pos1)){
                legal = true;
            }
            else if(pos == 3 || pos == 4 || pos == 11 | pos == 12 || pos == 19 || pos == 20 || pos == 27){
                legal = pos1 == Utility.bitAddition(pos, 4);
            }
            else if(weirdo){
                legal = (pos1 == Utility.bitAddition(pos, 3) || pos1 == Utility.bitAddition(pos, 4));
            }
            else{
                legal = (pos1 == Utility.bitAddition(pos, 4) || pos1 == Utility.bitAddition(pos, 5));
            }
        }
        else{                                                                        // same stuff but this time only checking for combinations that are valid for player 2, who can only move one direction (until their pieces become king)
            if(checkCapture(pos, pos1)){
                legal = true;
            }
            else if(pos == 28 || pos == 27 || pos == 20 || pos == 19 || pos == 12 || pos == 11 || pos == 4){
                legal = pos1 == Utility.bitSubtraction(pos, 4);
            }
            else if(pos == 26 || pos == 25 || pos == 24 || pos == 18 || pos == 17 || pos == 16 || pos == 10 || pos == 9|| pos == 8){
                legal = (pos1 == Utility.bitSubtraction(pos, 3) || pos1 == Utility.bitSubtraction(pos, 4));

            }
            else{
                legal = (pos1 == Utility.bitSubtraction(pos, 4) || pos1 == Utility.bitSubtraction(pos, 5));
            }
        }
        return legal;                      // returns the legal value to determine if the move trying to be made is allowed
    }
    public boolean checkCapture(int pos, int pos1){           // this method checks if a capture can be made based on the inputs of the user (variables pos and pos1), which would ignore all boundary constraints made earlier in the isLegal method
        boolean capture = false;                        // the capture variable will determine if a capture can be made, it's returned at the end
        boolean weirdo = (pos == 1 && pos1 == 8) || (pos == 2 && pos1 == 9) || (pos == 3 && pos1 == 10) ||            // all of these "weirdo" boolean values are hardcoded conditions, more specifically every possible pos and pos1 combination that could potentially result in a capture
                (pos == 9 && pos1 == 16) || (pos == 10 && pos1 == 17) || (pos == 11 && pos1 == 18) ||              // variables had to be made because they would be repeated later on
                (pos == 17 && pos1 == 24) || (pos == 18 && pos1 == 25) || (pos == 19 && pos1 == 26);
        boolean weirdo1 = (pos == 0 && pos1 == 9) || (pos == 1 && pos1 == 10) || (pos == 2 && pos1 == 11) ||
                (pos == 5 && pos1 == 12) || (pos == 6 && pos1 == 13) || (pos == 7 && pos1 == 14) ||
                (pos == 8 && pos1 == 17) || (pos == 9 && pos1 == 18) || (pos == 10 && pos1 == 19) ||
                (pos == 13 && pos1 == 20) || (pos == 14 && pos1 == 21) || (pos == 15 && pos1 == 22) ||
                (pos == 16 && pos1 == 25) || (pos == 17 && pos1 == 26) || (pos == 18 && pos1 == 27) ||
                (pos == 21 && pos1 == 28) || (pos == 22 && pos1 == 29) || (pos == 23 && pos1 == 30);
        boolean weirdo2 = (pos == 4 && pos1 == 13) || (pos == 5 && pos1 == 14) || (pos == 6 && pos1 == 15) ||
                (pos == 12 && pos1 == 21) || (pos == 13 && pos1 == 22) || (pos == 14 && pos1 == 23) ||                         // again, every possible combination of user inputs (initial positions and destination positions) that can result in a capture
                (pos == 20 && pos1 == 29) || (pos == 21 && pos1 == 30) || (pos == 22 && pos1 == 31);
        boolean weirdo3 = (pos == 30 && pos1 == 23) || (pos == 29 && pos1 == 22) || (pos == 28 && pos1 == 21) ||
                (pos == 22 && pos1 == 15) || (pos == 21 && pos1 == 14) || (pos == 20 && pos1 == 13) ||
                (pos == 14 && pos1 == 7) || (pos == 13 && pos1 == 6) || (pos == 12 && pos1 == 5);
        boolean weirdo4 = (pos == 31 && pos1 == 22) || (pos == 30 && pos1 == 21) || (pos == 29 && pos1 == 20) ||
                (pos == 26 && pos1 == 19) || (pos == 25 && pos1 == 18) || (pos == 24 && pos1 == 17) ||
                (pos == 23 && pos1 == 14) || (pos == 22 && pos1 == 13) || (pos == 21 && pos1 == 12) ||
                (pos == 18 && pos1 == 11) || (pos == 17 && pos1 == 10) || (pos == 16 && pos1 == 9) ||
                (pos == 15 && pos1 == 6) || (pos == 14 && pos1 == 5) || (pos == 13 && pos1 == 4) ||
                (pos == 10 && pos1 == 3) || (pos == 9 && pos1 == 2) || (pos == 8 && pos1 == 1);
        boolean weirdo5 = (pos == 27 && pos1 == 18) || (pos == 26 && pos1 == 17) || (pos == 25 && pos1 == 16) ||
                (pos == 19 && pos1 == 10) || (pos == 18 && pos1 == 9) || (pos == 17 && pos1 == 8) ||
                (pos == 11 && pos1 == 2) || (pos == 10 && pos1 == 1) || (pos == 9 && pos1 == 0);

        if(checkKing(pos)){                                           // just like with the isLegal method, we check if the moving piece is a king first because a king has more options
            if(Objects.equals(turn, "player1")){
                if(weirdo){                                                             // if any of the weirdo variables turn out true, we check the piece in the potential "captured" position
                    int num = Utility.bitSubtraction(pos1, 3);                                   // how we check for the piece in the potential "captured" position depends on the specific weirdo variable that is true, as different numbers/methods are used
                    char check = bitboard2.charAt(Utility.bitSubtraction(31, num));
                    capture = (check == '1');                                           //  if there is a piece in that potential "captured" position, and it belongs to the other player, then the capture variable returns true, meaning a capture can be made
                }
                else if(weirdo1){
                    int num = Utility.bitSubtraction(pos1, 4);
                    char check = bitboard2.charAt(Utility.bitSubtraction(31, num));
                    capture = (check == '1');
                }
                else if(weirdo2){
                    int num = Utility.bitSubtraction(pos1, 5);
                    char check = bitboard2.charAt(Utility.bitSubtraction(31, num));
                    capture = (check == '1');
                }
                else if(weirdo3){
                    int num = Utility.bitAddition(pos1, 3);
                    char check = bitboard2.charAt(Utility.bitSubtraction(31, num));
                    capture = (check == '1');
                }
                else if(weirdo4){
                    int num = Utility.bitAddition(pos1, 4);
                    char check = bitboard2.charAt(Utility.bitSubtraction(31, num));
                    capture = (check == '1');
                }
                else if(weirdo5){
                    int num = Utility.bitAddition(pos1, 5);
                    char check = bitboard2.charAt(Utility.bitSubtraction(31, num));
                    capture = (check == '1');

                }
            }
            else{                                                                               // same stuff but for player2's kings
                if(weirdo){
                    int num = Utility.bitSubtraction(pos1, 3);
                    char check = bitboard1.charAt(Utility.bitSubtraction(31, num));
                    capture = (check == '1');
                }
                else if(weirdo1){
                    int num = Utility.bitSubtraction(pos1, 4);
                    char check = bitboard1.charAt(Utility.bitSubtraction(31, num));
                    capture = (check == '1');
                }
                else if(weirdo2){
                    int num = Utility.bitSubtraction(pos1, 5);
                    char check = bitboard1.charAt(Utility.bitSubtraction(31, num));           // same exact code except we're always checking if the piece in the potential "captured" positions belongs to player1
                    capture = (check == '1');
                }
                else if(weirdo3){
                    int num = Utility.bitAddition(pos1, 3);
                    char check = bitboard1.charAt(Utility.bitSubtraction(31, num));
                    capture = (check == '1');
                }
                else if(weirdo4){
                    int num = Utility.bitAddition(pos1, 4);
                    char check = bitboard1.charAt(Utility.bitSubtraction(31, num));
                    capture = (check == '1');
                }
                else if(weirdo5){
                    int num = Utility.bitAddition(pos1, 5);
                    char check = bitboard1.charAt(Utility.bitSubtraction(31, num));
                    capture = (check == '1');

                }
            }
        }
        else if(Objects.equals(turn, "player1")){                                       // if the moving piece isn't a king, then we check if it's player1's turn
            if(weirdo){
                int num = Utility.bitSubtraction(pos1, 3);
                char check = bitboard2.charAt(Utility.bitSubtraction(31, num));
                capture = (check == '1');
            }
            else if (weirdo1){
                int num = Utility.bitSubtraction(pos1, 4);
                char check = bitboard2.charAt(Utility.bitSubtraction(31, num));        // normal pieces have fewer capture possibilities because they can only move in one direction, so not much here
                capture = (check == '1');
            }
            else if(weirdo2){
                int num = Utility.bitSubtraction(pos1, 5);
                char check = bitboard2.charAt(Utility.bitSubtraction(31, num));
                capture = (check == '1');
            }
        }
        else{                                                                          // finally, player2
            if(weirdo3){
                int num = Utility.bitAddition(pos1, 3);
                char check = bitboard1.charAt(Utility.bitSubtraction(31, num));
                capture = (check == '1');
            }
            else if(weirdo4){
                int num = Utility.bitAddition(pos1, 4);
                char check = bitboard1.charAt(Utility.bitSubtraction(31, num));     // just like player1's capture possibilities, there aren't that many
                capture = (check == '1');
            }
            else if(weirdo5){
                int num = Utility.bitAddition(pos1, 5);
                char check = bitboard1.charAt(Utility.bitSubtraction(31, num));
                capture = (check == '1');

            }
        }
        return capture;
    }
    public void capturePiece(int pos, int pos1){                   // this method holds the code that actually carries out the capture by modifying the bitboards
        int sub = 0;                                  // variable sub holds a number to be determined that will be used to clear the captured piece in the opponent's bitboard
        boolean weirdo = (pos == 1 && pos1 == 8) || (pos == 2 && pos1 == 9) || (pos == 3 && pos1 == 10) ||                // these conditions are used here too
                (pos == 9 && pos1 == 16) || (pos == 10 && pos1 == 17) || (pos == 11 && pos1 == 18) ||
                (pos == 17 && pos1 == 24) || (pos == 18 && pos1 == 25) || (pos == 19 && pos1 == 26);
        boolean weirdo1 = (pos == 0 && pos1 == 9) || (pos == 1 && pos1 == 10) || (pos == 2 && pos1 == 11) ||
                (pos == 5 && pos1 == 12) || (pos == 6 && pos1 == 13) || (pos == 7 && pos1 == 14) ||
                (pos == 8 && pos1 == 17) || (pos == 9 && pos1 == 18) || (pos == 10 && pos1 == 19) ||
                (pos == 13 && pos1 == 20) || (pos == 14 && pos1 == 21) || (pos == 15 && pos1 == 22) ||
                (pos == 16 && pos1 == 25) || (pos == 17 && pos1 == 26) || (pos == 18 && pos1 == 27) ||
                (pos == 21 && pos1 == 28) || (pos == 22 && pos1 == 29) || (pos == 23 && pos1 == 30);
        boolean weirdo2 = (pos == 4 && pos1 == 13) || (pos == 5 && pos1 == 14) || (pos == 6 && pos1 == 15) ||
                (pos == 12 && pos1 == 21) || (pos == 13 && pos1 == 22) || (pos == 14 && pos1 == 23) ||
                (pos == 20 && pos1 == 29) || (pos == 21 && pos1 == 30) || (pos == 22 && pos1 == 31);
        boolean weirdo3 = (pos == 30 && pos1 == 23) || (pos == 29 && pos1 == 22) || (pos == 28 && pos1 == 21) ||
                (pos == 22 && pos1 == 15) || (pos == 21 && pos1 == 14) || (pos == 20 && pos1 == 13) ||
                (pos == 14 && pos1 == 7) || (pos == 13 && pos1 == 6) || (pos == 12 && pos1 == 5);
        boolean weirdo4 = (pos == 31 && pos1 == 22) || (pos == 30 && pos1 == 21) || (pos == 29 && pos1 == 20) ||
                (pos == 26 && pos1 == 19) || (pos == 25 && pos1 == 18) || (pos == 24 && pos1 == 17) ||
                (pos == 23 && pos1 == 14) || (pos == 22 && pos1 == 13) || (pos == 21 && pos1 == 12) ||
                (pos == 18 && pos1 == 11) || (pos == 17 && pos1 == 10) || (pos == 16 && pos1 == 9) ||
                (pos == 15 && pos1 == 6) || (pos == 14 && pos1 == 5) || (pos == 13 && pos1 == 4) ||
                (pos == 10 && pos1 == 3) || (pos == 9 && pos1 == 2) || (pos == 8 && pos1 == 1);
        boolean weirdo5 = (pos == 27 && pos1 == 18) || (pos == 26 && pos1 == 17) || (pos == 25 && pos1 == 16) ||
                (pos == 19 && pos1 == 10) || (pos == 18 && pos1 == 9) || (pos == 17 && pos1 == 8) ||
                (pos == 11 && pos1 == 2) || (pos == 10 && pos1 == 1) || (pos == 9 && pos1 == 0);

        if(checkKing(pos)){                                 // kings first, again
            if(Objects.equals(turn, "player1")){         // this is similar to the checkCapture method but instead of checking for opponent pieces in potential "captured" positions, we're just setting sub to a specific number based on whatever weirdo variable is true
                if(weirdo){                                 // if this method is executed then that means we're already past the point of "potential captures" and now we know that a capture can definitely be made
                    sub = -3;                               // sub is the position of the piece that is to be captured, so the variable will be used to clear that bit in the bitboard
                }
                else if(weirdo1){
                    sub = -4;
                }
                else if(weirdo2){
                    sub = -5;
                }
                else if(weirdo3){
                    sub = 3;
                }
                else if(weirdo4){
                    sub = 4;
                }
                else if(weirdo5){
                    sub = 5;
                }
                int pos3 = Utility.bitAddition(pos1, sub);                                        // once sub is set, it is implemented in the "clearing bit" process, which is similar to the process in the movePiece method, the difference being we're not setting bits, only clearing
                int intdecimal = Integer.parseInt(Utility.toDecimalString(bitboard2));
                int clear = Utility.clearBit(intdecimal, pos3);
                bitboard2 = Utility.toBinaryString(clear);                            // the opponent's bitboard is modified
                updateGameState();                         // once the capture is finished, the game's state is updated
            }
            else{                        // same stuff but for player2's kings
                if(weirdo){
                    sub = -3;
                }
                else if(weirdo1){
                    sub = -4;
                }
                else if(weirdo2){
                    sub = -5;
                }
                else if(weirdo3){
                    sub = 3;
                }
                else if(weirdo4){
                    sub = 4;
                }
                else if(weirdo5){
                    sub = 5;
                }
                int pos3 = Utility.bitAddition(pos1, sub);
                int intdecimal = Integer.parseInt(Utility.toDecimalString(bitboard1));
                int clear = Utility.clearBit(intdecimal, pos3);
                bitboard1 = Utility.toBinaryString(clear);
                updateGameState();
            }
        }
        else if(Objects.equals(turn, "player1")){          // if the piece isn't a king, then we move down to player1's capture
            if(weirdo){
                sub = 3;
            }
            else if (weirdo1){
                sub = 4;
            }                                           // same stuff but fewer options for capture, because less capture possibilities
            else if(weirdo2){
                sub = 5;
            }
            int pos3 = Utility.bitSubtraction(pos1, sub);
            int intdecimal = Integer.parseInt(Utility.toDecimalString(bitboard2));
            int clear = Utility.clearBit(intdecimal, pos3);
            bitboard2 = Utility.toBinaryString(clear);
            updateGameState();
        }
        else{                                // player2's capture
            if(weirdo3){
                sub = 3;
            }
            else if(weirdo4){
                sub = 4;                 // just like player1, fewer options
            }
            else if(weirdo5){
               sub = 5;
            }
            int pos3 = Utility.bitAddition(pos1, sub);
            int intdecimal = Integer.parseInt(Utility.toDecimalString(bitboard1));
            int clear = Utility.clearBit(intdecimal, pos3);
            bitboard1 = Utility.toBinaryString(clear);
            updateGameState();
        }
    }
    public void potentialKing (int pos1){                     // this method turns a normal piece into a king once it reaches its opponent's back-line
        if (Objects.equals(turn, "player1")){
            if(pos1 == 28 || pos1 == 29 || pos1 == 30 || pos1 == 31){                    // these are player2's back-line positions, so if that's where player1's piece is heading (checked with pos1, the destination position value) then it will become king
                int intdecimal = Integer.parseInt(Utility.toDecimalString(bitboard3));
                int set = Utility.setBit(intdecimal, pos1);                                  // modifying the bit position in the king bitboard to distinguish and keep track of king pieces
                bitboard3 = Utility.toBinaryString(set);
            }
        }
        else{
            if(pos1 == 0 || pos1 == 1 || pos1 == 2 || pos1 == 3){                      // these are player1's back-line positions, so if that's where player2's piece is heading (checked with pos1, the destination position value) then it will become king
                int intdecimal = Integer.parseInt(Utility.toDecimalString(bitboard3));
                int set = Utility.setBit(intdecimal, pos1);                                    // modifying the bit position in the king bitboard to distinguish and keep track of king pieces
                bitboard3 = Utility.toBinaryString(set);
            }
        }
    }
    public boolean checkKing (int pos){                  // this method checks if a piece is a king piece, it's used a lot in the code above
        boolean isKing;                                      // variable isKing determines if the piece is a king or not
        char check1;                                   // variable check1 will hold the bit in the position from either player 1 or player 2's bitboard
        char check2 = bitboard3.charAt(31 - pos);         // variable check2 holds the bit in the same position on the king's bitboard
        if (Objects.equals(turn, "player1")){
            check1 = bitboard1.charAt(31 - pos);              // getting bit (player 1)
        }
        else{
            check1 = bitboard2.charAt(31 - pos);           // getting bit (player 2)
        }
        isKing = (check1 == check2);    // if the bit is the same value (1) on both player 1 or player 2's bitboard and the king bitboard then that means the piece in question is a king
        return isKing;
    }
    public void updateGameState(){             // this method will both update the board after every move and check for game ending conditions such as a player losing all their pieces
        int num = 1;                     // variable num will be used to skip the non-playable spaces on the board
        int where = 31;                // variable where will be used to get the bit values on both player's bitboards
        for(int i = 0; i < 8; i++){        // this for-loop is used to set the board according to the both player's bitboards
            if(num % 2 == 0){          // basically, the process of skipping the non-playable spaces works in an odd/even patter
                num = 1;
            }
            else{
                num = 0;
            }
            for(int j = 0; j < 8; j++){
                if(num % 2 == 0){                     // when num is an even number, it will put a ' ' in the array index, meaning it is an unplayable space
                    board[i][j] = ' ';
                }
                else{                                               // when num is an odd number, it will use the bitboards to put down either a 1 or 0 in that array index, meaning it is a playable space
                    char check1 = bitboard1.charAt(where);
                    char check2 = bitboard2.charAt(where);
                    if(check1 == '0' & check2 == '0'){            // if the bit values in both bitboards is 0 then that means neither player has a piece in that space, so a 0 will go in the array index
                        board[i][j] = '0';
                    }
                    else{
                        board[i][j] = '1';             // if at least one player has 1 in their bitboard for that position, then that means a player has a piece in that space, so a 1 will go in the array index
                    }
                    where = Utility.bitSubtraction(where, 1);         // every time a 0 or 1 is put down in a playable space, the where value decreases by 1 so that we can get through all 32 bits of the bitboards
                }
                num = Utility.bitAddition(num, 1);              // num will increase by 1 to continue the even/odd pattern
            }
        }
        if (Objects.equals(bitboard1, "00000000000000000000000000000000")){                      // these if statements check for game ending conditions
            System.out.println("All of Player 1's pieces have been captured. Player 2 wins!");      // if either player's bitboard loses all their 1's (pieces) then the other player wins
            finished = true;
        }
        else if(Objects.equals(bitboard2, "00000000000000000000000000000000")){
            System.out.println("All of Player 2's pieces have been captured. Player 1 wins!");
            finished = true;                                                                             // when someone wins the game, the finished variable becomes true, ending the game
        }
    }
    public String printBoard(){                                                                    // this method prints the actual board visual
        StringBuilder line = new StringBuilder("     0   1   2   3   4   5   6   7");       // line object holds all the content of the board
        System.out.println();
        System.out.println("PLAYER 1 BITBOARD: "+bitboard1);
        System.out.println("PLAYER 2 BITBOARD: "+bitboard2);
        System.out.println("KING BITBOARD: "+bitboard3);
        System.out.println();
        for(int i=0;i<8;i++) {
            line.append("\n   ---------------------------------\n");
            if (i == 0){
                line.append("A  ");
            }
            else if (i == 1){                                                   // with each iteration of the for-loop, a different letter will print at the beginning of the line
                line.append("B  ");
            }
            else if (i == 2){
                line.append("C  ");                         //the numbers and letters helps with the selection of pieces and move making in general
            }
            else if (i == 3){
                line.append("D  ");
            }
            else if (i == 4){
                line.append("E  ");
            }
            else if (i == 5){
                line.append("F  ");
            }
            else if (i == 6){
                line.append("G  ");
            }
            else{
                line.append("H  ");
            }
            line.append("| ");
            for(int j=0;j<8;j++) {
                line.append(board[i][j]).append(" | ");
            }
        }
        line.append("\n   ---------------------------------\n");
        return line.toString();                                               // line is returned and will be printed to the console in the application class
    }
}