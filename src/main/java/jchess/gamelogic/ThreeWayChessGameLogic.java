package jchess.gamelogic;

import java.util.List;

/**
 *
 */
public class ThreeWayChessGameLogic implements IGameLogic {

    /**
     * Method to initialize Game
     */
    @Override
    public void initializeGame() {

    }

    /**
     * @return true if no more turns are possible. Last activePlayer wins.
     */
    @Override
    public boolean isGameEnded() {
        return false;
    }
    /**
     * @return should return ActivePlayerID FieldIDbyFigureID and TurnNumber
     */
    @Override
    public GameState getCurrentGameState() {
        return null;
    }
    /**
     * Eventhandler for Tileselection
     * @param tileID The Tile ID. Can be null, if no tile is selected.
     */
    @Override
    public void onBoardTileSelected(String tileID) {

    }

    /**
     * @return List of possible FieldIDs the activeFigure can move to
     */
    @Override
    public List<String> getPossibleMoves() {
        return null;
    }

    /**
     * @return FieldID of selected Figure
     */
    @Override
    public String getActiveFigureID() {
        return null;
    }

}
