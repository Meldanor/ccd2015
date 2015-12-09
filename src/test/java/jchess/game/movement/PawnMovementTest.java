package jchess.game.movement;

import jchess.game.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the movements of the pawn.
 *
 * @since 01.12.2015.
 */
public class PawnMovementTest {

    /**
     * Test the very first pawn movements (for every player/color). Pawns would be allowed to move two fields.
     */
    @Test
    public void testVeryFirstPawnMovements() {
        GameboardCreator.FigureSetter figureSetter = new GameboardCreator.FigureSetter();

        // A white pawn.
        figureSetter.putFigure(Position2D.of(1, 1), DefaultFigures.pawn(HexagonalPlayerType.WHITE));

        // A black pawn at the border of the gameboard.
        figureSetter.putFigure(Position2D.of(12, 8), DefaultFigures.pawn(HexagonalPlayerType.BLACK));

        // A gray pawn.
        figureSetter.putFigure(Position2D.of(6, 11), DefaultFigures.pawn(HexagonalPlayerType.GRAY));

        HexagonalGameboard gameboard = figureSetter.createGameboard();
        PawnMovement pawnMoves = new PawnMovement();


        // Test for white pawn.
        Figure whitePawn = gameboard.getFigure(Position2D.of(1, 1)).get();
        List<ChessAction> possibleActions = pawnMoves.getPossibleActions(whitePawn, gameboard);

        List<ChessAction> expectedActions = new ArrayList<>();
        expectedActions.add(pawnMoves.moveTo(whitePawn, Position2D.of(2, 1)));
        expectedActions.add(pawnMoves.moveTo(whitePawn, Position2D.of(3, 1)));
        expectedActions.add(pawnMoves.moveTo(whitePawn, Position2D.of(2, 2)));
        expectedActions.add(pawnMoves.moveTo(whitePawn, Position2D.of(3, 3)));

        assertEquals(possibleActions.size(), expectedActions.size());
        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }


        // Test for black pawn.
        Figure blackPawn = gameboard.getFigure(Position2D.of(12, 8)).get();
        possibleActions = pawnMoves.getPossibleActions(blackPawn, gameboard);

        expectedActions = new ArrayList<>();
        expectedActions.add(pawnMoves.moveTo(blackPawn, Position2D.of(11, 8)));
        expectedActions.add(pawnMoves.moveTo(blackPawn, Position2D.of(10, 8)));
        expectedActions.add(pawnMoves.moveTo(blackPawn, Position2D.of(12, 9)));
        expectedActions.add(pawnMoves.moveTo(blackPawn, Position2D.of(12, 10)));

