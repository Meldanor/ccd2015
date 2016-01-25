package jchess.event;

/**
 * The type of the event. Useful to register to an event.
 * <p>
 * There must be an 1:1 relation between an Event and its EventType. If there are more than one Event using the same
 * EventType, this can result in some errors with the {@link EventBroadcaster}
 *
 * @since 18.11.2015
 */
public enum EventType {

    FIGURE_MOVED,
    FIGURE_CAPTURED,
    FIGURE_SELECTED,
    ACTION_SELECTED,
    POSITION_CLICKED,
    GAMEBOARD_UPDATED
}
