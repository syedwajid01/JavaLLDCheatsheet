package GameStateHandler;

import GameStateHandler.GameContext.GameContext;
import Utility.Player;

public interface GameState {
    void next(GameContext context,Player player, boolean hasWon);
    boolean isGameOver();
}
