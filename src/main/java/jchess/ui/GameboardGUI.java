package jchess.ui;

import jchess.game.Position2D;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class GameboardGUI {

    private static Map<HexagonalFieldShape, Position2D> shapePosition;

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setUndecorated(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        int windowWidth = 806;
        int windowHeight = 720;
        window.setBounds((int) (screenWidth - windowWidth) / 2, (int) (screenHeight - windowHeight) / 2, windowWidth,
                windowHeight);

        // Use the following command to load the gameboard from an image resource.
        window.add(new JLabel(new ImageIcon("src/main/resources/jchess/images/chessboard.png")));

        // Use the following two lines to draw the gameboard at runtime (on a black background).
        window.getContentPane().add(new GameboardDrawer());
        window.getContentPane().setBackground(Color.BLACK);

        window.setVisible(true);

        shapePosition = new HashMap<>();
        shapePosition.putAll(mapRow(new Point(185, 35), 8, Position2D.of(0, 0)));
        shapePosition.putAll(mapRow(new Point(154, 89), 9, Position2D.of(1, 0)));
        shapePosition.putAll(mapRow(new Point(123, 143), 10, Position2D.of(2, 0)));
        shapePosition.putAll(mapRow(new Point(92, 197), 11, Position2D.of(3, 0)));
        shapePosition.putAll(mapRow(new Point(61, 251), 12, Position2D.of(4, 0)));
        shapePosition.putAll(mapRow(new Point(30, 305), 13, Position2D.of(5, 0)));
        shapePosition.putAll(mapRow(new Point(61, 359), 12, Position2D.of(6, 1)));
        shapePosition.putAll(mapRow(new Point(92, 413), 11, Position2D.of(7, 2)));
        shapePosition.putAll(mapRow(new Point(123, 467), 10, Position2D.of(8, 3)));
        shapePosition.putAll(mapRow(new Point(154, 521), 9, Position2D.of(9, 4)));
        shapePosition.putAll(mapRow(new Point(185, 575), 8, Position2D.of(10, 5)));
        shapePosition.putAll(mapRow(new Point(216, 629), 7, Position2D.of(11, 6)));
        shapePosition.putAll(mapRow(new Point(247, 683), 6, Position2D.of(12, 7)));

        window.getContentPane().addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // Put lines from "mouseReleased(MouseEvent e)" in here instead???
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                for (Map.Entry<HexagonalFieldShape, Position2D> entry : shapePosition.entrySet()) {
                    if (entry.getKey().contains(e.getX(), e.getY())) {
                        System.out.println("Position2D: " + entry.getValue().getX() + " " + entry.getValue().getY());
                        break;
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }


    /**
     * Map the {@link HexagonalFieldShape}s of a single row to its appropriate {@link Position2D}s.
     *
     * @param firstColumnFieldCenter The center coordinates (in pixels) of the first polygon that shall be drawn.
     * @param numOfElementsInRow     The number of elements in the row (elements with the same x value).
     * @param firstPos2D             The {@link Position2D} of the first element in the current row.
     * @return A map ({@link HexagonalFieldShape}, {@link Position2D}) of all the elements in the current row.
     */
    private static Map mapRow(Point firstColumnFieldCenter, int numOfElementsInRow, Position2D firstPos2D) {
        Map<HexagonalFieldShape, Position2D> map = new HashMap<>();

        for (int i = 0; i < numOfElementsInRow; i++) {
            HexagonalFieldShape shape = new HexagonalFieldShape(
                    new Point((int) firstColumnFieldCenter.getX() + (i * 62), (int) firstColumnFieldCenter.getY()));

            map.put(shape, Position2D.of(firstPos2D.getX(), firstPos2D.getY() + i));
        }

        return map;
    }

}