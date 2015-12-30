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
//        window.getContentPane().add(new GameboardDrawer());
//        window.getContentPane().setBackground(Color.BLACK);

        window.setVisible(true);

        shapePosition = new HashMap<>();
        shapePosition.putAll(mapColumn(new Point(185, 35), 6, Position2D.of(0, 0)));
        shapePosition.putAll(mapColumn(new Point(247, 35), 7, Position2D.of(0, 1)));
        shapePosition.putAll(mapColumn(new Point(309, 35), 8, Position2D.of(0, 2)));
        shapePosition.putAll(mapColumn(new Point(371, 35), 9, Position2D.of(0, 3)));
        shapePosition.putAll(mapColumn(new Point(433, 35), 10, Position2D.of(0, 4)));
        shapePosition.putAll(mapColumn(new Point(495, 35), 11, Position2D.of(0, 5)));
        shapePosition.putAll(mapColumn(new Point(557, 35), 12, Position2D.of(0, 6)));
        shapePosition.putAll(mapColumn(new Point(619, 35), 13, Position2D.of(0, 7)));
        shapePosition.putAll(mapColumn(new Point(650, 89), 12, Position2D.of(1, 8)));
        shapePosition.putAll(mapColumn(new Point(681, 143), 11, Position2D.of(2, 9)));
        shapePosition.putAll(mapColumn(new Point(712, 197), 10, Position2D.of(3, 10)));
        shapePosition.putAll(mapColumn(new Point(743, 251), 9, Position2D.of(4, 11)));
        shapePosition.putAll(mapColumn(new Point(774, 305), 8, Position2D.of(5, 12)));

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
                        return;
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
     * Map the {@link HexagonalFieldShape}s of a single column to its appropriate {@link Position2D}s.
     *
     * @param firstColumnFieldCenter The center coordinates (in pixels) of the first polygon that shall be drawn.
     * @param numOfRowsInColumn      The number of elements in the column (elements with the same y value).
     * @param firstPos2D             The {@link Position2D} of the first/top element of the current column.
     * @return A map ({@link HexagonalFieldShape}, {@link Position2D}) of all the elements in the current column.
     */
    private static Map mapColumn(Point firstColumnFieldCenter, int numOfRowsInColumn, Position2D firstPos2D) {
        Map<HexagonalFieldShape, Position2D> map = new HashMap<>();

        for (int i = 0; i < numOfRowsInColumn; i++) {
            HexagonalFieldShape shape = new HexagonalFieldShape(new Point(
                    (int) firstColumnFieldCenter.getX() - (i * 31), (int) firstColumnFieldCenter.getY() + (i * 54)));

            map.put(shape, Position2D.of(firstPos2D.getX() + i, firstPos2D.getY()));
        }

        return map;
    }

}