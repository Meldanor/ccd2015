package jchess.game.movement;

import jchess.game.Figure;
import jchess.game.Gameboard;
import jchess.game.Position2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Provides a list of possible {@link ChessAction} for a figure, that uses the movement patterns of a bishop figure. In
 * the base version of the Three-Person-Chess-Game, this movement pattern can not only be used by the bishop, but also
 * as part of the movement patterns of the king and queen figures.
 *
 * @since 17.11.2015.
 */

public class BishopMovementPattern implements MovementPattern {

    // These arrays contain the changes of the x- and y-coordinate when performing a diagonal move along the specified
    // direction.
    private static final int[] NORTH = {-2, -1};
    private static final int[] NORTH_EAST = {-1, 1};
    private static final int[] SOUTH_EAST = {1, 2};
    private static final int[] SOUTH = {2, 1};
    private static final int[] SOUTH_WEST = {1, -1};
    private static final int[] NORTH_WEST = {-1, -2};

    private int rangeLimitation;


    /**
     * Constructor without arguments. The range that a figure can move in a single direction will NOT be limited by a
     * fixed value.
     */
    public BishopMovementPattern() {
        this.rangeLimitation = -1;
    }


    /**
     * Constructor with an argument to limit the range a figure can move.
     *
     * @param rangeLimitation The maximum number of fields a figure will be able to move in a single direction (e.g.
     *                        for king = 1). Only positive values are allowed.
     * @throws IllegalArgumentException if {@link #rangeLimitation} is a negative number.
     */
    public BishopMovementPattern(int rangeLimitation) {
        if (rangeLimitation < 0) {
            throw new IllegalArgumentException("Move range can not be negative");
        }
        this.rangeLimitation = rangeLimitation;
    }


    @Override
    public List<ChessAction> getPossibleActions(Figure figure, Gameboard chessboard) {
        List<ChessAction> possibleActions = new ArrayList<>();

        possibleActions.addAll(this.checkDirection(figure, chessboard, NORTH));
        possibleActions.addAll(this.checkDirection(figure, chessboard, NORTH_EAST));
        possibleActions.addAll(this.checkDirection(figure, chessboard, SOUTH_EAST));
        possibleActions.addAll(this.checkDirection(figure, chessboard, SOUTH));
        possibleActions.addAll(this.checkDirection(figure, chessboard, SOUTH_WEST));
        possibleActions.addAll(this.checkDirection(figure, chessboard, NORTH_WEST));

        return possibleActions;
    }


    /**
     * Checks all possible {@link ChessAction} for the figure along a single direction.
     *
     * @param chessboard The chessboard in its current state.
     * @param figure     The figure.
     * @param direction  An array containing the changes of the x- and y-coordinate along a specific direction.
     * @return A non-null list (can be empty) containing possible actions along the specified direction. An empty list
     * indicates, that the figure cannot do anything along this direction.
     */
    private List<ChessAction> checkDirection(Figure figure, Gameboard chessboard, int[] direction) {
        List<ChessAction> possibleActionsInDirection = new ArrayList<>();

        int changeInX = direction[0];
        int changeInY = direction[1];

        Position2D latestValidPosition = chessboard.getPositionOf(figure);
        int nextX = latestValidPosition.getX() + changeInX;
        int nextY = latestValidPosition.getY() + changeInY;
        Position2D nextPosition = Position2D.of(nextX, nextY);

        int remainingMoves = this.rangeLimitation;
        while (chessboard.isInField(nextPosition) && remainingMoves != 0) {
            remainingMoves--;

            List<Position2D> identicalNeighbors = chessboard.getNeighbors(latestValidPosition);
            identicalNeighbors.retainAll(chessboard.getNeighbors(nextPosition));

            Optional<Figure> identicalNeighbor1 = chessboard.getFigure(identicalNeighbors.get(0));
            Optional<Figure> identicalNeighbor2 = chessboard.getFigure(identicalNeighbors.get(1));

            // The figure can not perform a diagonal move to the next position in the specified direction, if the way to
            // it is blocked by two other figures.
            if (identicalNeighbor1.isPresent() == false || identicalNeighbor2.isPresent() == false) {
                Optional<Figure> nextPositionFigure = chessboard.getFigure(nextPosition);

                if (nextPositionFigure.isPresent() == false) {
                    possibleActionsInDirection.add(this.moveTo(figure, nextPosition));
                } else {
                    Figure target = nextPositionFigure.get();
                    if (target.isOppositesFigure(figure)) {
                        possibleActionsInDirection.add(this.captureEnemy(figure, target, nextPosition));
                    }

                    // Leave the while loop, because the figure can not jump over an other figure that is directly
                    // in its way.
                    break;
                }

                latestValidPosition = nextPosition;
                nextPosition = Position2D.of(latestValidPosition.getX() + changeInX, latestValidPosition.getY()
                        + changeInY);

            } else {
                break;
            }
        }

        return possibleActionsInDirection;
    }
}
