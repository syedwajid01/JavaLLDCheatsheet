package PlayerStrategies.ConcreteStrategy;

import java.util.Scanner;

import PlayerStrategies.PlayerStrategy;
import Utility.Board;
import Utility.Position;

public class HumanPlayerStrategy implements PlayerStrategy {
    private Scanner scanner;
    private String playerName;

    public HumanPlayerStrategy(String playerName) {
        this.playerName = playerName;
        this.scanner = new Scanner(System.in);
    }

    public String getPlayerName() {
        return playerName;
    }
    
    @Override
    public Position makeMove(Board board) {
        while (true) {
            System.out.println(playerName + ", enter your move (row [0-2] and column[0-2]) separated by space: ");

            try {
                // Read input from the user
                String input = scanner.nextLine();
                String[] parts = input.split(" ");
                
                if (parts.length != 2) {
                    System.out.println("Invalid input. Please enter row and column separated by a space.");
                    continue;
                }

                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);

                // Create a Position object
                Position move = new Position(row, col);

                // Validate the input
                if (board.isValidMove(move)) {
                    return move;
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter numeric values for row and column.");
                scanner.nextLine(); // clear the input buffer in case of invalid input
            }
        }
        
    }
    
}
