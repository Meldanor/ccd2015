package jchess.event.impl;

import jchess.event.Event;
import jchess.event.EventType;
import jchess.game.Figure;
import jchess.game.Position;

/**
 * After a figure has moved, this event is thrown. It indicates its start and endposition and the figure itself.
 *
 * @since 18.11.2015
 */
public class FigureMovedEvent extends Event {

    private final Figure figure;
    private final Position start;
    private final Position end;

    /**
     * Create the event.
     *
     * @param figure The figure which was moved.
     * @param start  The start position of the figure.
     * @param end    The end position of the figure.
     */
    public FigureMovedEvent(Figure figure, Position start, Position end) {
        super(EventType.FIGURE_MOVED);

        this.figure = figure;
        this.start = start;
        this.end = end;
    }

    /**
     * @return The figure which was moved.
     */
    public Figure getFigure() {
        return figure;
    }

    /**
     * @return The start position of the movement.
     */
    public Position getStart() {
        return start;
    }

    /**
     * @return The end position of the movement.
     */
    public Position getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "FigureMovedEvent{" +
            "figure=" + figure +
            ", start=" + start +
            ", end=" + end +
            "} " + super.toString();
    }
}
