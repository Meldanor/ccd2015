package jchess.game.movement;


import jchess.game.Figure;
import jchess.game.Gameboard;
import jchess.game.Position2D;

import java.util.List;

/**
 * Provides a list of possible {@link ChessAction} for a figure in a gameboard. Also has some default methods for
 * basic chess actions.
 *
 * @since 14.11.2015
 */
public interface MovementPattern<P extends Position2D, T extends Gameboard<P>> {

    /**
     * Provides a list of possible action the figure can perform (but needn't).
     *
     * @param figure     The figure it self.
     * @param chessboard The chessboard in its current state.
     * @return A non-null list (can be empty) containing possible actions. An empty list indicates, that this figure
     * cannot do anything at the moment (like the rook at the start).
     */
    List<ChessAction> getPossibleActions(Figure figure, T chessboard);

    /**
     * Moves the figure to a given position. The most used function.
     *
     * @param figure      The figure itself.
     * @param endPosition The end position of the figure.
     * @return A chess action.
     */
    default ChessAction<P> moveTo(Figure figure, P endPosition) {
        return new ChessAction<>(ChessAction.ChessActionType.MOVE, figure, endPosition);
    }

    /**
     * Captures (defeat) an enemy at a given position.
     *
     * @param figure        The figure itself.
     * @param enemy         The enemy to capture.
     * @param enemyPosition The enemies position
     * @return A chess action.
     */
    default ChessAction<P> captureEnemy(Figure figure, Figure enemy, P enemyPosition) {
        return new ChessAction<>(ChessAction.ChessActionType.CAPTURE, figure, enemyPosition);
    }
}
