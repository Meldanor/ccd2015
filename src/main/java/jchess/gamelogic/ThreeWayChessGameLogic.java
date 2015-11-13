package jchess.gamelogic;

import java.util.List;

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
    public List<String> getPossibleMoves() {
        return null;
    }

    @Override
    public String getActiveFigureID() {
        return null;
    }

}
