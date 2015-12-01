package jchess.game.movement;

import jchess.game.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Test the movements of the bishop movement pattern.
 *
 * @since 19.11.2015.
 */
public class BishopMovementPatternTest {

    /**
     * Specifies the position of figures for a test of the bishop figure and checks, whether the number of returned
     * possible {@link ChessAction} from {@link BishopMovementPattern} equals the expected number (manually checked) of
     * {@link ChessAction}.
     *
     * @throws Exception
     */
    @Test
    public void testBishopMovement() throws Exception {
        GameboardCreator.FigureSetter figureSetter = new GameboardCreator.FigureSetter();

        // The bishop figure that will be tested in here.
        figureSetter.putFigure(Position2D.of(7, 6), GameboardCreator.FigureSetup.bishop(HexagonalPlayerType.WHITE));

        // Used to test the bishop's action, when a figure of the same color is on one of its possible positions.
        figureSetter.putFigure(Position2D.of(3, 4), GameboardCreator.FigureSetup.pawn(HexagonalPlayerType.WHITE));

        // Used to test the bishop's action, when 2 of the bishop's neighbors block the way.
        figureSetter.putFigure(Position2D.of(6, 6), GameboardCreator.FigureSetup.bishop(HexagonalPlayerType.BLACK));
        figureSetter.putFigure(Position2D.of(7, 7), GameboardCreator.FigureSetup.bishop(HexagonalPlayerType.BLACK));

        // Used to test the capturing of an enemy.
        figureSetter.putFigure(Position2D.of(8, 5), GameboardCreator.FigureSetup.bishop(HexagonalPlayerType.GRAY));

        HexagonalGameboard gameboard = figureSetter.createGameboard();

        Figure whiteBishop = gameboard.getFigure(Position2D.of(7, 6)).get();

        BishopMovementPattern bishopMoves = new BishopMovementPattern(BishopMovementPattern.RANGE_UNLIMITED);
        List<ChessAction> possibleActions = bishopMoves.getPossibleActions(whiteBishop, gameboard);

        // Test the sum of the possible ChessActions in each direction.
        assertEquals(1 + 0 + 3 + 2 + 1 + 3, possibleActions.size());

    }


    /**
     * Test the right behavior of the bishop when positioned at the border of the gameboard. Checks if the number of
     * returned possible {@link ChessAction} from {@link BishopMovementPattern} equals the expected number (manually checked)
     * of {@link ChessAction}.
     *
     * @throws Exception
     */
    @Test
    public void testBishopMovementAtBorder() throws Exception {
        GameboardCreator.FigureSetter figureSetter = new GameboardCreator.FigureSetter();

        // The bishop figure that will be tested in here.
        figureSetter.putFigure(Position2D.of(5, 12), GameboardCreator.FigureSetup.bishop(HexagonalPlayerType.BLACK));

        // Figure that can be caught by the bishop.
        figureSetter.putFigure(Position2D.of(3, 8), GameboardCreator.FigureSetup.pawn(HexagonalPlayerType.WHITE));

        // Figures that blocks the way of the bishop. Can not be caught.
        figureSetter.putFigure(Position2D.of(7, 9), GameboardCreator.FigureSetup.pawn(HexagonalPlayerType.WHITE));
        figureSetter.putFigure(Position2D.of(8, 10), GameboardCreator.FigureSetup.pawn(HexagonalPlayerType.GRAY));

        HexagonalGameboard gameboard = figureSetter.createGameboard();

        Figure blackBishop = gameboard.getFigure(Position2D.of(5, 12)).get();

        BishopMovementPattern bishopMoves = new BishopMovementPattern(BishopMovementPattern.RANGE_UNLIMITED);
        List<ChessAction> possibleActions = bishopMoves.getPossibleActions(blackBishop, gameboard);

        // Test the sum of the possible ChessActions in each direction.
        assertEquals(0 + 0 + 0 + 0 + 2 + 2, possibleActions.size());

    }

}
