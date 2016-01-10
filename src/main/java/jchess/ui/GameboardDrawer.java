package jchess.ui;

import jchess.game.Position2D;

import javax.swing.*;
import java.awt.*;

/**
 * A class (currently optional), that creates a gameboard by drawing appropriate polygons.
 *
 * @since 30.12.2015.
 */
public class GameboardDrawer extends JComponent {

    public void paint(Graphics g) {
        drawColumn(g, new Point(185, 35), 8, Position2D.of(0, 0));
        drawColumn(g, new Point(154, 89), 9, Position2D.of(1, 0));
        drawColumn(g, new Point(123, 143), 10, Position2D.of(2, 0));
        drawColumn(g, new Point(92, 197), 11, Position2D.of(3, 0));
        drawColumn(g, new Point(61, 251), 12, Position2D.of(4, 0));
        drawColumn(g, new Point(30, 305), 13, Position2D.of(5, 0));
        drawColumn(g, new Point(61, 359), 12, Position2D.of(6, 1));
        drawColumn(g, new Point(92, 413), 11, Position2D.of(7, 2));
        drawColumn(g, new Point(123, 467), 10, Position2D.of(8, 3));
        drawColumn(g, new Point(154, 521), 9, Position2D.of(9, 4));
        drawColumn(g, new Point(185, 575), 8, Position2D.of(10, 5));
        drawColumn(g, new Point(216, 629), 7, Position2D.of(11, 6));
        drawColumn(g, new Point(247, 683), 6, Position2D.of(12, 7));
    }

    /**
     * Draws all the fields with the same x value ({@link Position2D}).
     *
     * @param g                      The {@link Graphics} object from the paint method.
     * @param firstColumnFieldCenter The center coordinates (in pixels) of the first polygon that shall be drawn.
     * @param numOfElementsInRow     The number of elements in the row (elements with the same x value).
     * @param firstPos2D             The {@link Position2D} of the first element in the current row.
     */
    public void drawColumn(Graphics g, Point firstColumnFieldCenter, int numOfElementsInRow, Position2D firstPos2D) {

        for (int i = 0; i < numOfElementsInRow; i++) {
            HexagonalFieldShape shape = new HexagonalFieldShape(
                    new Point((int) firstColumnFieldCenter.getX() + (i * 62), (int) firstColumnFieldCenter.getY()));

            if ((i + firstPos2D.getX() + firstPos2D.getY()) % 3 == 0)
                g.setColor(new Color(222, 255, 214));
            else if ((i + firstPos2D.getX() + firstPos2D.getY()) % 3 == 1)
                g.setColor(new Color(74, 166, 49));
            else
                g.setColor(new Color(140, 219, 115));

            g.fillPolygon(shape.getShape());
        }
    }

}
