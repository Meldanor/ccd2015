package jchess.gamelogic;

import jchess.Player;
import java.util.List;

/**
 * Created by stephan on 11.11.2015.
 */
public interface IGameLogic {

    /**
     * Method to initialize Game
     */
    void initializeGame();

    /**
     * Method to check if a next Turn is possible otherwise last activePlayer wins.
     */
    boolean isGameEnded();

    /**
     * Determine GameState: getActivePlayerID,getFieldIDbyFigureID,getTurnNumber
     */
    GameState getCurrentGameState();

    /**
     * Eventhandler for Tileselection
     * @param tileID The Tile ID. Can be null.
     */
    void onBoardTileSelected(String tileID);

    /**
     * Additional Information:
     * getPossibleMoves     List of all FieldIDs a Figure can move to.
     * getActiveFigureID    Returns FieldID of selected Figure
     */
    List<String> getPossibleMoves();
    String getActiveFigureID();

}
