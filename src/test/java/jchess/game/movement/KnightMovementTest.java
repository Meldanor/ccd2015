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
 */
public class KnightMovementTest {
    //Arrange(Alle Variablen anlegen), Act (Test laufen lassen, erwartetes Ergebnis), Assert(Pruefen)

    private static Map<Position2D, Position2D> rows;
    private static Map<Position2D, Figure> figures;

    @BeforeClass
    public static void init() {
        //Setting up testing environment
        Map<Position2D, Position2D> rowsMap = new HashMap<>();
        rowsMap.put(Position2D.of(0, 0), Position2D.of(0, 7));
        rowsMap.put(Position2D.of(1, 0), Position2D.of(1, 8));
        rowsMap.put(Position2D.of(2, 0), Position2D.of(2, 9));
        rowsMap.put(Position2D.of(3, 0), Position2D.of(3, 10));
        rowsMap.put(Position2D.of(4, 0), Position2D.of(4, 11));
        rowsMap.put(Position2D.of(5, 0), Position2D.of(5, 12));
        rowsMap.put(Position2D.of(6, 1), Position2D.of(6, 12));
        rowsMap.put(Position2D.of(7, 2), Position2D.of(7, 12));
        rowsMap.put(Position2D.of(8, 3), Position2D.of(8, 12));
        rowsMap.put(Position2D.of(9, 4), Position2D.of(9, 12));
        rowsMap.put(Position2D.of(10, 5), Position2D.of(10, 12));
        rowsMap.put(Position2D.of(11, 6), Position2D.of(11, 12));
        rowsMap.put(Position2D.of(12, 7), Position2D.of(12, 12));
        rows = Collections.unmodifiableMap(rowsMap);

        Map<Position2D, Figure> figuresMap = new HashMap<>();

        //Test 1: Knight Position border
        figuresMap.put(Position2D.of(0, 0), DefaultFigures.knight(HexagonalPlayerType.WHITE));
        //Test 2: Knight Position central
        figuresMap.put(Position2D.of(3, 3), DefaultFigures.knight(HexagonalPlayerType.WHITE));

        //Test 3: Knight Position with surround figures, enemy bishop, enemy rook and allied king
        figuresMap.put(Position2D.of(8, 8), DefaultFigures.knight(HexagonalPlayerType.WHITE));
        figuresMap.put(Position2D.of(5, 7), DefaultFigures.bishop(HexagonalPlayerType.GRAY));
        figuresMap.put(Position2D.of(9, 11), DefaultFigures.rook(HexagonalPlayerType.BLACK));
        figuresMap.put(Position2D.of(11, 10), DefaultFigures.king(HexagonalPlayerType.WHITE));


        figures = Collections.unmodifiableMap(figuresMap);
    }

    @Test
    public void KnightCornerTest() throws Exception {
        //Arrange
        HexagonalGameboard gameboard = new HexagonalGameboard(rows, figures);
        Figure knightInCorner = figures.get(Position2D.of(0, 0));
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
        HexagonalGameboard gameboard = new HexagonalGameboard(rows, figures);
        Figure knightCentral = figures.get(Position2D.of(3, 3));
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
        HexagonalGameboard gameboard = new HexagonalGameboard(rows, figures);
        Figure knightSurrounded = figures.get(Position2D.of(8, 8));
        Figure kingAllied = figures.get(Position2D.of(11, 10));
        Figure bishopEnemy = figures.get(Position2D.of(5, 7));
        Figure rookEnemy = figures.get(Position2D.of(5, 6));
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
