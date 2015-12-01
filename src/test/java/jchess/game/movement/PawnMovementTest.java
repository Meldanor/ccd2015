package jchess.game.movement;

import jchess.game.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Test the movements of the pawn.
 *
 * @since 01.12.2015.
 */
public class PawnMovementTest {

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
        figureSetter.putFigure(Position2D.of(4, 10), GameboardCreator.FigureSetup.pawn(HexagonalPlayerType.WHITE));
        figureSetter.putFigure(Position2D.of(5, 9), GameboardCreator.FigureSetup.pawn(HexagonalPlayerType.BLACK));
        figureSetter.putFigure(Position2D.of(7, 10), GameboardCreator.FigureSetup.pawn(HexagonalPlayerType.WHITE));

        HexagonalGameboard gameboard = figureSetter.createGameboard();

        PawnMovement pawnMoves = new PawnMovement();

        Figure whitePawn = gameboard.getFigure(Position2D.of(1, 1)).get();
        List<ChessAction> possibleActions = pawnMoves.getPossibleActions(whitePawn, gameboard);
        assertEquals(2 + 2, possibleActions.size());

        Figure blackPawn = gameboard.getFigure(Position2D.of(12, 8)).get();
        possibleActions = pawnMoves.getPossibleActions(blackPawn, gameboard);
        assertEquals(2 + 2, possibleActions.size());

        Figure grayPawn = gameboard.getFigure(Position2D.of(6, 11)).get();
        possibleActions = pawnMoves.getPossibleActions(grayPawn, gameboard);
        assertEquals(2 + 2 + 1 + 1 + 1, possibleActions.size());
    }

}
