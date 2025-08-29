package GameStateHandler.GameStates;

import CommonEnums.Symbol;
import GameStateHandler.GameState;
import GameStateHandler.GameContext.GameContext;
import Utility.Player;

public class XTurnState implements GameState {
    @Override
    public void next(GameContext context, Player player, boolean hasWon) {
        if (hasWon) {
           context.setState(player.getSymbol() == Symbol.X ? new XWonState() : new OWonState());
        } else {
           context.setState(new OTurnState());
        }
    }

    @Override
    public boolean isGameOver() {
       return false;
    }
    
}
