package jchess.gamelogic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stephan on 11.11.2015.
 */
public class ThreeWayChessGameLogic implements IGameLogic {

    @Override
    public void initializeGame() {

    }

    @Override
    public boolean isGameEnded() {
        return false;
    }

    @Override
    public GameState getCurrentGameState() {
        return null;
    }

    @Override
    public void onBoardTileSelected(String tileID) {

    }

    @Override
    public ArrayList<String> getPossibleMoves() {
        return null;
    }

    @Override
    public String getActiveFigureID() {
        return null;
    }

    @Override
    public void getTurnHistory() {

    }

    @Override
    public void stepHistoryBackward() {

    }

    @Override
    public void stepHistoryForward() {

    }
}
