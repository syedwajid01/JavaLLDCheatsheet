import java.util.ArrayList;
import java.util.List;

import Controller.GameController.TicTacToeGame;
import PlayerFactory.SimplePlayerFactory;
import PlayerStrategies.PlayerStrategy;
import PlayerStrategies.ConcreteStrategy.HumanPlayerStrategy;

class Main {
    public static void main(String[] args) {
        List<PlayerStrategy> playerStrategies = new ArrayList<>();
        playerStrategies.add(new HumanPlayerStrategy("X"));
        playerStrategies.add(new HumanPlayerStrategy("O"));
        TicTacToeGame game = new TicTacToeGame(3, playerStrategies,
                new SimplePlayerFactory());
        game.play();
    }
}