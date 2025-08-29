package EventListeners.Concrete_EventListeners;

import CommonEnums.Symbol;
import EventListeners.GameEventListener;
import GameStateHandler.GameState;
import Utility.Position;

public class ConsoleGameEventListener implements GameEventListener{

    @Override
    public void onMoveMade(Position position, Symbol symbol) {
        System.out.println("Move made at position: " + position + " by player with symbol: " + symbol);
    }

    @Override
    public void onGameStateChanged(GameState gameState) {
        System.out.println("Game state changed to: " + gameState);
    }
    
}
