package Controller.GameController;

import java.util.ArrayList;
import java.util.List;

import CommonEnums.Symbol;
import Controller.BoardGames;
import GameStateHandler.GameContext.GameContext;
import GameStateHandler.GameStates.OWonState;
import GameStateHandler.GameStates.XWonState;
import PlayerFactory.PlayerFactory;
import PlayerStrategies.PlayerStrategy;
import Utility.Board;
import Utility.Player;
import Utility.Position;

public class TicTacToeGame implements BoardGames {
    private Board board;
    private List<Player> players;
    private int currentPlayerIdx;
    private GameContext gameContext;

    public TicTacToeGame(int boardSize, List<PlayerStrategy> playerStrategies, PlayerFactory playerFactory) {
        this.players = new ArrayList<>();
        for (int idx = 0; idx < playerStrategies.size(); idx++) {
            this.players.add(playerFactory.createPlayer(Symbol.values()[idx], playerStrategies.get(idx)));
        }
        currentPlayerIdx = 0;
        this.board = new Board(3);
        this.gameContext = new GameContext();
    }

    private Player getCurrentPlayer() {
        return players.get(currentPlayerIdx);
    }

    @Override
    public void play() {
        do {
            // print the current state of the game
            board.printBoard();
            Player currentPlayer = getCurrentPlayer();
            Position move = currentPlayer.makeMove(board);
            board.makeMove(move, currentPlayer.getSymbol());
            board.checkGameState(gameContext, currentPlayer);

            switchPlayer();
        } while (!gameContext.isGameOver());
        board.printBoard();
        announceWinner();
    }

    private void switchPlayer() {
        currentPlayerIdx = (currentPlayerIdx + 1) % players.size();
    }

    private void announceWinner() {
        if (gameContext.getState() instanceof XWonState) {
            System.out.println("Player X wins!");
        } else if (gameContext.getState() instanceof OWonState) {
            System.out.println("Player O wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}
