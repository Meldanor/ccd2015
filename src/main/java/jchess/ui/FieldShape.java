package jchess.ui;

import java.awt.*;

/**
 * Interface for the shape of a field on the gameboard.
 *
 * @since 30.12.2015.
 */
public interface FieldShape {

    /**
     * Get the center of the field shape.
     *
     * @return A {@link Point} representing the center of the field (pixel coordinates).
     */
    Point getCenter();

    /**
     * Get the shape of the field.
     *
     * @return The shape of the field in form of a {@link Polygon}.
     */
    Polygon getShape();

    /**
     * Check, whether the shape includes the specified pixel coordinates.
     *
     * @param x x-value of the pixel coordinate.
     * @param y y-value of the pixel coordinate.
     * @return true, if the shape contains the pixel coordinate, else false.
     */
    default boolean contains(int x, int y) {
        return getShape().contains(x, y);
    }

}
