package Utility;

import java.util.ArrayList;
import java.util.List;

import CommonEnums.Symbol;
import EventListeners.GameEventListener;
import GameStateHandler.GameContext.GameContext;
import GameStateHandler.GameStates.OWonState;
import GameStateHandler.GameStates.XWonState;

public class Board {
    private int SIZE = 3; // Default size for a Tic Tac Toe board is 3x3
    private Symbol[][] grid;
    private List<GameEventListener> eventListeners;

    public Board(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        this.SIZE = size;
        eventListeners = new ArrayList<>();
        grid = new Symbol[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = Symbol.EMPTY;
            }
        }
    }

    public void addEventListener(GameEventListener listener) {
        eventListeners.add(listener);
    }

    public void removeEventListener(GameEventListener listener) {
        eventListeners.remove(listener);
    }

    private void notifyMoveMade(Position position, Symbol symbol) {
        for (GameEventListener listener : eventListeners) {
            listener.onMoveMade(position, symbol);
        }
    }

    private void notifyGameStateChanged(GameContext context) {
        for (GameEventListener listener : eventListeners) {
            listener.onGameStateChanged(context.getState());
        }
    }

    public boolean isValidMove(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE && grid[row][col] == Symbol.EMPTY;
    }

    public void makeMove(Position position, Symbol symbol) {
        if (isValidMove(position)) {
            grid[position.getRow()][position.getCol()] = symbol;
            notifyMoveMade(position, symbol);
        } else {
            throw new IllegalArgumentException("Invalid move");
        }
    }

    public void checkGameState(GameContext context, Player currentPlayer) {
        // Check rows
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][0] != Symbol.EMPTY && isWinningLine(grid[i])) {
                updateGameContext(context, currentPlayer);
                return;
            }
        }

        // Check columns
        for (int c = 0; c < SIZE; c++) {
            Symbol[] column = new Symbol[SIZE];
            for (int r = 0; r < SIZE; r++) {
                column[r] = grid[r][c];
            }
            if (column[0] != Symbol.EMPTY && isWinningLine(column)) {
                updateGameContext(context, currentPlayer);
                return;
            }
        }

        // Check diagonals
        Symbol[] diagonal1 = new Symbol[SIZE];
        Symbol[] diagonal2 = new Symbol[SIZE];
        for (int i = 0; i < SIZE; i++) {
            diagonal1[i] = grid[i][i];
            diagonal2[i] = grid[i][SIZE - 1 - i];
        }

        if (diagonal1[0] != Symbol.EMPTY && isWinningLine(diagonal1)) {
            updateGameContext(context, currentPlayer);
            return;
        }
        if (diagonal2[0] != Symbol.EMPTY && isWinningLine(diagonal2)) {
            updateGameContext(context, currentPlayer);
            return;
        }
    }

    private void updateGameContext(GameContext context, Player currentPlayer) {
        context.setState(currentPlayer.getSymbol() == Symbol.X ? new XWonState() : new OWonState());
        notifyGameStateChanged(context);
    }

    private boolean isWinningLine(Symbol[] line) {
        Symbol first = line[0];
        for (Symbol symbol : line) {
            if (symbol != first) {
                return false;
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Symbol symbol = grid[i][j];
                switch (symbol) {
                    case X:
                        System.err.print(" X ");
                        break;
                    case O:
                        System.err.print(" O ");
                        break;
                    case EMPTY:
                        System.err.print(" . ");
                        break;
                    default:
                        break;
                }
                if (j < SIZE - 1) {
                    System.err.print("|");
                }
            }
            System.out.println();
            if (i < SIZE - 1) {
                System.out.println("---+---+---");
            }
        }
    }

}
