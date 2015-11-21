package jchess.game.movement;

import jchess.game.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

//TODO: Possibly adjust the whole test. Currently more like a first draft. (Copy-Paste of GameBoard inefficient).

/**
 * Test the movements of the bishop figure.
 *
 * @since 19.11.2015.
 */
public class BishopMovementTest {

    private static final Map<Position2D, Position2D> rows;
    private static final Map<Position2D, Figure> figures;

    static {
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

        // The bishop figure that will be tested in here.
        figuresMap.put(Position2D.of(7, 6), FigureSetup.bishop(HexagonalPlayerType.WHITE));

        // Used to test the bishop's action, when a figure of the same color is on one of its possible positions.
        figuresMap.put(Position2D.of(3, 4), FigureSetup.pawn(HexagonalPlayerType.WHITE));

        // Used to test the bishop's action, when 2 of the bishop's neighbors block the way.
        figuresMap.put(Position2D.of(6, 6), FigureSetup.knight(HexagonalPlayerType.BLACK));
        figuresMap.put(Position2D.of(7, 7), FigureSetup.pawn(HexagonalPlayerType.BLACK));

        // Used to test the capturing of an enemy.
        figuresMap.put(Position2D.of(8, 5), FigureSetup.queen(HexagonalPlayerType.GRAY));

        figures = Collections.unmodifiableMap(figuresMap);
    }


    /**
     * Creates a special chessboard for a test and checks, whether the number of returned possible {@link ChessAction}
     * from {@link BishopMovement} equals the expected number (manually checked) of {@link ChessAction}.
     *
     * @throws Exception
     */
    @Test
    public void testBishopMovement() throws Exception {
        HexagonalGameboard gameboard = new HexagonalGameboard(rows, figures);

        Figure whiteBishop = gameboard.getFigure(Position2D.of(7, 6)).get();

        BishopMovement bishopMoves = new BishopMovement();
        List<ChessAction> possibleActions = bishopMoves.getPossibleActions(whiteBishop, gameboard);

        // Test the sum of the possible ChessActions in each direction.
        assertEquals(1 + 0 + 3 + 2 + 1 + 3, possibleActions.size());
    }


    private static class FigureSetup extends Figure {
        FigureSetup(String name, FigureType type, HexagonalPlayerType player) {
            super(name, type, player);
        }

        static Figure pawn(HexagonalPlayerType player) {
            return new FigureSetup("Pawn", FigureType.PAWN, player);
        }

        static Figure rook(HexagonalPlayerType player) {
            return new FigureSetup("Rook", FigureType.ROOK,player);
        }

        static Figure bishop(HexagonalPlayerType player) {
            return new FigureSetup("Bishop", FigureType.BISHOP,player);
        }

        static Figure knight(HexagonalPlayerType player) {
            return new FigureSetup("Knight", FigureType.KNIGHT,player);
        }

        static Figure queen(HexagonalPlayerType player) {
            return new FigureSetup("Queen", FigureType.QUEEN,player);
        }

        static Figure king(HexagonalPlayerType player) {
            return new FigureSetup("King", FigureType.KING,player);
        }
    }
}
