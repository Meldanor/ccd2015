package jchess.game.movement;

import jchess.game.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tests for the movements of the rook.
 *
 * @since 18.11.2015.
 */
public class RookMovementPatternTest {

    private Map<Position2D, Position2D> rows = new HashMap<>();
    private Map<Position2D, Figure> figures = new HashMap<>();


    private static class FigureSetup extends Figure {
        FigureSetup(String name, FigureType type, HexagonalPlayerType player) {
            super(name, type, player);
        }

        static Figure pawn(HexagonalPlayerType player) {
            return new FigureSetup("Pawn", FigureType.PAWN, player);
        }

        static Figure rook(HexagonalPlayerType player) {
            return new FigureSetup("Rook", FigureType.ROOK, player);
        }

        static Figure bishop(HexagonalPlayerType player) {
            return new FigureSetup("Bishop", FigureType.BISHOP, player);
        }

        static Figure knight(HexagonalPlayerType player) {
            return new FigureSetup("Knight", FigureType.KNIGHT, player);
        }

        static Figure queen(HexagonalPlayerType player) {
            return new FigureSetup("Queen", FigureType.QUEEN, player);
        }

        static Figure king(HexagonalPlayerType player) {
            return new FigureSetup("King", FigureType.KING, player);
        }
    }

    @Before
    public void before() {

        rows.put(Position2D.of(0, 0), Position2D.of(0, 7));
        rows.put(Position2D.of(1, 0), Position2D.of(1, 8));
        rows.put(Position2D.of(2, 0), Position2D.of(2, 9));
        rows.put(Position2D.of(3, 0), Position2D.of(3, 10));
        rows.put(Position2D.of(4, 0), Position2D.of(4, 11));
        rows.put(Position2D.of(5, 0), Position2D.of(5, 12));
        rows.put(Position2D.of(6, 1), Position2D.of(6, 12));
        rows.put(Position2D.of(7, 2), Position2D.of(7, 12));
        rows.put(Position2D.of(8, 3), Position2D.of(8, 12));
        rows.put(Position2D.of(9, 4), Position2D.of(9, 12));
        rows.put(Position2D.of(10, 5), Position2D.of(10, 12));
        rows.put(Position2D.of(11, 6), Position2D.of(11, 12));
        rows.put(Position2D.of(12, 7), Position2D.of(12, 12));

        // The rook figure that will be tested in here.
        figures.put(Position2D.of(0, 0), FigureSetup.rook(HexagonalPlayerType.WHITE));

    }

    /**
     * Tests the movement of the rook when it's the only figure on the board.
     *
     */
    @Test
    public void testOnlyRook() throws Exception {

        HexagonalGameboard gameboard = new HexagonalGameboard(rows, figures);

        Figure rook = gameboard.getFigure(Position2D.of(0, 0)).get();

        RookMovementPattern moves = new RookMovementPattern();

        List<ChessAction> actions = moves.getPossibleActions(rook, gameboard);

        assertEquals(0 + 0 + 0 + 5 + 7 + 12, actions.size());
    }


    /**
     * Tests the limited movement of the rook when it's the only figure on the board.
     *
     */
    @Test
    public void testLimitedRook() throws Exception {
        HexagonalGameboard gameboard = new HexagonalGameboard(rows, figures);

        Figure rook = gameboard.getFigure(Position2D.of(0, 0)).get();

        RookMovementPattern moves = new RookMovementPattern(2);

        List<ChessAction> actions = moves.getPossibleActions(rook, gameboard);

        assertEquals(0 + 0 + 0 + 2 + 2 + 2, actions.size());
    }


    /**
     * Tests the movements of the rook when it's surrounded by friendly figures (as in the starting position).
     *
     */
    @Test
    public void testStandardStart() throws Exception {
        HexagonalGameboard gameboard = new DefaultHexagonalGameboard();

        Figure rook = gameboard.getFigure(Position2D.of(0,0)).get();

        RookMovementPattern moves = new RookMovementPattern();

        List<ChessAction> actions = moves.getPossibleActions(rook, gameboard);

        assertEquals(0, actions.size());
    }

}
