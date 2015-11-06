package jchess.game;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A gameboard for chess based on a hexagonal field using axial coordinates.
 *
 * @since 03.11.2015
 */
public class HexagonalGameboard implements Gameboard<Position2D> {

    private Map<Position2D, Figure> chessBoard;
    private Map<Figure, Position2D> figurePosition;

    public HexagonalGameboard(int horizontal, int diagonal, Map<Position2D, Figure> figures) {
        this.chessBoard = new HashMap<>(figures);
        for (int x = 0; x < horizontal; x++) {
            for (int y = 0; y < diagonal; y++) {
                Position2D pos = Position2D.of(x, y);
                this.chessBoard.putIfAbsent(pos, EmptySpace.get());
            }
        }
        this.figurePosition = new IdentityHashMap<>();

        for (Map.Entry<Position2D, Figure> entry : figures.entrySet()) {
            this.figurePosition.put(entry.getValue(), entry.getKey());
        }
    }

    @Override
    public Optional<Figure> getFigure(Position2D position) {
        Figure figure = chessBoard.get(position);
        if (figure == null) {
            throw new ArrayIndexOutOfBoundsException("The position" + position + "is outside the field!");
        }
        return figure != EmptySpace.get() ? Optional.of(figure) : Optional.empty();
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

        this.chessBoard.put(start, EmptySpace.get());
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
            Position2D.of(x + 1, y),
            Position2D.of(x + 1, y - 1),
            Position2D.of(x, y - 1),
            Position2D.of(x - 1, y),
            Position2D.of(x - 1, y + 1),
            Position2D.of(x, y + 1)
        ).filter(this::isInField)
            .collect(Collectors.toList());
    }

    @Override
    public Map<Position2D, Figure> getAllFigures() {
        return chessBoard.entrySet().stream()
            .filter(e -> e.getValue() != EmptySpace.get())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


}
