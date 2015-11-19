package jchess.game.movement;

import jchess.game.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Provides a list of possible {@link ChessAction} for a bishop figure
 *
 * @since 17.11.2015.
 */
public class BishopMovement implements MovementPattern {

    private static int[] NORTH = {-2, -1};
    private static int[] NORTH_EAST = {-1, 1};
    private static int[] SOUTH_EAST = {1, 2};
    private static int[] SOUTH = {2, 1};
    private static int[] SOUTH_WEST = {1, -1};
    private static int[] NORTH_WEST = {-1, -2};


    @Override
    public List<ChessAction> getPossibleActions(Figure figure, Gameboard chessboard) {
        List<ChessAction> possibleActions = new ArrayList<>();

        possibleActions.addAll(checkDirection(figure, chessboard, NORTH));
        possibleActions.addAll(checkDirection(figure, chessboard, NORTH_EAST));
        possibleActions.addAll(checkDirection(figure, chessboard, SOUTH_EAST));
        possibleActions.addAll(checkDirection(figure, chessboard, SOUTH));
        possibleActions.addAll(checkDirection(figure, chessboard, SOUTH_WEST));
        possibleActions.addAll(checkDirection(figure, chessboard, NORTH_WEST));

        return possibleActions;
    }


    /**
     * Checks all possible actions for the bishop along a single direction.
     *
     * @param chessboard The chessboard in its current state.
     * @param bishop     The respective bishop figure.
     * @param direction  An array containing the changes of the x- and y-coordinate along a specific direction.
     * @return A non-null list (can be empty) containing possible actions along the specified direction. An empty list
     * indicates, that the bishop cannot do anything along this direction.
     */
    private List<ChessAction> checkDirection(Figure bishop, Gameboard chessboard, int[] direction) {
        List<ChessAction> possibleActionsInDirection = new ArrayList<>();

        int changeInX = direction[0];
        int changeInY = direction[1];

        Position2D latestValidPosition = chessboard.getPositionOf(bishop);
        int nextX = latestValidPosition.getX() + changeInX;
        int nextY = latestValidPosition.getY() + changeInY;
        Position2D nextPosition = Position2D.of(nextX, nextY);

        while (chessboard.isInField(nextPosition)) {
            List<Position2D> identicalNeighbors = chessboard.getNeighbors(latestValidPosition);
            identicalNeighbors.retainAll(chessboard.getNeighbors(nextPosition));

            Optional<Figure> neighborOptional1 = chessboard.getFigure(identicalNeighbors.get(0));
            Optional<Figure> neighborOptional2 = chessboard.getFigure(identicalNeighbors.get(1));

            if (neighborOptional1.isPresent() == false || neighborOptional2.isPresent() == false) {
                break;
            }

            Figure identicalNeighbor1 = neighborOptional1.get();
            Figure identicalNeighbor2 = neighborOptional2.get();

            if (identicalNeighbor1.getType() == FigureType.FREE || identicalNeighbor2.getType() == FigureType.FREE) {
                Optional<Figure> nextPositionOptional = chessboard.getFigure(nextPosition);

                if (nextPositionOptional.isPresent() == false) {
                    break;
                }

                Figure target = nextPositionOptional.get();

                if (target.getType() == FigureType.FREE) {
                    possibleActionsInDirection.add(this.moveTo(bishop, nextPosition));
                } else if (target.isOppositesFigure(bishop)) {
                    possibleActionsInDirection.add(this.captureEnemy(bishop, target, nextPosition));
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
