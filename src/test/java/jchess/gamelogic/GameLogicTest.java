package jchess.gamelogic;

import jchess.game.Figure;
import jchess.game.HexagonalPlayerType;
import jchess.game.Position2D;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by stephan on 30.12.2015.
 */
public class GameLogicTest {

    /**
     * Test if all 51 Figures are set on Board.
     * Test interaction:
     * a white pawn was selected and moved from Position (1,0) to (2,0)
     * <p>
     * Test order of Players after nextTurn() was called in onBoardTileSelected.
     * Test if turnNumber increased
     */
    @Test
    public void testOne() {

        ThreeWayChessGameLogic T = new ThreeWayChessGameLogic();
        T.initializeGame();
        assertEquals(T.getGameBoard().getAllFigures().size(), 51);

        HexagonalPlayerType player = T.getCurrentPlayer();
        assertEquals(HexagonalPlayerType.WHITE, player);
        assertEquals(1, player.getOrder());

        Figure whitePawn = (Figure) T.getGameBoard().getFigure(Position2D.of(1, 0)).get();
        T.onBoardTileSelected(Position2D.of(1, 0));
        assertEquals(1, T.getCurrentGameState().turnNumber);
        assertEquals(whitePawn, T.getActiveFigure());
        T.onBoardTileSelected(Position2D.of(2, 0));

        assertEquals(whitePawn, T.getGameBoard().getFigure(Position2D.of(2, 0)).get());
        assertEquals(2, T.getCurrentGameState().turnNumber);
        player = T.getCurrentPlayer();
        assertEquals(HexagonalPlayerType.BLACK, player);
        assertEquals(2, player.getOrder());

        /*
        T.nextTurn();
        assertEquals(3,T.getCurrentGameState().turnNumber);
        player = T.getCurrentPlayer();
        assertEquals(HexagonalPlayerType.GRAY,player);
        assertEquals(3,player.getOrder());
        */

    }
}
