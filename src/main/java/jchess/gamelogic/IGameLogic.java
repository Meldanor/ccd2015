package jchess.gamelogic;

import jchess.game.*;
import jchess.game.movement.ChessAction;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by stephan on 11.11.2015.
 */
public interface IGameLogic {

    /**
     * @return A Map with all Players and a bool to determine a player's king's death.
     */
    Map<HexagonalPlayerType, Boolean> getPlayerInformation();

    /**
     *
     */
    void initializeGame();

    /**
     * Method to check if a next Turn is possible otherwise last activePlayer wins.
     *
     * @return
     */
    boolean isGameEnded();

    /**
     * @return the Gameboard which is played on
     */
    Gameboard getGameBoard();

    /**
     * @return a GameState-Object with all figures on Board, turn number and the active Player.
     */
    GameState getCurrentGameState();

    /**
     * Eventhandler for Tileselection
     *
     * @param tile The Tile ID. Can be null.
     */
    void onBoardTileSelected(Position2D tile);

    /**
     * @return List of all ChessActions a Figure can do.
     */
    ArrayList<ChessAction> getPossibleMoves();

    PlayerType getCurrentPlayer();

    /**
     * @return the Figure which is currently selected
     */
    Figure getActiveFigure();

    /**
     * @return serialized History for saving a Game
     */
    String saveGame();

    /**
     * @param json a String to deserialize to the GameHistory _History.
     */
    void loadGame(String json);

}
