package jchess.event.impl;

import jchess.event.Event;
import jchess.event.EventType;
import jchess.game.movement.ChessAction;

/**
 * When an action is selected to be executed via the UI this event is thrown. It indicates the selected action.
 *
 * @since 14.12.2015.
 */
public class ActionSelectedEvent extends Event {

    private final ChessAction action;

    protected ActionSelectedEvent(ChessAction action) {
        super(EventType.ACTION_SELECTED);

        this.action = action;
    }

    /**
     *
     * @return The selected Action.
     */
    public ChessAction getAction() { return action; }
}
