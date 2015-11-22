package jchess.game.movement;

import jchess.game.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Provides a list of possible {@link ChessAction} for a pawn figure
 *
 * @since 21.11.2015.
 */
public class PawnMovement implements MovementPattern {

    private static final int[] WHITE_MOVE1 = {1, 0};
    private static final int[] WHITE_MOVE2 = {1, 1};
    private static final int[] WHITE_CAPTURE1 = {1, -1};
    private static final int[] WHITE_CAPTURE2 = {2, 1};
    private static final int[] WHITE_CAPTURE3 = {1, 2};

    private static final int[] BLACK_MOVE1 = {-1, 0};
    private static final int[] BLACK_MOVE2 = {0, 1};
    private static final int[] BLACK_CAPTURE1 = {-2, -1};
    private static final int[] BLACK_CAPTURE2 = {-1, 1};
    private static final int[] BLACK_CAPTURE3 = {1, 2};

    private static final int[] GRAY_MOVE1 = {-1, -1};
    private static final int[] GRAY_MOVE2 = {0, -1};
    private static final int[] GRAY_CAPTURE1 = {-2, -1};
    private static final int[] GRAY_CAPTURE2 = {-1, -2};
    private static final int[] GRAY_CAPTURE3 = {1, -1};


    @Override
    public List<ChessAction> getPossibleActions(Figure figure, Gameboard chessboard) {
        List<ChessAction> possibleActions = new ArrayList<>();

        boolean wasMovedBefore = this.wasMovedBefore(chessboard.getPositionOf(figure), figure.getOwner());

        if (figure.getOwner() == HexagonalPlayerType.WHITE) {
            possibleActions.addAll(this.getMoveActions(figure, chessboard, WHITE_MOVE1, wasMovedBefore));
            possibleActions.addAll(this.getMoveActions(figure, chessboard, WHITE_MOVE2, wasMovedBefore));
            possibleActions.addAll(this.getCaptureActions(figure, chessboard, WHITE_CAPTURE1));
            possibleActions.addAll(this.getCaptureActions(figure, chessboard, WHITE_CAPTURE2));
            possibleActions.addAll(this.getCaptureActions(figure, chessboard, WHITE_CAPTURE3));
        } else if (figure.getOwner() == HexagonalPlayerType.BLACK) {
            possibleActions.addAll(this.getMoveActions(figure, chessboard, BLACK_MOVE1, wasMovedBefore));
            possibleActions.addAll(this.getMoveActions(figure, chessboard, BLACK_MOVE2, wasMovedBefore));
            possibleActions.addAll(this.getCaptureActions(figure, chessboard, BLACK_CAPTURE1));
            possibleActions.addAll(this.getCaptureActions(figure, chessboard, BLACK_CAPTURE2));
            possibleActions.addAll(this.getCaptureActions(figure, chessboard, BLACK_CAPTURE3));
        } else if (figure.getOwner() == HexagonalPlayerType.GRAY) {
            possibleActions.addAll(this.getMoveActions(figure, chessboard, GRAY_MOVE1, wasMovedBefore));
            possibleActions.addAll(this.getMoveActions(figure, chessboard, GRAY_MOVE2, wasMovedBefore));
            possibleActions.addAll(this.getCaptureActions(figure, chessboard, GRAY_CAPTURE1));
            possibleActions.addAll(this.getCaptureActions(figure, chessboard, GRAY_CAPTURE2));
            possibleActions.addAll(this.getCaptureActions(figure, chessboard, GRAY_CAPTURE3));
        }

        return possibleActions;
    }


