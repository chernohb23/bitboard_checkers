import java.util.Objects;
import java.util.Scanner;

public class Application {
    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        Board board = new Board();                     // creates board game
       do{                                                     // do-while loop that will run the game
           System.out.println(board.printBoard());
           if(Objects.equals(board.turn, "player1")){
               System.out.println("Player 1, which piece would you like to move?");         // asks the player which piece they want to move
               String one = sc.nextLine();                                               // takes in that value
               System.out.println("Where would you like to move that piece?");                   // asks the player where they want to move that piece
               String two = sc.nextLine();                                         // takes that value in
               board.movePieces(one, two);                                             // executes the movePieces method
           }
           else{
               System.out.println("Player 2, which piece would you like to move?");          // same thing but for player2
               String one = sc.nextLine();
               System.out.println("Where would you like to move that piece?");
               String two = sc.nextLine();
               board.movePieces(one, two);
           }
       }while(!board.finished);                    // game will run until the finished variable is true
    }
}