package jchess.ui;

import jchess.Core;
import jchess.event.EventBroadcaster;
import jchess.event.EventType;
import jchess.event.impl.FigureSelectedEvent;
import jchess.event.impl.GameboardUpdatedEvent;
import jchess.event.impl.PositionClickedEvent;
import jchess.game.Figure;
import jchess.game.HexagonalGameboard;
import jchess.game.HexagonalPlayerType;
import jchess.game.Position2D;
import jchess.game.movement.ChessAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GUI for a hexagonal chessboard gameboard.
 */
public class HexagonalGameboardGUI {

    private static final String IMAGE_BASE = "src/main/resources/jchess/images/";
    private static final String BACKGROUND_IMAGE = IMAGE_BASE + "chessboard.png";
    private static final String IMAGE_OVERLAY_ACTION = "Overlay-Active.png";
    private static final String IMAGE_OVERLAY_MOVE = "Overlay-Moves.png";

    private JLayeredPane pane;
    private JFrame window;
    private Map<HexagonalFieldShape, Position2D> positionByShape;
    private Map<Position2D, Point> pointByPosition;
    private List<Component> currentlyDrawnPossibleActions;

    private static final Integer BASE_LAYER = 0;
    private static final Integer ACTION_LAYER = 1;
    private static final Integer FIGURE_LAYER = 1;


    /**
     * A graphical user interface for displaying and interacting with a hexagonal gameboard.
     */
    public HexagonalGameboardGUI() {
        this.pane = new JLayeredPane();
        this.window = new JFrame();
        this.positionByShape = new HashMap<>();
        this.pointByPosition = new HashMap<>();
        this.currentlyDrawnPossibleActions = new ArrayList<>();

        createMappings();
        frameInitialization();

        //Event-Handling for the GUI
        EventBroadcaster.register(EventType.FIGURE_SELECTED, (FigureSelectedEvent event) -> {
            if (event.getFigurePosition() == null) {
                hidePossibleActions();
                return;
            }
            showPossibleActions(event.getFigurePosition(), event.getPossibleActions());
        });
        EventBroadcaster.register(EventType.GAMEBOARD_UPDATED, (GameboardUpdatedEvent event) -> drawGameboardState((HexagonalGameboard) event.getGameboard()));
    }


    /**
     * First initialization of the window for the gameboard.
     */
    private void frameInitialization() {
        ImageIcon backgroundImage = new ImageIcon(BACKGROUND_IMAGE);
        int windowWidth = backgroundImage.getIconWidth();
        int windowHeight = backgroundImage.getIconHeight();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        window.setBounds((int) (screenWidth - windowWidth) / 2, (int) (screenHeight - windowHeight) / 2, windowWidth,
            windowHeight);

        window.setContentPane(pane);
        window.getContentPane().addMouseListener(getMouseListener());
        window.setUndecorated(true);
        window.setVisible(true);
    }


    /**
     * Draws the current state of the game. That means all the {@link Figure}s at their current position.
     *
     * @param chessboard The chessboard of the current game.
     */
    public void drawGameboardState(HexagonalGameboard chessboard) {
        ImageIcon backgroundImage = new ImageIcon(BACKGROUND_IMAGE);
        int width = backgroundImage.getIconWidth();
        int height = backgroundImage.getIconHeight();
        pane.removeAll();
        JLabel background = new JLabel(backgroundImage);
        background.setBounds(0, 0, width, height);
        pane.add(background, 0, BASE_LAYER);

        Map<Position2D, Figure> figures = chessboard.getAllFigures();

        for (Map.Entry<Position2D, Figure> entry : figures.entrySet()) {
            Figure figure = entry.getValue();
            Position2D figurePosition = entry.getKey();

            String file = figure.getName() + "-";
            if (figure.getOwner() == HexagonalPlayerType.BLACK) {
                file += "B";
            } else if (figure.getOwner() == HexagonalPlayerType.GRAY) {
                file += "G";
            } else {
                file += "W";
            }
            file += ".png";

            drawGameboardOverlay(figurePosition, file, FIGURE_LAYER);
        }
    }


    /**
     * Shows a preview of possible actions for a figure. Shall be called, after a field (that holds a {@link Figure}) on
     * the gameboard was clicked.
     *
     * @param figurePosition  The position of the clicked figure.
     * @param possibleActions The possible actions for the clicked figure.
     */
    public void showPossibleActions(Position2D figurePosition, List<ChessAction> possibleActions) {
        hidePossibleActions();
        JLabel activeFigureOverlay = drawGameboardOverlay(figurePosition, IMAGE_OVERLAY_ACTION, ACTION_LAYER);
        currentlyDrawnPossibleActions.add(activeFigureOverlay);

        for (ChessAction action : possibleActions) {
            Position2D possibleActionPosition = (Position2D) action.getEndPosition();
            JLabel possibleActionOverlay = drawGameboardOverlay(possibleActionPosition, IMAGE_OVERLAY_MOVE, ACTION_LAYER);
            currentlyDrawnPossibleActions.add(possibleActionOverlay);
        }

    }


