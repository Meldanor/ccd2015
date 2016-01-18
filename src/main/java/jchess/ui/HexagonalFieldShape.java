package jchess.ui;

import java.awt.*;

/**
 * A hexagonal shape, used as a field on the gameboard.
 *
 * @since 18.12.2015.
 */
public class HexagonalFieldShape implements FieldShape {

    Point hexagonCenter;
    Polygon hexagonShape;

    /**
     * A hexagonal shape for a field on the gameboard.
     *
     * @param centerPoint The center of the hexagonal field.
     */
    public HexagonalFieldShape(Point centerPoint) {
        hexagonCenter = centerPoint;

        hexagonShape = new Polygon();
        hexagonShape.addPoint((int) hexagonCenter.getX(), (int) hexagonCenter.getY() - 35);
        hexagonShape.addPoint((int) hexagonCenter.getX() - 30, (int) hexagonCenter.getY() - 17);
        hexagonShape.addPoint((int) hexagonCenter.getX() - 30, (int) hexagonCenter.getY() + 18);
        hexagonShape.addPoint((int) hexagonCenter.getX(), (int) hexagonCenter.getY() + 36);
        hexagonShape.addPoint((int) hexagonCenter.getX() + 1, (int) hexagonCenter.getY() + 36);
        hexagonShape.addPoint((int) hexagonCenter.getX() + 31, (int) hexagonCenter.getY() + 18);
        hexagonShape.addPoint((int) hexagonCenter.getX() + 31, (int) hexagonCenter.getY() - 17);
        hexagonShape.addPoint((int) hexagonCenter.getX() + 1, (int) hexagonCenter.getY() - 35);
    }

    public Point getCenter() {
        return hexagonCenter;
    }

    public Polygon getShape() {
        return hexagonShape;
    }

}
