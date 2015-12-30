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
        drawColumn(g, new Point(185, 35), 6, Position2D.of(0, 0));
        drawColumn(g, new Point(247, 35), 7, Position2D.of(0, 1));
        drawColumn(g, new Point(309, 35), 8, Position2D.of(0, 2));
        drawColumn(g, new Point(371, 35), 9, Position2D.of(0, 3));
        drawColumn(g, new Point(433, 35), 10, Position2D.of(0, 4));
        drawColumn(g, new Point(495, 35), 11, Position2D.of(0, 5));
        drawColumn(g, new Point(557, 35), 12, Position2D.of(0, 6));
        drawColumn(g, new Point(619, 35), 13, Position2D.of(0, 7));
        drawColumn(g, new Point(650, 89), 12, Position2D.of(1, 8));
        drawColumn(g, new Point(681, 143), 11, Position2D.of(2, 9));
        drawColumn(g, new Point(712, 197), 10, Position2D.of(3, 10));
        drawColumn(g, new Point(743, 251), 9, Position2D.of(4, 11));
        drawColumn(g, new Point(774, 305), 8, Position2D.of(5, 12));
    }

    /**
     * Draws all the fields with the same y value ({@link Position2D}).
     *
     * @param g                         The {@link Graphics} object from the paint method.
     * @param firstColumnFieldCenter    The center coordinates (in pixels) of the polygon that shall be drawn.
     * @param numOfRowsInColumn         The number of elements in the column (elements with the same y value).
     * @param firstPos2D                The {@link Position2D} of the first/top element of the current column.
     */
    public void drawColumn(Graphics g, Point firstColumnFieldCenter, int numOfRowsInColumn, Position2D firstPos2D) {

        for (int i = 0; i < numOfRowsInColumn; i++) {
            HexagonalFieldShape shape = new HexagonalFieldShape(new Point(
                    (int) firstColumnFieldCenter.getX() - (i * 31), (int) firstColumnFieldCenter.getY() + (i * 54)));

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
