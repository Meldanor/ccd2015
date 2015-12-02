package jchess.game.movement;

import javafx.geometry.Pos;
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
     * Test the very first pawn movements (for every color). Pawns would be allowed to move two fields.
     */
    @Test
    public void testVeryFirstPawnMovements() {
        GameboardCreator.FigureSetter figureSetter = new GameboardCreator.FigureSetter();

        // A white pawn.
        figureSetter.putFigure(Position2D.of(1, 1), GameboardCreator.FigureSetup.pawn(HexagonalPlayerType.WHITE));

        // A black pawn at the border of the gameboard.
        figureSetter.putFigure(Position2D.of(12, 8), GameboardCreator.FigureSetup.pawn(HexagonalPlayerType.BLACK));

        // A gray pawn that can catch three other figures.
        figureSetter.putFigure(Position2D.of(6, 11), GameboardCreator.FigureSetup.pawn(HexagonalPlayerType.GRAY));

        // The three figures the gray pawn can catch.
        figureSetter.putFigure(Position2D.of(4, 10), GameboardCreator.FigureSetup.knight(HexagonalPlayerType.WHITE));
        figureSetter.putFigure(Position2D.of(5, 9), GameboardCreator.FigureSetup.knight(HexagonalPlayerType.BLACK));
        figureSetter.putFigure(Position2D.of(7, 10), GameboardCreator.FigureSetup.knight(HexagonalPlayerType.WHITE));

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
        Figure whiteKnight1 = gameboard.getFigure(Position2D.of(4, 10)).get();
        Figure blackKnight = gameboard.getFigure(Position2D.of(5, 9)).get();
        Figure whiteKnight2 = gameboard.getFigure(Position2D.of(7, 10)).get();
        possibleActions = pawnMoves.getPossibleActions(grayPawn, gameboard);

        expectedActions = new ArrayList<>();
        expectedActions.add(pawnMoves.moveTo(grayPawn, Position2D.of(6, 10)));
        expectedActions.add(pawnMoves.moveTo(grayPawn, Position2D.of(6, 9)));
        expectedActions.add(pawnMoves.moveTo(grayPawn, Position2D.of(5, 10)));
        expectedActions.add(pawnMoves.moveTo(grayPawn, Position2D.of(4, 9)));
        expectedActions.add(pawnMoves.captureEnemy(grayPawn, whiteKnight1, Position2D.of(4, 10)));
        expectedActions.add(pawnMoves.captureEnemy(grayPawn, blackKnight, Position2D.of(5, 9)));
        expectedActions.add(pawnMoves.captureEnemy(grayPawn, whiteKnight2, Position2D.of(7, 10)));

        assertEquals(possibleActions.size(), expectedActions.size());

        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }

    }


    /**
     * Test pawns that were already moved before.
     */
    @Test
    public void testStandardPawnMovements() {
        GameboardCreator.FigureSetter figureSetter = new GameboardCreator.FigureSetter();

        // A white pawn at the border of the gameboard.
        figureSetter.putFigure(Position2D.of(2, 0), GameboardCreator.FigureSetup.pawn(HexagonalPlayerType.WHITE));

        // A figure the white pawn can catch.
        figureSetter.putFigure(Position2D.of(3, 2), GameboardCreator.FigureSetup.bishop(HexagonalPlayerType.BLACK));

        // A black pawn.
        figureSetter.putFigure(Position2D.of(7, 4), GameboardCreator.FigureSetup.pawn(HexagonalPlayerType.BLACK));

        // A gray pawn.
        figureSetter.putFigure(Position2D.of(3, 7), GameboardCreator.FigureSetup.pawn(HexagonalPlayerType.GRAY));

        // Three figures the gray pawn could(!) catch.
        figureSetter.putFigure(Position2D.of(1, 6), GameboardCreator.FigureSetup.rook(HexagonalPlayerType.WHITE));
        figureSetter.putFigure(Position2D.of(2, 5), GameboardCreator.FigureSetup.rook(HexagonalPlayerType.BLACK));
        figureSetter.putFigure(Position2D.of(4, 6), GameboardCreator.FigureSetup.queen(HexagonalPlayerType.WHITE));

        // To figures blocking the way for the gray pawn to catch the white rook. Also disables the pawn to do one of
        // his normal moves.
        figureSetter.putFigure(Position2D.of(2, 6), GameboardCreator.FigureSetup.rook(HexagonalPlayerType.GRAY));
        figureSetter.putFigure(Position2D.of(2, 7), GameboardCreator.FigureSetup.knight(HexagonalPlayerType.WHITE));

        HexagonalGameboard gameboard = figureSetter.createGameboard();
        PawnMovement pawnMoves = new PawnMovement();

        // Test for white pawn.
        Figure whitePawn = gameboard.getFigure(Position2D.of(2, 0)).get();
        Figure blackBishop = gameboard.getFigure(Position2D.of(3, 2)).get();
        List<ChessAction> possibleActions = pawnMoves.getPossibleActions(whitePawn, gameboard);

        List<ChessAction> expectedActions = new ArrayList<>();
        expectedActions.add(pawnMoves.moveTo(whitePawn, Position2D.of(3, 0)));
        expectedActions.add(pawnMoves.moveTo(whitePawn, Position2D.of(3, 1)));
        expectedActions.add(pawnMoves.captureEnemy(whitePawn, blackBishop, Position2D.of(3, 2)));

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
        Figure blackRook = gameboard.getFigure(Position2D.of(2, 5)).get();
        Figure whiteQueen = gameboard.getFigure(Position2D.of(4, 6)).get();
        possibleActions = pawnMoves.getPossibleActions(grayPawn, gameboard);

        expectedActions = new ArrayList<>();
        expectedActions.add(pawnMoves.moveTo(grayPawn, Position2D.of(3, 6)));
        expectedActions.add(pawnMoves.captureEnemy(grayPawn, whiteQueen, Position2D.of(4, 6)));
        expectedActions.add(pawnMoves.captureEnemy(grayPawn, blackRook, Position2D.of(2, 5)));

        assertEquals(possibleActions.size(), expectedActions.size());

        for (ChessAction expected : expectedActions) {
            assertTrue(possibleActions.contains(expected));
        }
    }

}
