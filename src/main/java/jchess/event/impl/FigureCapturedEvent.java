package jchess.event.impl;

import jchess.event.Event;
import jchess.event.EventType;
import jchess.game.Figure;
import jchess.game.Position;

/**
 * An event thrown when one figure capture another figure.
 *
 * @since 20.11.2015
 */
public class FigureCapturedEvent extends Event {

    private final Figure ownFigure;
    private final Position oldPosition;
    private final Figure capturedFigure;
    private final Position capturedPosition;

    /**
     * Create the event
     *
     * @param ownFigure      The figure which has captured another figure.
     * @param oldPosition    The position of the  figure before the capture
     * @param capturedFigure The captured figure (the enemy).
     * @param capturedPosition    The position of the enemy.
     */
    public FigureCapturedEvent(Figure ownFigure, Position oldPosition, Figure capturedFigure, Position capturedPosition) {
        super(EventType.FIGURE_CAPTURED);
        this.ownFigure = ownFigure;
        this.oldPosition = oldPosition;
        this.capturedFigure = capturedFigure;
        this.capturedPosition = capturedPosition;
    }


    /**
     * @return The figure which has captured another figure.
     */
    public Figure getOwnFigure() {
        return ownFigure;
    }

    /**
     * @return The position of the figure before the capture.
     */
    public Position getOldPosition() {
        return oldPosition;
    }

    /**
     * @return The captured figure (the enemy).
     */
    public Figure getCapturedFigure() {
        return capturedFigure;
    }

    /**
     * @return THe position of the enemy (which was captured).
     */
    public Position getCapturedPosition() {
        return capturedPosition;
    }

    @Override
    public String toString() {
        return "FigureCapturedEvent{" +
            "ownFigure=" + ownFigure +
            ", oldPosition=" + oldPosition +
            ", capturedFigure=" + capturedFigure +
            ", capturedPosition=" + capturedPosition +
            "} " + super.toString();
    }
}
