package Utility;

import CommonEnums.Symbol;
import PlayerStrategies.PlayerStrategy;

public class Player {
    Symbol symbol;
    PlayerStrategy playerStrategy;

    public Player(Symbol symbol, PlayerStrategy playerStrategy) {
        this.symbol = symbol;
        this.playerStrategy = playerStrategy;
    }
    public Symbol getSymbol() {
        return symbol;
    }
    
    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public PlayerStrategy getPlayerStrategy() {
        return playerStrategy;
    }

    public Position makeMove(Board board) {
        return playerStrategy.makeMove(board);
    }
}