    /**
     * Hides the preview of possible actions for a figure.
     */
    public void hidePossibleActions() {

        for (Component comp : currentlyDrawnPossibleActions) {
            pane.remove(comp);
        }
        currentlyDrawnPossibleActions.clear();

        pane.revalidate();
        pane.repaint();
    }


    /**
     * Draws everything that is not the background. That includes overlays for showing possible actions and the figures
     * itself.
     *
     * @param position The {@link Position2D} at which something will be drawn.
     * @param file     The name of the resource file to be drawn. MUST CONTAIN ITS NAME INCLUDING FILE EXTENSION.
     * @param layer    On which layer the resource file shall be drawn. Resource files with a higher value are drawn
     *                 above those with a lower value.
     * @return A {@link JLabel} of the drawn resource.
     */
    private JLabel drawGameboardOverlay(Position2D position, String file, Integer layer) {
        Point center = pointByPosition.get(position);
        ImageIcon image = new ImageIcon(IMAGE_BASE + file);

        JLabel overlay = createJLabel(image, center);
        pane.add(overlay, layer);


        pane.revalidate();
        pane.repaint();

        return overlay;
    }


    /**
     * Puts together a {@link JLabel}. Also responsible for positioning it.
     *
     * @param image          The image for the {@link JLabel}.
     * @param centerPosition Pixel coordinates. The center of the image will be positioned there.
     * @return A {@link JLabel} with a defined position.
     */
    private JLabel createJLabel(ImageIcon image, Point centerPosition) {
        JLabel overlay = new JLabel(image);
        int xCoordinate = (int) centerPosition.getX() - image.getIconWidth() / 2 + 1;
        int yCoordinate = (int) centerPosition.getY() - image.getIconHeight() / 2 + 1;

        overlay.setBounds(xCoordinate, yCoordinate, image.getIconWidth(), image.getIconHeight());
        return overlay;
    }


    /**
     * Returns the MouseListener for the gameboard. The function also has behavior implemented in it, that identifies
     * the clicked {@link Position2D}.
     *
     * @return MouseListener for the gameboard.
     */
    private MouseListener getMouseListener() {

        return new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // Put lines from "mouseReleased(MouseEvent e)" in here instead???
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                for (Map.Entry<HexagonalFieldShape, Position2D> entry : positionByShape.entrySet()) {
                    if (entry.getKey().contains(e.getX(), e.getY())) {
                        Core.LOGGER.debug(() -> "Position2D: " + entry.getValue().getX() + " " + entry.getValue().getY());

                        Position2D pos = Position2D.of(entry.getValue().getX(), entry.getValue().getY());

                        new PositionClickedEvent(pos).trigger();

                        break;
                    }
                }

                // TODO: Send event with parameter null???

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }


    /**
     * Calls all the necessary {@link HexagonalGameboardGUI#mapRow(Point, int, Position2D)} operations for creating the
     * hexagonal gameboard.
     */
    private void createMappings() {
        mapRow(new Point(185, 35), 8, Position2D.of(0, 0));
        mapRow(new Point(154, 89), 9, Position2D.of(1, 0));
        mapRow(new Point(123, 143), 10, Position2D.of(2, 0));
        mapRow(new Point(92, 197), 11, Position2D.of(3, 0));
        mapRow(new Point(61, 251), 12, Position2D.of(4, 0));
        mapRow(new Point(30, 305), 13, Position2D.of(5, 0));
        mapRow(new Point(61, 359), 12, Position2D.of(6, 1));
        mapRow(new Point(92, 413), 11, Position2D.of(7, 2));
        mapRow(new Point(123, 467), 10, Position2D.of(8, 3));
        mapRow(new Point(154, 521), 9, Position2D.of(9, 4));
        mapRow(new Point(185, 575), 8, Position2D.of(10, 5));
        mapRow(new Point(216, 629), 7, Position2D.of(11, 6));
        mapRow(new Point(247, 683), 6, Position2D.of(12, 7));
    }


    /**
     * Maps the {@link HexagonalFieldShape}s of a single row to its appropriate {@link Position2D}s. Also maps the
     * {@link Position2D}s to the center point of the field.
     *
     * @param firstRowFieldCenter The center coordinates (in pixels) of the first polygon that shall be drawn.
     * @param numOfElementsInRow  The number of elements in the row (elements with the same x value).
     * @param firstPos2D          The {@link Position2D} of the first element in the current row.
     */
    private void mapRow(Point firstRowFieldCenter, int numOfElementsInRow, Position2D firstPos2D) {

        for (int i = 0; i < numOfElementsInRow; i++) {
            Point center = new Point((int) firstRowFieldCenter.getX() + (i * 62), (int) firstRowFieldCenter.getY());
            HexagonalFieldShape shape = new HexagonalFieldShape(center);

            positionByShape.put(shape, Position2D.of(firstPos2D.getX(), firstPos2D.getY() + i));
            pointByPosition.put(Position2D.of(firstPos2D.getX(), firstPos2D.getY() + i), center);
        }

    }
}