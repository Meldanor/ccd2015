package jchess.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a placeholder the create a default hexagonal gameboard. Will be replaced by a Gameboard builder , which will
 * read the start values from a text file.
 *
 * @since 10.11.2015
 */
public class DefaultHexagonalGameboard extends HexagonalGameboard {

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

        // White
        for (int x = 1, y = 0; y <= 8; y++) {
            figuresMap.put(Position2D.of(x, y), FigureStup.pawn());
        }
        figuresMap.put(Position2D.of(0, 0), FigureStup.rook());
        figuresMap.put(Position2D.of(0, 1), FigureStup.knight());
        figuresMap.put(Position2D.of(0, 2), FigureStup.bishop());
        figuresMap.put(Position2D.of(0, 3), FigureStup.queen());
        figuresMap.put(Position2D.of(0, 4), FigureStup.king());
        figuresMap.put(Position2D.of(0, 5), FigureStup.knight());
        figuresMap.put(Position2D.of(0, 6), FigureStup.bishop());
        figuresMap.put(Position2D.of(0, 7), FigureStup.rook());

        // Black
        for (int x = 4, y = 0; y <= 8; x++, y++) {
            figuresMap.put(Position2D.of(x, y), FigureStup.pawn());
        }
        figuresMap.put(Position2D.of(5, 0), FigureStup.rook());
        figuresMap.put(Position2D.of(6, 1), FigureStup.bishop());
        figuresMap.put(Position2D.of(7, 2), FigureStup.knight());
        figuresMap.put(Position2D.of(8, 3), FigureStup.king());
        figuresMap.put(Position2D.of(9, 4), FigureStup.queen());
        figuresMap.put(Position2D.of(10, 5), FigureStup.bishop());
        figuresMap.put(Position2D.of(11, 6), FigureStup.knight());
        figuresMap.put(Position2D.of(12, 7), FigureStup.rook());

        // Gray
        for (int x = 4, y = 1; x <= 12; x++) {
            figuresMap.put(Position2D.of(x, y), FigureStup.pawn());
        }
        figuresMap.put(Position2D.of(5, 12), FigureStup.rook());
        figuresMap.put(Position2D.of(6, 12), FigureStup.knight());
        figuresMap.put(Position2D.of(7, 12), FigureStup.bishop());
        figuresMap.put(Position2D.of(8, 12), FigureStup.queen());
        figuresMap.put(Position2D.of(9, 12), FigureStup.king());
        figuresMap.put(Position2D.of(10, 12), FigureStup.knight());
        figuresMap.put(Position2D.of(11, 12), FigureStup.bishop());
        figuresMap.put(Position2D.of(12, 12), FigureStup.rook());

        figures = Collections.unmodifiableMap(figuresMap);
    }

    public DefaultHexagonalGameboard() {
        super(rows, figures);
    }

    private static class FigureStup extends Figure {
        FigureStup(String name, FigureType type) {
            super(name, type);
        }

        static Figure pawn() {
            return new FigureStup("Pawn", FigureType.PAWN);
        }

        static Figure rook() {
            return new FigureStup("Rook", FigureType.ROOK);
        }

        static Figure bishop() {
            return new FigureStup("Bishop", FigureType.BISHOP);
        }

        static Figure knight() {
            return new FigureStup("Knight", FigureType.KNIGHT);
        }

        static Figure queen() {
            return new FigureStup("Queen", FigureType.QUEEN);
        }

        static Figure king() {
            return new FigureStup("King", FigureType.KING);
        }
    }
}
