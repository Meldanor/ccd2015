package jchess.game;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @since 10.11.2015
 */
public class HexagonalGameboardTest {

    @Test
    public void testIsInField() throws Exception {
        Gameboard<Position2D> gameboard = new DefaultHexagonalGameboard();
        // Test the main diagonal
        for (int i = 0; i <= 12; i++) {
            assertTrue(gameboard.isInField(Position2D.of(i, i)));
        }
        assertFalse(gameboard.isInField(Position2D.of(-1, 0)));
        assertFalse(gameboard.isInField(Position2D.of(-1, -1)));
        assertFalse(gameboard.isInField(Position2D.of(0, -1)));
        assertFalse(gameboard.isInField(Position2D.of(13, 0)));
        assertFalse(gameboard.isInField(Position2D.of(12, 6)));
    }

    @Test
    public void testNeighbors() throws Exception {
        Gameboard<Position2D> gameboard = new DefaultHexagonalGameboard();
        List<Position2D> neighbors = gameboard.getNeighbors(Position2D.of(6, 6));
        assertEquals(6, neighbors.size());
        assertTrue(neighbors.stream().anyMatch(p -> p == Position2D.of(5, 5)));
        assertTrue(neighbors.stream().anyMatch(p -> p == Position2D.of(5, 6)));
        assertTrue(neighbors.stream().anyMatch(p -> p == Position2D.of(6, 7)));
        assertTrue(neighbors.stream().anyMatch(p -> p == Position2D.of(7, 7)));
        assertTrue(neighbors.stream().anyMatch(p -> p == Position2D.of(7, 6)));
        assertTrue(neighbors.stream().anyMatch(p -> p == Position2D.of(6, 5)));

        neighbors = gameboard.getNeighbors(Position2D.of(0, 0));
        assertEquals(3, neighbors.size());
        assertTrue(neighbors.stream().anyMatch(p -> p == Position2D.of(0, 1)));
        assertTrue(neighbors.stream().anyMatch(p -> p == Position2D.of(1, 0)));
        assertTrue(neighbors.stream().anyMatch(p -> p == Position2D.of(1, 1)));
    }

    @Test
    public void testGetFigure() throws Exception {
        Gameboard<Position2D> gameboard = new DefaultHexagonalGameboard();
        // There should be a white rook
        assertTrue(gameboard.getFigure(Position2D.of(0, 0)).isPresent());
        assertEquals(FigureType.ROOK, gameboard.getFigure(Position2D.of(0, 0)).get().getType());

        // There should be nothing
        assertFalse(gameboard.getFigure(Position2D.of(5, 5)).isPresent());
    }
}