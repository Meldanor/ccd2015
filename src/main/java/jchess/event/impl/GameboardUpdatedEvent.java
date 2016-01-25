package jchess.event.impl;

import jchess.event.Event;
import jchess.event.EventType;
import jchess.game.Gameboard;

/**
 * When a change to the gameboard was made this event is thrown (so the UI can draw the updated gameboard). It indicates the updated gameboard.
 *
 * @since 25.01.2016.
 */
public class GameboardUpdatedEvent extends Event {

    private Gameboard gameboard;

    /**
     * Creates the event.
     *
     * @param gameboard The updated gameboard with it's contents
     */
    public GameboardUpdatedEvent(Gameboard gameboard) {
        super(EventType.GAMEBOARD_UPDATED);
        this.gameboard = gameboard;
    }

    /**
     *
     * @return The updated gameboard.
     */
    public Gameboard getGameboard() {
        return gameboard;
    }
}
