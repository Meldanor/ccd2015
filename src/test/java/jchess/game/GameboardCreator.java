package jchess.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class creates the {@link DefaultHexagonalGameboard} without automatically setting the figures. The developer
 * gets the opportunity to set the figures on his own. This way, the creation of test cases for unit tests will be
 * simplified. FOR TESTING ONLY.
 *
 * @since 28.11.2015.
 */
public class GameboardCreator extends HexagonalGameboard {

    private static final Map<Position2D, Position2D> rows;

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
    }


    /**
     * Create a {@link HexagonalGameboard} including figures set by the user.
     *
     * @param figures The figures and their respective positions.
     */
    private GameboardCreator(Map<Position2D, Figure> figures) {
        super(rows, figures);
    }


    /**
     * Used for setting figures (including their respective positions) and getting a gameboard containing these figures
     * at the specified positions.
     */
    public static class FigureSetter {

        private Map<Position2D, Figure> figuresMap;

        /**
         * Holds all the figures and their respective positions, that can be set by the user.
         */
        public FigureSetter() {
            figuresMap = new HashMap<>();
        }


        /**
         * @return A Map of figures and their positions.
         */
        public Map<Position2D, Figure> getFiguresMap() {
            return figuresMap;
        }


        /**
         * @param figuresMap A Map of figures and their positions.
         */
        public void setFiguresMap(Map<Position2D, Figure> figuresMap) {
            this.figuresMap = figuresMap;
        }


        /**
         * Specify a figure and its position on a gameboard.
         *
         * @param position Position of the figure.
         * @param figure   The figure itself.
         */
        public void putFigure(Position2D position, Figure figure) {
            figuresMap.put(position, figure);
        }


        /**
         * Get the gameboard including all the figures previously set by
         * {@link #putFigure(Position2D, Figure)} or {@link #setFiguresMap(Map)}.
         *
         * @return A {@link HexagonalGameboard} that may include figures.
         */
        public HexagonalGameboard createGameboard() {
            return new GameboardCreator(Collections.unmodifiableMap(figuresMap));
        }
    }
}
