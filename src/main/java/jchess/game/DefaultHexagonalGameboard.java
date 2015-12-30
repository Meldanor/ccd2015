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
            figuresMap.put(Position2D.of(x, y), DefaultFigures.pawn(HexagonalPlayerType.WHITE));
        }
        figuresMap.put(Position2D.of(0, 0), DefaultFigures.rook(HexagonalPlayerType.WHITE));
        figuresMap.put(Position2D.of(0, 1), DefaultFigures.knight(HexagonalPlayerType.WHITE));
        figuresMap.put(Position2D.of(0, 2), DefaultFigures.bishop(HexagonalPlayerType.WHITE));
        figuresMap.put(Position2D.of(0, 3), DefaultFigures.queen(HexagonalPlayerType.WHITE));
        figuresMap.put(Position2D.of(0, 4), DefaultFigures.king(HexagonalPlayerType.WHITE));
        figuresMap.put(Position2D.of(0, 5), DefaultFigures.knight(HexagonalPlayerType.WHITE));
        figuresMap.put(Position2D.of(0, 6), DefaultFigures.bishop(HexagonalPlayerType.WHITE));
        figuresMap.put(Position2D.of(0, 7), DefaultFigures.rook(HexagonalPlayerType.WHITE));

        // Black
        for (int x = 4, y = 0; y <= 8; x++, y++) {
            figuresMap.put(Position2D.of(x, y), DefaultFigures.pawn(HexagonalPlayerType.BLACK));
        }
        figuresMap.put(Position2D.of(5, 0), DefaultFigures.rook(HexagonalPlayerType.BLACK));
        figuresMap.put(Position2D.of(6, 1), DefaultFigures.bishop(HexagonalPlayerType.BLACK));
        figuresMap.put(Position2D.of(7, 2), DefaultFigures.knight(HexagonalPlayerType.BLACK));
        figuresMap.put(Position2D.of(8, 3), DefaultFigures.king(HexagonalPlayerType.BLACK));
        figuresMap.put(Position2D.of(9, 4), DefaultFigures.queen(HexagonalPlayerType.BLACK));
        figuresMap.put(Position2D.of(10, 5), DefaultFigures.bishop(HexagonalPlayerType.BLACK));
        figuresMap.put(Position2D.of(11, 6), DefaultFigures.knight(HexagonalPlayerType.BLACK));
        figuresMap.put(Position2D.of(12, 7), DefaultFigures.rook(HexagonalPlayerType.BLACK));

        // Gray
        for (int x = 4, y = 11; x <= 12; x++) {
            figuresMap.put(Position2D.of(x, y), DefaultFigures.pawn(HexagonalPlayerType.GRAY));
        }
        figuresMap.put(Position2D.of(5, 12), DefaultFigures.rook(HexagonalPlayerType.GRAY));
        figuresMap.put(Position2D.of(6, 12), DefaultFigures.knight(HexagonalPlayerType.GRAY));
        figuresMap.put(Position2D.of(7, 12), DefaultFigures.bishop(HexagonalPlayerType.GRAY));
        figuresMap.put(Position2D.of(8, 12), DefaultFigures.queen(HexagonalPlayerType.GRAY));
        figuresMap.put(Position2D.of(9, 12), DefaultFigures.king(HexagonalPlayerType.GRAY));
        figuresMap.put(Position2D.of(10, 12), DefaultFigures.knight(HexagonalPlayerType.GRAY));
        figuresMap.put(Position2D.of(11, 12), DefaultFigures.bishop(HexagonalPlayerType.GRAY));
        figuresMap.put(Position2D.of(12, 12), DefaultFigures.rook(HexagonalPlayerType.GRAY));

        figures = Collections.unmodifiableMap(figuresMap);
    }

    public DefaultHexagonalGameboard() {
        super(rows, figures);
    }
}
