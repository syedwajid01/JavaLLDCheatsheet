package PlayerStrategies;

import Utility.Board;
import Utility.Position;

public interface PlayerStrategy {
    Position makeMove(Board board);
}