        assertEquals(possibleActions.size(), expectedActions.size());
        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }


        // Test for gray pawn.
        Figure grayPawn = gameboard.getFigure(Position2D.of(6, 11)).get();
        possibleActions = pawnMoves.getPossibleActions(grayPawn, gameboard);

        expectedActions = new ArrayList<>();
        expectedActions.add(pawnMoves.moveTo(grayPawn, Position2D.of(6, 10)));
        expectedActions.add(pawnMoves.moveTo(grayPawn, Position2D.of(6, 9)));
        expectedActions.add(pawnMoves.moveTo(grayPawn, Position2D.of(5, 10)));
        expectedActions.add(pawnMoves.moveTo(grayPawn, Position2D.of(4, 9)));

        assertEquals(possibleActions.size(), expectedActions.size());
        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }
    }


    /**
     * Test the movements of pawns that were already moved before (for every player/color). Pawns can only move one
     * field then.
     */
    @Test
    public void testStandardPawnMovements() {
        GameboardCreator.FigureSetter figureSetter = new GameboardCreator.FigureSetter();

        // A white pawn at the border of the gameboard.
        figureSetter.putFigure(Position2D.of(2, 0), DefaultFigures.pawn(HexagonalPlayerType.WHITE));

        // A black pawn.
        figureSetter.putFigure(Position2D.of(7, 4), DefaultFigures.pawn(HexagonalPlayerType.BLACK));

        // A gray pawn.
        figureSetter.putFigure(Position2D.of(3, 7), DefaultFigures.pawn(HexagonalPlayerType.GRAY));

        HexagonalGameboard gameboard = figureSetter.createGameboard();
        PawnMovement pawnMoves = new PawnMovement();


        // Test for white pawn.
        Figure whitePawn = gameboard.getFigure(Position2D.of(2, 0)).get();
        List<ChessAction> possibleActions = pawnMoves.getPossibleActions(whitePawn, gameboard);

        List<ChessAction> expectedActions = new ArrayList<>();
        expectedActions.add(pawnMoves.moveTo(whitePawn, Position2D.of(3, 0)));
        expectedActions.add(pawnMoves.moveTo(whitePawn, Position2D.of(3, 1)));

        assertEquals(possibleActions.size(), expectedActions.size());
        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }


        // Test for black pawn.
        Figure blackPawn = gameboard.getFigure(Position2D.of(7, 4)).get();
        possibleActions = pawnMoves.getPossibleActions(blackPawn, gameboard);

        expectedActions = new ArrayList<>();
        expectedActions.add(pawnMoves.moveTo(blackPawn, Position2D.of(6, 4)));
        expectedActions.add(pawnMoves.moveTo(blackPawn, Position2D.of(7, 5)));

        assertEquals(possibleActions.size(), expectedActions.size());
        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }


        // Test for gray pawn.
        Figure grayPawn = gameboard.getFigure(Position2D.of(3, 7)).get();
        possibleActions = pawnMoves.getPossibleActions(grayPawn, gameboard);

        expectedActions = new ArrayList<>();
        expectedActions.add(pawnMoves.moveTo(grayPawn, Position2D.of(3, 6)));
        expectedActions.add(pawnMoves.moveTo(grayPawn, Position2D.of(2, 6)));

        assertEquals(possibleActions.size(), expectedActions.size());
        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }
    }


    /**
     * Test the capture actions of the pawn figures (for every player/color).
     */
    @Test
    public void testPawnCaptureActions() {
        GameboardCreator.FigureSetter figureSetter = new GameboardCreator.FigureSetter();

        // A white pawn and the figures it can catch.
        figureSetter.putFigure(Position2D.of(1, 4), DefaultFigures.pawn(HexagonalPlayerType.WHITE));
        figureSetter.putFigure(Position2D.of(2, 3), DefaultFigures.bishop(HexagonalPlayerType.BLACK));
        figureSetter.putFigure(Position2D.of(3, 5), DefaultFigures.queen(HexagonalPlayerType.GRAY));
        figureSetter.putFigure(Position2D.of(2, 6), DefaultFigures.knight(HexagonalPlayerType.BLACK));

        // A black pawn and the figures it can catch.
        figureSetter.putFigure(Position2D.of(8, 5), DefaultFigures.pawn(HexagonalPlayerType.BLACK));
        figureSetter.putFigure(Position2D.of(6, 4), DefaultFigures.rook(HexagonalPlayerType.GRAY));
        figureSetter.putFigure(Position2D.of(7, 6), DefaultFigures.knight(HexagonalPlayerType.WHITE));
        figureSetter.putFigure(Position2D.of(9, 7), DefaultFigures.queen(HexagonalPlayerType.GRAY));

        // A gray pawn and the figures it can catch.
        figureSetter.putFigure(Position2D.of(7, 11), DefaultFigures.pawn(HexagonalPlayerType.GRAY));
        figureSetter.putFigure(Position2D.of(8, 10), DefaultFigures.knight(HexagonalPlayerType.WHITE));
        figureSetter.putFigure(Position2D.of(6, 9), DefaultFigures.rook(HexagonalPlayerType.BLACK));
        figureSetter.putFigure(Position2D.of(5, 10), DefaultFigures.bishop(HexagonalPlayerType.WHITE));

        HexagonalGameboard gameboard = figureSetter.createGameboard();
        PawnMovement pawnMoves = new PawnMovement();


        // Test for the white pawn.
        Figure whitePawn = gameboard.getFigure(Position2D.of(1, 4)).get();
        List<ChessAction> possibleActions = pawnMoves.getPossibleActions(whitePawn, gameboard);

        Figure whitePawnTarget1 = gameboard.getFigure(Position2D.of(2, 3)).get();
        Figure whitePawnTarget2 = gameboard.getFigure(Position2D.of(3, 5)).get();
        Figure whitePawnTarget3 = gameboard.getFigure(Position2D.of(2, 6)).get();

        List<ChessAction> expectedActions = new ArrayList<>();
        expectedActions.add(pawnMoves.captureEnemy(whitePawn, whitePawnTarget1, Position2D.of(2, 3)));
        expectedActions.add(pawnMoves.captureEnemy(whitePawn, whitePawnTarget2, Position2D.of(3, 5)));
        expectedActions.add(pawnMoves.captureEnemy(whitePawn, whitePawnTarget3, Position2D.of(2, 6)));

        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }


        // Test for the black pawn.
        Figure blackPawn = gameboard.getFigure(Position2D.of(8, 5)).get();
        possibleActions = pawnMoves.getPossibleActions(blackPawn, gameboard);

        Figure blackPawnTarget1 = gameboard.getFigure(Position2D.of(6, 4)).get();
        Figure blackPawnTarget2 = gameboard.getFigure(Position2D.of(7, 6)).get();
        Figure blackPawnTarget3 = gameboard.getFigure(Position2D.of(9, 7)).get();

        expectedActions = new ArrayList<>();
        expectedActions.add(pawnMoves.captureEnemy(blackPawn, blackPawnTarget1, Position2D.of(6, 4)));
        expectedActions.add(pawnMoves.captureEnemy(blackPawn, blackPawnTarget2, Position2D.of(7, 6)));
        expectedActions.add(pawnMoves.captureEnemy(blackPawn, blackPawnTarget3, Position2D.of(9, 7)));

        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }

        // Test for gray pawn.
        Figure grayPawn = gameboard.getFigure(Position2D.of(7, 11)).get();
        possibleActions = pawnMoves.getPossibleActions(grayPawn, gameboard);

        Figure grayPawnTarget1 = gameboard.getFigure(Position2D.of(8, 10)).get();
        Figure grayPawnTarget2 = gameboard.getFigure(Position2D.of(6, 9)).get();
        Figure grayPawnTarget3 = gameboard.getFigure(Position2D.of(5, 10)).get();

        expectedActions = new ArrayList<>();
        expectedActions.add(pawnMoves.captureEnemy(grayPawn, grayPawnTarget1, Position2D.of(8, 10)));
        expectedActions.add(pawnMoves.captureEnemy(grayPawn, grayPawnTarget2, Position2D.of(6, 9)));
        expectedActions.add(pawnMoves.captureEnemy(grayPawn, grayPawnTarget3, Position2D.of(5, 10)));

        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }
    }


    /**
     * Test whether the pawn respects the rule, that it can not capture an enemy if the way is blocked by two figures.
     * Additionally tests, whether the pawn can catch an enemy, that is only half blocked (pawn is allowed to catch
     * then) and whether the pawn also respects, that it can not perform a
     * {@link BishopMovementPattern#moveTo(Figure, Position2D)} while an other figure is on the appropriate field.
     */
    @Test
    public void testPawnBlocking() {
        GameboardCreator.FigureSetter figureSetter = new GameboardCreator.FigureSetter();

        // A white pawn, figures it can catch and figures that block its way
        figureSetter.putFigure(Position2D.of(1, 4), DefaultFigures.pawn(HexagonalPlayerType.WHITE));
        figureSetter.putFigure(Position2D.of(2, 3), DefaultFigures.bishop(HexagonalPlayerType.BLACK));
        figureSetter.putFigure(Position2D.of(3, 5), DefaultFigures.queen(HexagonalPlayerType.GRAY));
        figureSetter.putFigure(Position2D.of(2, 6), DefaultFigures.knight(HexagonalPlayerType.BLACK));
        figureSetter.putFigure(Position2D.of(2, 4), DefaultFigures.rook(HexagonalPlayerType.WHITE));
        figureSetter.putFigure(Position2D.of(2, 5), DefaultFigures.pawn(HexagonalPlayerType.GRAY));

        HexagonalGameboard gameboard = figureSetter.createGameboard();
        PawnMovement pawnMoves = new PawnMovement();

        Figure whitePawn = gameboard.getFigure(Position2D.of(1, 4)).get();
        List<ChessAction> possibleActions = pawnMoves.getPossibleActions(whitePawn, gameboard);

        Figure target1 = gameboard.getFigure(Position2D.of(2, 3)).get();
        Figure target2 = gameboard.getFigure(Position2D.of(2, 6)).get();

        List<ChessAction> expectedActions = new ArrayList<>();
        expectedActions.add(pawnMoves.captureEnemy(whitePawn, target1, Position2D.of(2, 3)));
        expectedActions.add(pawnMoves.captureEnemy(whitePawn, target2, Position2D.of(2, 6)));

        assertEquals(possibleActions.size(), expectedActions.size());
        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }
    }

}
