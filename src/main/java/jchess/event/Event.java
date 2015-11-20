package jchess.event;

/**
 * The base class for events. Identified by the event type.
 * <p>
 * There must be an 1:1 relation between an Event and its EventType. If there are more than one Event using the same
 * EventType, this can result in some errors with the {@link EventBroadcaster}.
 *
 * @since 18.11.2015
 */
public abstract class Event {

    private final EventType eventType;

    protected Event(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }

    /**
     * Inform all registered observer about this event.
     */
    public void trigger() {
        EventBroadcaster.triggerEvent(this);
    }

    @Override
    public String toString() {
        return "Event{" +
            "eventType=" + eventType +
            '}';
    }
}
