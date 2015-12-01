package jchess.game;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A gameboard for chess based on a hexagonal field using axial coordinates.
 * <p>
 * The base (0,0) is at the top left of the game. The x-axis is from left to right and the y-axis from
 *
 * @since 03.11.2015
 */
public class HexagonalGameboard implements Gameboard<Position2D> {

    private static Figure EMPTY_SPACE = new FigureBuilder<>("", null, () -> 0).build();

    private Map<Position2D, Figure> chessBoard;
    private Map<Figure, Position2D> figurePosition;

    public HexagonalGameboard(Map<Position2D, Position2D> rowsIntervals, Map<Position2D, Figure> figures) {
        this.chessBoard = createChessBoard(rowsIntervals, figures);

        this.figurePosition = new IdentityHashMap<>();

        for (Map.Entry<Position2D, Figure> entry : figures.entrySet()) {
            this.figurePosition.put(entry.getValue(), entry.getKey());
        }
    }

    private Map<Position2D, Figure> createChessBoard(Map<Position2D, Position2D> rowsIntervals,
                                                     Map<Position2D, Figure> figures) {
        Map<Position2D, Figure> map = new HashMap<>();
        for (Map.Entry<Position2D, Position2D> entry : rowsIntervals.entrySet()) {
            if (entry.getValue().getX() != entry.getKey().getX()) {
                throw new IllegalArgumentException("The X values must be equal for each pair!"
                    + entry.getKey() + " ; " + entry.getValue());
            }
            int x = entry.getKey().getX();

            for (int y = entry.getKey().getY(); y <= entry.getValue().getY(); y++) {
                Position2D pos = Position2D.of(x, y);
                Figure figure = figures.getOrDefault(pos, EMPTY_SPACE);
                map.put(pos, figure);
            }
        }

        return map;
    }

    @Override
    public Optional<Figure> getFigure(Position2D position) {
        Figure figure = chessBoard.get(position);
        if (figure == null) {
            throw new ArrayIndexOutOfBoundsException("The position" + position + "is outside the field!");
        }
        return figure != EMPTY_SPACE ? Optional.of(figure) : Optional.empty();
    }

    @Override
    public boolean isInField(Position2D position) {
        return chessBoard.containsKey(position);
    }

    // TODO: Think about necessary changes for the Castling
    @Override
    public Optional<Figure> moveTo(Position2D start, Position2D end) {
        if (!isInField(start))
            throw new MovementException("Start position " + start + " is not in the field");
        if (!isInField(end))
            throw new MovementException("End position " + end + " is not in the field");

        Optional<Figure> startFigure = getFigure(start);
        if (!startFigure.isPresent()) {
            throw new MovementException("There is no figure at the start position " + start + " to move");
        }

        Optional<Figure> endPositionFigure = getFigure(end);

        this.chessBoard.put(end, startFigure.get());
        this.figurePosition.put(startFigure.get(), end);

        this.chessBoard.put(start, EMPTY_SPACE);
        if (endPositionFigure.isPresent())
            this.figurePosition.remove(endPositionFigure.get());

        return endPositionFigure;
    }

    @Override
    public Position2D getPositionOf(Figure figure) {
        return figurePosition.get(figure);
    }

    @Override
    public List<Position2D> getNeighbors(Position2D position) {
        int x = position.getX();
        int y = position.getY();

        return Stream.of(
            Position2D.of(x - 1, y), // NE
            Position2D.of(x, y + 1), // E
            Position2D.of(x + 1, y + 1), // SE
            Position2D.of(x + 1, y), // SW
            Position2D.of(x, y - 1), //W
            Position2D.of(x - 1, y - 1) // NW
        ).filter(this::isInField)
            .collect(Collectors.toList());
    }

    @Override
    public Map<Position2D, Figure> getAllFigures() {
        return chessBoard.entrySet().stream()
            .filter(e -> e.getValue() != EMPTY_SPACE)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


}