    /**
     * Checks where a pawn figure can be moved to along a specific direction.
     *
     * @param pawn           The respective pawn figure.
     * @param chessboard     The chessboard in its current state.
     * @param move           An array containing the changes of the x- and y-coordinate when moving the pawn figure one
     *                       position forward along a specific direction.
     * @param wasMovedBefore Whether or not the pawn figure was already moved before.
     * @return A non-null list (can be empty) containing possible move-actions along the specified move-direction. An
     * empty list indicates, that the pawn cannot be moved along the move-direction.
     */
    private List<ChessAction> getMoveActions(Figure pawn, Gameboard chessboard, int[] move, boolean wasMovedBefore) {
        List<ChessAction> possibleMoveActions = new ArrayList<>();

        int changeInX = move[0];
        int changeInY = move[1];

        Position2D pawnPosition = chessboard.getPositionOf(pawn);
        int nextX = pawnPosition.getX() + changeInX;
        int nextY = pawnPosition.getY() + changeInY;
        Position2D nextPosition = Position2D.of(nextX, nextY);

        if (chessboard.getFigure(nextPosition).isPresent() == false) {
            possibleMoveActions.add(this.moveTo(pawn, nextPosition));

            if (wasMovedBefore == false) {
                nextX = pawnPosition.getX() + 2 * changeInX;
                nextY = pawnPosition.getY() + 2 * changeInY;
                nextPosition = Position2D.of(nextX, nextY);

                if (chessboard.getFigure(nextPosition).isPresent() == false) {
                    possibleMoveActions.add(this.moveTo(pawn, nextPosition));
                }
            }
        }

        return possibleMoveActions;
    }


    /**
     * Checks whether the pawn is able to capture an enemy figure at a specific position.
     *
     * @param pawn       The respective pawn figure.
     * @param chessboard The chessboard in its current state.
     * @param move       An array containing the changes of the x- and y-coordinate to reach a specific position, at
     *                   which the pawn would be allowed to capture an enemy figure.
     * @return A non-null list (can be empty) containing a possible capture-action. An empty list indicates, that the
     * pawn cannot capture an enemy figure.
     */
    private List<ChessAction> getCaptureActions(Figure pawn, Gameboard chessboard, int[] move) {
        List<ChessAction> possibleCaptureActions = new ArrayList<>();

        int changeInX = move[0];
        int changeInY = move[1];

        Position2D pawnPosition = chessboard.getPositionOf(pawn);
        int nextX = pawnPosition.getX() + changeInX;
        int nextY = pawnPosition.getY() + changeInY;
        Position2D nextPosition = Position2D.of(nextX, nextY);

        if (chessboard.isInField(nextPosition)) {
            Optional<Figure> nextPositionFigure = chessboard.getFigure(nextPosition);

            if (nextPositionFigure.isPresent() == true) {
                List<Position2D> identicalNeighbors = chessboard.getNeighbors(pawnPosition);
                identicalNeighbors.retainAll(chessboard.getNeighbors(nextPosition));

                Optional<Figure> identicalNeighbor1 = chessboard.getFigure(identicalNeighbors.get(0));
                Optional<Figure> identicalNeighbor2 = chessboard.getFigure(identicalNeighbors.get(1));

                if (identicalNeighbor1.isPresent() == false || identicalNeighbor2.isPresent() == false) {
                    Figure target = nextPositionFigure.get();

                    if (target.isOppositesFigure(pawn)) {
                        possibleCaptureActions.add(this.captureEnemy(pawn, target, nextPosition));
                    }
                }
            }
        }

        return possibleCaptureActions;
    }


    /**
     * Returns true if the pawn at a specified position was not moved before.
     *
     * @param pawnPosition The position of the respective pawn figure.
     * @param owner        The owner (player) of the respective pawn figure.
     * @return true if the pawn at the specified position was moved before.
     */
    private boolean wasMovedBefore(Position2D pawnPosition, PlayerType owner) {
        if (owner == HexagonalPlayerType.WHITE) {
            if (pawnPosition.getX() == 1) {
                return false;
            }
        } else if (owner == HexagonalPlayerType.BLACK) {
            for (int x = 4, y = 0; y <= 8; x++, y++) {
                if (pawnPosition.getX() == x && pawnPosition.getY() == y) {
                    return false;
                }
            }
        } else if (owner == HexagonalPlayerType.GRAY) {
            for (int x = 4, y = 1; x <= 12; x++) {
                if (pawnPosition.getX() == x && pawnPosition.getY() == y) {
                    return false;
                }
            }
        }

        return true;
    }

}
