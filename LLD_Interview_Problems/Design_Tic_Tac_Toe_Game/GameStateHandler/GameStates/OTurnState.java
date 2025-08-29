package GameStateHandler.GameStates;

import CommonEnums.Symbol;
import GameStateHandler.GameState;
import GameStateHandler.GameContext.GameContext;
import Utility.Player;

public class OTurnState implements GameState {
    @Override
    public void next(GameContext context, Player player, boolean hasWon) {
        if (hasWon) {
            context.setState(player.getSymbol() == Symbol.O ? new OWonState() : new XWonState());
        } else {
            context.setState(new XTurnState());
        }
    }

    @Override
    public boolean isGameOver() {
       return false;
    }
}
