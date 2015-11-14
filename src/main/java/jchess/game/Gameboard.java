package jchess.game;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The base interface for the gameboard to hold all references of {@link Figure} in the game and their positions.
 * Provide {@link Position} based access for the figures.
 *
 * @since 03.11.2015
 */
public interface Gameboard<T extends Position> {

    /**
     * Get the figure at a position.
     *
     * @param position The possible of the figure. Must be inside the field, otherwise an ArrayIndexOutOfBoundsException
     *                 will be thrown.
     * @return If there is a figure at the position, a non-empty Optional will be returned. Otherwise the Optional is
     * empty.
     * @throws ArrayIndexOutOfBoundsException If the position is outside the field.
     */
    Optional<Figure> getFigure(T position);

    /**
     * Check, if the position is in the field.
     *
     * @param position The position to check.
     * @return <code>True</code> if, and only if, the position is a valid position of the field, regardless there is
     * a figure or not on it. <code>False</code> otherwise.
     */
    boolean isInField(T position);

    /**
     * Change a figures position to the end position. There is no check if the move was valid according the rules.
     *
     * @param start The start position. It must contain a figure, otherwise a MovementException is thrown. Also the
     *              position must be in the field.
     * @param end   The end position. Must be in the field, otherwise a MovementException is thrown.
     * @return If there was a figure at the end position, the Optional will contain it. Otherwise, it is empty.
     * @throws MovementException There was no figure at the start, or the start or end positions are invalid.
     */
    Optional<Figure> moveTo(T start, T end);

    /**
     * Get the position of a single figure.
     *
     * @param figure The figure. Must be not null.
     * @return The position of the figure.
     */
    Position2D getPositionOf(Figure figure);

    /**
     * Return a list of neighbor position of a position.
     *
     * @param position The position. Must be not null and must exist.
     * @return A list of valid position.
     */
    List<T> getNeighbors(T position);

    /**
     * @return A non-modifiable mapping of all figures and their positions.
     */
    Map<T, Figure> getAllFigures();

    class MovementException extends RuntimeException {
        public MovementException(String message) {
            super(message);
        }
    }
}
