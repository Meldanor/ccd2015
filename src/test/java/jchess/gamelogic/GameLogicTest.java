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
     * Logic Test includes:
     * (1) A Test if all 51 Figures are set on Board.
     * (2) An interaction Test, focusing on the onBoardTileSelected() method:
     *  Scenario: A white pawn was selected.
     *            As a white pawn is selected, an unreachable field is clicked. This is handled as deselection.
     *            Reselecting the figure, it will finally be moved from Position (1,0) to (2,0).
     *            A black pawn will move from (4,0) to (4,1) so we can check for gray, and again for white.
     */
    @Test
    public void logicTest() {

        ThreeWayChessGameLogic T = new ThreeWayChessGameLogic();
        T.initializeGame();

        //(1) Test if the figure count is correct
        assertEquals(T.getGameBoard().getAllFigures().size(), 51);

        //(3) Test if first Player is White
        HexagonalPlayerType player = T.getCurrentPlayer();
        assertEquals(HexagonalPlayerType.WHITE, player);
        assertEquals(1, player.getOrder());

        /**
         *  (2)Test if onBoardTileSelected() method works correctly
         *      This includes if:
         *      (2.1) setting the activeFigure works,
         *      (2.2) deselection works,
         *      (2.3) Figure Movement updates correctly,
         *      (2.4) turnNumber will update,
         *      (2.5) currentPlayer will be set.
         *      (2.6) Blacks turn
         *      (2.7) Grays turn
         *      (2.8) Whites turn again
         */
        Figure whitePawn = (Figure) T.getGameBoard().getFigure(Position2D.of(1, 0)).get();
        T.onBoardTileSelected(Position2D.of(1, 0));

        //initial turnNumber
        assertEquals(1, T.getCurrentGameState().turnNumber);
        // (2.1) get the activeFigure
        assertEquals(whitePawn, T.getActiveFigure());

        /*
        (2.2) Test if Deselecting works
         deselect on: an unreachable Field was clicked.
         */
        T.onBoardTileSelected(Position2D.of(5,5));
        //Reselect whitePawn
        T.onBoardTileSelected(Position2D.of(1, 0));
        //deselect on: a field was clicked, where another white Figure is standing
        T.onBoardTileSelected(Position2D.of(1, 1));
        //Reselect whitePawn
        T.onBoardTileSelected(Position2D.of(1, 0));
        T.onBoardTileSelected(Position2D.of(2, 0));

        //(2.3) Figure whitePawn was moved from (1,0) to (2,0)
        assertEquals(whitePawn, T.getGameBoard().getFigure(Position2D.of(2, 0)).get());

        //(2.4) turnNumber update
        assertEquals(2, T.getCurrentGameState().turnNumber);

        //(2.5) check current Player
        player = T.getCurrentPlayer();

        //(2.6) Black is second in order, black pawn will be moved.
        assertEquals(HexagonalPlayerType.BLACK, player);
        assertEquals(2, player.getOrder());
        T.onBoardTileSelected(Position2D.of(4,0));
        T.onBoardTileSelected(Position2D.of(4,1));
        assertEquals(3,T.getCurrentGameState().turnNumber);

        //(2.7) Gray is third in order, gray pawn will be moved.
        player = T.getCurrentPlayer();
        assertEquals(HexagonalPlayerType.GRAY,player);
        assertEquals(3,player.getOrder());
        T.onBoardTileSelected(Position2D.of(12,11));
        T.onBoardTileSelected(Position2D.of(12,10));
        assertEquals(4,T.getCurrentGameState().turnNumber);

        //(2.8) Test player order for white again
        player = T.getCurrentPlayer();
        assertEquals(HexagonalPlayerType.WHITE,player);
        assertEquals(1,player.getOrder());
    }

    /**
     * Test if the Player Order is updating correctly.
     * The Player order will be updated with nextTurn() which is called in onBoardTileSelected() after a figure is moved.
     * If Player White captures Player Blacks King, it's Player Greys turn next.
     * Ergo: Black was beaten and will drop from rotation.
     * The DebugBoard Scenario is used.
     */
    @Test
    public void playerOutWhiteBeatsBlack(){
        //Check White beats Black, Gray should be next.
        ThreeWayChessGameLogic T = new ThreeWayChessGameLogic();
        //use DebugBoard
        T.initializeGame(true);

        /*
        Figure information, just to show where the relevant King Figures are standing.
         */
        Figure whiteKing = (Figure) T.getGameBoard().getFigure(Position2D.of(5,5)).get();
        Figure blackKing = (Figure) T.getGameBoard().getFigure(Position2D.of(6,6)).get();
        Figure grayKing = (Figure) T.getGameBoard().getFigure(Position2D.of(5,6)).get();

        //Whites turn, White will beat Blacks King
        assertEquals(T.getCurrentPlayer(),HexagonalPlayerType.WHITE);
        T.onBoardTileSelected(Position2D.of(5, 5));
        T.onBoardTileSelected(Position2D.of(6, 6));
        //next turn it's grays turn.
        assertEquals(T.getCurrentPlayer(),HexagonalPlayerType.GRAY);

    }

    /**
     * Test if the Player Order is updating correctly.
     * If Player Black captures Player Greys King, it's Player Whites turn next.
     * Ergo: Grey was beaten and will drop from rotation.
     * The DebugBoard Scenario is used.
     */
    @Test
    public void playerOutBlackBeatsGray(){
        //Check Black beats Gray, White should be next.
        ThreeWayChessGameLogic T = new ThreeWayChessGameLogic();
        //use DebugBoard
        T.initializeGame(true);

        //White moves Pawn from (1,0) to (2,0), so Black can beat grays King in his turn.

        T.onBoardTileSelected(Position2D.of(1,0));
        T.onBoardTileSelected(Position2D.of(2,0));

        //Black beats grays King
        assertEquals(T.getCurrentPlayer(),HexagonalPlayerType.BLACK);
        T.onBoardTileSelected(Position2D.of(6, 6));
        T.onBoardTileSelected(Position2D.of(5, 6));
        //next turn it's whites turn.
        assertEquals(T.getCurrentPlayer(),HexagonalPlayerType.WHITE);
    }

    /**
     * Test if the Player Order is updating correctly.
     * If Player Grey captures Player Whites King, it's Player Blacks turn next.
     * Ergo: White was beaten and will drop from rotation.
     * The DebugBoard Scenario is used.
     */
    @Test
    public void playerOutGrayBeatsWhite(){
        ThreeWayChessGameLogic T = new ThreeWayChessGameLogic();
        //use DebugBoard
        T.initializeGame(true);

        //White moves Pawn from (1,0) to (2,0)

        T.onBoardTileSelected(Position2D.of(1,0));
        T.onBoardTileSelected(Position2D.of(2,0));

        //Black moves Pawn from (4,0) to (4,1), so Gray can beat Whites King in his turn.

        T.onBoardTileSelected(Position2D.of(4,0));
        T.onBoardTileSelected(Position2D.of(4,1));

        //Gray beats White King
        assertEquals(T.getCurrentPlayer(),HexagonalPlayerType.GRAY);
        T.onBoardTileSelected(Position2D.of(5, 6));
        T.onBoardTileSelected(Position2D.of(5, 5));
        //next turn it's blacks turn.
        assertEquals(T.getCurrentPlayer(),HexagonalPlayerType.BLACK);
    }

}
