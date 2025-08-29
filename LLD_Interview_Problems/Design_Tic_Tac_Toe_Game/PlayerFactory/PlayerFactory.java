package PlayerFactory;

import CommonEnums.Symbol;
import PlayerStrategies.PlayerStrategy;
import Utility.Player;

public interface PlayerFactory {
    Player createPlayer(Symbol symbol, PlayerStrategy playerStrategy);
}
