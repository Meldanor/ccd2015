package jchess.gamelogic;

import jchess.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stephan on 11.11.2015.
 */
public interface IGameLogic {

    void initializeGame();

    /*
    * last activePlayer wins.
     */
    boolean isGameEnded();

    /*
    * Determine GameState: getActivePlayerID,getFieldIDbyFigureID,getTurnNumber
    */
    GameState getCurrentGameState();

    /*
    * Eventhandler for Tileselection
    *
    * */
    void onBoardTileSelected(String tileID);

    /*
    * Additional Information: getPossibleMoves,getActiveFigureID
    * Returns possible FieldID of selected Figure
    * */
    ArrayList<String> getPossibleMoves();
    String getActiveFigureID();

    void getTurnHistory();
    void stepHistoryBackward();
    void stepHistoryForward();

}
