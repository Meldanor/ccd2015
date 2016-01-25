package jchess.game;

import java.util.HashMap;
import java.util.Map;

/**
 * A two dimension implementation of {@link Position}.
 * <p>
 * This class is immutable and uses the Flyweight pattern to share instances instead of creating them.
 *
 * @since 03.11.2015
 */
public final class Position2D implements Position {

    private final static Position2DFactory factory = new Position2DFactory();

    private final int x;
    private final int y;

    public Position2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String getChessNotation() {
        return null;
    }

    /**
     * @return The x-coordinate. Can be negative or zero.
     */
    public int getX() {
        return x;
    }

    /**
     * @return The y-coordinate. Can be negative or zero.
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position2D that = (Position2D) o;

        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Position2D{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }

    /**
     * Wraps the x and y coordinate via a Position2D instance and returns it. This will return a single instance for
     * every coordinate combination.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return An immutable shared instance of Position2D.
     */
    public static Position2D of(int x, int y) {
        return factory.get(x, y);
    }

    // Flyweight pattern factory.
    private static class Position2DFactory {

        private Map<Integer, Map<Integer, Position2D>> cache;

        private Position2DFactory() {
            cache = new HashMap<>();
        }

        /**
         * Constructs if absent an instance of Position2D.
         *
         * @param x The x-coordinate.
         * @param y The y-coordinate.
         * @return A new instance if this combination is unknown, otherwise an already existing instance.
         */
        Position2D get(int x, int y) {
            Map<Integer, Position2D> firstDimension = cache.computeIfAbsent(x, i -> new HashMap<>());
            return firstDimension.computeIfAbsent(y, i -> new Position2D(x, y));
        }
    }
}
