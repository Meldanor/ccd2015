package jchess;

import jchess.game.HexagonalGameboard;
import jchess.gamelogic.IGameLogic;
import jchess.gamelogic.ThreeWayChessGameLogic;
import jchess.ui.HexagonalGameboardGUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The entrance of the program.
 * @since 02.12.2015
 */
public class Core {

    public static final Logger LOGGER = LogManager.getLogger("Dash");

    public static void main(String[] args) {
        LOGGER.info(() -> "JChess 2.0 by Dash");
        LOGGER.info(() -> "Starting game");

        IGameLogic logic = new ThreeWayChessGameLogic();
        logic.initializeGame();


        HexagonalGameboardGUI gui = new HexagonalGameboardGUI();
        gui.drawGameboardState((HexagonalGameboard) logic.getGameBoard());

    }
}
