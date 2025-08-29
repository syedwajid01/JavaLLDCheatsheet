package PlayerFactory;

import CommonEnums.Symbol;
import PlayerStrategies.PlayerStrategy;
import Utility.Player;

public class SimplePlayerFactory implements PlayerFactory {
    @Override
    public Player createPlayer(Symbol symbol, PlayerStrategy playerStrategy) {
        return new Player(symbol, playerStrategy);
    }
}
