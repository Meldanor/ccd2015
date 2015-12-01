package jchess.game.movement;

import jchess.game.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the movements of the bishop movement pattern.
 *
 * @since 19.11.2015.
 */
public class BishopMovementPatternTest {

    /**
     * Specifies the position of figures for a test of the bishop figure and checks, whether the expected
     * {@link ChessAction} (manually checked) are equal to those returned by
     * {@link BishopMovementPattern#getPossibleActions(Figure, Gameboard)}.
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
        Figure blackBishop = gameboard.getFigure(Position2D.of(8, 5)).get();

        BishopMovementPattern bishopMoves = new BishopMovementPattern();
        List<ChessAction> possibleActions = bishopMoves.getPossibleActions(whiteBishop, gameboard);


        // Create a manually checked list of ChessActions, that the test case should deliver.
        List<ChessAction> expectedActions = new ArrayList<>();
        expectedActions.add(bishopMoves.moveTo(whiteBishop, Position2D.of(5, 5)));
        expectedActions.add(bishopMoves.moveTo(whiteBishop, Position2D.of(8, 8)));
        expectedActions.add(bishopMoves.moveTo(whiteBishop, Position2D.of(9, 10)));
        expectedActions.add(bishopMoves.moveTo(whiteBishop, Position2D.of(10, 12)));
        expectedActions.add(bishopMoves.moveTo(whiteBishop, Position2D.of(9, 7)));
        expectedActions.add(bishopMoves.moveTo(whiteBishop, Position2D.of(11, 8)));
        expectedActions.add(bishopMoves.captureEnemy(whiteBishop, blackBishop, Position2D.of(8, 5)));
        expectedActions.add(bishopMoves.moveTo(whiteBishop, Position2D.of(6, 4)));
        expectedActions.add(bishopMoves.moveTo(whiteBishop, Position2D.of(5, 2)));
        expectedActions.add(bishopMoves.moveTo(whiteBishop, Position2D.of(4, 0)));

        assertEquals(possibleActions.size(), expectedActions.size());

        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }

    }


    /**
     * Test the right behavior of the bishop when positioned at the border of the gameboard. Checks if the expected
     * {@link ChessAction} (manually checked) are equal to those returned by
     * {@link BishopMovementPattern#getPossibleActions(Figure, Gameboard)}.
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
        Figure whitePawn = gameboard.getFigure(Position2D.of(3, 8)).get();

        BishopMovementPattern bishopMoves = new BishopMovementPattern();
        List<ChessAction> possibleActions = bishopMoves.getPossibleActions(blackBishop, gameboard);

        // Create a manually checked list of ChessActions, that the test case should deliver.
        List<ChessAction> expectedActions = new ArrayList<>();
        expectedActions.add(bishopMoves.moveTo(blackBishop, Position2D.of(4, 10)));
        expectedActions.add(bishopMoves.captureEnemy(blackBishop, whitePawn, Position2D.of(3, 8)));
        expectedActions.add(bishopMoves.moveTo(blackBishop, Position2D.of(6, 11)));
        expectedActions.add(bishopMoves.moveTo(blackBishop, Position2D.of(7, 10)));

        assertEquals(possibleActions.size(), expectedActions.size());

        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }

    }

}
