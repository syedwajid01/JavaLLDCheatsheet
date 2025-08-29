package GameStateHandler.GameStates;

import GameStateHandler.GameState;
import GameStateHandler.GameContext.GameContext;
import Utility.Player;

public class OWonState implements GameState {

    @Override
    public void next(GameContext context, Player player, boolean hasWon) {
        // No Next state after a win
    }

    @Override
    public boolean isGameOver() {
        return true;
    }
}
