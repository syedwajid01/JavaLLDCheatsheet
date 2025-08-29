package GameStateHandler.GameContext;

import GameStateHandler.GameState;
import GameStateHandler.GameStates.XTurnState;
import Utility.Player;

public class GameContext {
    private GameState currentState;
    public GameContext() {
        this.currentState = new XTurnState();
    }
    
    public void setState(GameState state) {
        this.currentState = state;
    }

    public GameState getState() {
        return currentState;
    }

    public void next(Player player,boolean hasWon) {
        currentState.next(this, player, hasWon);
    }

    public boolean isGameOver() {
        return currentState.isGameOver();
    }
}
