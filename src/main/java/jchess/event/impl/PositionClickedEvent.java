package jchess.event.impl;

import jchess.event.Event;
import jchess.event.EventType;
import jchess.game.Position2D;

/**
 * When a position is clicked on in the UI this event is thrown. It indicates the clicked position.
 *
 * @since 18.01.2016.
 */
public class PositionClickedEvent extends Event {

    private final Position2D position;

    /**
     * Create the event.
     *
     * @param pos The position where the click occurred.
     */
    public PositionClickedEvent(Position2D pos) {
        super(EventType.POSITION_CLICKED);

        position = pos;
    }

    /**
     *
     * @return The position where the click occurred
     */
    public Position2D getPosition() {return position;}


    public String toString() {
        return "PositionClickedEvent{position=" + position + "} " + super.toString();
    }
}
