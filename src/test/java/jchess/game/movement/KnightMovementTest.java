package jchess.game.movement;

import jchess.game.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test knight movements
 * Created by stephan on 26.11.2015.
 * TODO: After Issue CCD15-26 is done, replace moveTo and captureEnemy usage.
 */
public class KnightMovementTest {
    //Arrange(Alle Variablen anlegen), Act (Test laufen lassen, erwartetes Ergebnis), Assert(Pruefen)

    @Test
    public void KnightCornerTest() throws Exception {
        //Arrange
        GameboardCreator.FigureSetter figureSetter = new GameboardCreator.FigureSetter();

        //Test1: Knight is positioned in top left corner
        figureSetter.putFigure(Position2D.of(0, 0), DefaultFigures.knight(HexagonalPlayerType.WHITE));

        HexagonalGameboard gameboard = figureSetter.createGameboard();
        Figure knightInCorner = gameboard.getFigure(Position2D.of(0, 0)).get();
        KnightMovement knightMovement = new KnightMovement();

        List<ChessAction> expectedActions = new ArrayList<>();
        expectedActions.add(knightMovement.moveTo(knightInCorner, Position2D.of(3, 1)));
        expectedActions.add(knightMovement.moveTo(knightInCorner, Position2D.of(3, 2)));
        expectedActions.add(knightMovement.moveTo(knightInCorner, Position2D.of(2, 3)));
        expectedActions.add(knightMovement.moveTo(knightInCorner, Position2D.of(1, 3)));
        //Act
        List<ChessAction> actualActions = knightMovement.getPossibleActions(knightInCorner, gameboard);
        //Assert
        assertEquals(expectedActions.size(), actualActions.size());
        for (ChessAction expected : expectedActions) {
            assertTrue(actualActions.contains(expected));
        }
    }

    @Test
    public void KnightCentralTest() throws Exception {
        //Arrange
        GameboardCreator.FigureSetter figureSetter = new GameboardCreator.FigureSetter();
        //Test 2: Knight is positioned central, free movement.
        figureSetter.putFigure(Position2D.of(3, 3), DefaultFigures.knight(HexagonalPlayerType.WHITE));

        HexagonalGameboard gameboard = figureSetter.createGameboard();
        Figure knightCentral = gameboard.getFigure(Position2D.of(3, 3)).get();
        KnightMovement knightMovement = new KnightMovement();

        List<ChessAction> expectedActions = new ArrayList<>();
        expectedActions.add(knightMovement.moveTo(knightCentral, Position2D.of(1, 0)));
        expectedActions.add(knightMovement.moveTo(knightCentral, Position2D.of(0, 1)));
        expectedActions.add(knightMovement.moveTo(knightCentral, Position2D.of(0, 2)));
        expectedActions.add(knightMovement.moveTo(knightCentral, Position2D.of(1, 4)));
        expectedActions.add(knightMovement.moveTo(knightCentral, Position2D.of(2, 5)));
        expectedActions.add(knightMovement.moveTo(knightCentral, Position2D.of(4, 6)));
        expectedActions.add(knightMovement.moveTo(knightCentral, Position2D.of(5, 6)));
        expectedActions.add(knightMovement.moveTo(knightCentral, Position2D.of(6, 5)));
        expectedActions.add(knightMovement.moveTo(knightCentral, Position2D.of(6, 4)));
        expectedActions.add(knightMovement.moveTo(knightCentral, Position2D.of(5, 2)));
        expectedActions.add(knightMovement.moveTo(knightCentral, Position2D.of(4, 1)));
        expectedActions.add(knightMovement.moveTo(knightCentral, Position2D.of(2, 0)));
        //Act
        List<ChessAction> actualActions = knightMovement.getPossibleActions(knightCentral, gameboard);
        //Assert
        assertEquals(expectedActions.size(), actualActions.size());
        for (ChessAction expected : expectedActions) {
            assertTrue(actualActions.contains(expected));
        }
    }

    @Test
    public void KnightSurrounded() throws Exception {
        //Arrange
        GameboardCreator.FigureSetter figureSetter = new GameboardCreator.FigureSetter();
        //Test 3: Knight is positioned with surrounding figures.
        figureSetter.putFigure(Position2D.of(8, 8), DefaultFigures.knight(HexagonalPlayerType.WHITE));
        figureSetter.putFigure(Position2D.of(5, 7), DefaultFigures.bishop(HexagonalPlayerType.GRAY));
        figureSetter.putFigure(Position2D.of(9, 11), DefaultFigures.rook(HexagonalPlayerType.BLACK));
        figureSetter.putFigure(Position2D.of(11, 10), DefaultFigures.king(HexagonalPlayerType.WHITE));

        HexagonalGameboard gameboard = figureSetter.createGameboard();
        Figure knightSurrounded =  gameboard.getFigure(Position2D.of(8, 8)).get();
        Figure bishopEnemy =  gameboard.getFigure(Position2D.of(5, 7)).get();
        Figure rookEnemy =  gameboard.getFigure(Position2D.of(9, 11)).get();
        Figure kingAllied =  gameboard.getFigure(Position2D.of(11, 10)).get();
        KnightMovement knightMovement = new KnightMovement();

        List<ChessAction> expectedActions = new ArrayList<>();
        expectedActions.add(knightMovement.moveTo(knightSurrounded, Position2D.of(6, 5)));
        expectedActions.add(knightMovement.moveTo(knightSurrounded, Position2D.of(5, 6)));
        expectedActions.add(knightMovement.captureEnemy(knightSurrounded, bishopEnemy, Position2D.of(5, 7)));
        expectedActions.add(knightMovement.moveTo(knightSurrounded, Position2D.of(6, 9)));
        expectedActions.add(knightMovement.moveTo(knightSurrounded, Position2D.of(7, 10)));
        expectedActions.add(knightMovement.captureEnemy(knightSurrounded, rookEnemy, Position2D.of(9, 11)));
        expectedActions.add(knightMovement.moveTo(knightSurrounded, Position2D.of(10, 11)));
        // expectedActions.add(knightMovement.moveTo(knightCentral,Position2D.of(11,10)));
        expectedActions.add(knightMovement.moveTo(knightSurrounded, Position2D.of(11, 9)));
        expectedActions.add(knightMovement.moveTo(knightSurrounded, Position2D.of(10, 7)));
        expectedActions.add(knightMovement.moveTo(knightSurrounded, Position2D.of(9, 6)));
        expectedActions.add(knightMovement.moveTo(knightSurrounded, Position2D.of(7, 5)));
        //Act
        List<ChessAction> actualActions = knightMovement.getPossibleActions(knightSurrounded, gameboard);
        //Assert
        assertEquals(expectedActions.size(), actualActions.size());
        for (ChessAction expected : expectedActions) {
            assertTrue(actualActions.contains(expected));
        }
    }
}
