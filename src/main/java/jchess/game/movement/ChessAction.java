package jchess.game.movement;

import jchess.game.Figure;
import jchess.game.Position;

/**
 * A single, possible action of a figure like moving or capturing an enemy.
 *
 * @since 14.11.2015
 */
public class ChessAction<P extends Position> {

    private final ChessActionType actionType;
    private final P endPosition;
    private final Figure origin;

    /**
     * Encapsulate the values of an action.
     *
     * @param actionType  The type of action.
     * @param origin      The figure performing the action.
     * @param endPosition The end position of the figure after the action.
     */
    public ChessAction(ChessActionType actionType, Figure origin, P endPosition) {
        this.actionType = actionType;
        this.origin = origin;
        this.endPosition = endPosition;
    }

    /**
     * @return What is the action?
     */
    public ChessActionType getActionType() {
        return actionType;
    }

    /**
     * @return The figure performing the action.
     */
    public Figure getOrigin() {
        return origin;
    }

    /**
     * @return The end position of the figure after the action.
     */
    public P getEndPosition() {
        return endPosition;
    }

    /**
     * A description of the chess action.
     */
    public enum ChessActionType {
        /**
         * Simple move without any enemy contact.}
         */
        MOVE,
        /**
         * Move with capturing an enemy
         */
        CAPTURE,
        /**
         * The king and a rook swaps their position once per game.
         */
        CASTLING,
        /**
         * Some strange move of pawns.
         */
        EN_PASSE,
        /**
         * Yeah, little pawn is growing up to some big mother *****.
         */
        EVOLVE;
    }
}
