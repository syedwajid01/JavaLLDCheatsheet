package EventListeners;

import CommonEnums.Symbol;
import GameStateHandler.GameState;
import Utility.Position;

public interface GameEventListener {
    void onMoveMade(Position position, Symbol symbol);
    void onGameStateChanged(GameState gameState);   
}
