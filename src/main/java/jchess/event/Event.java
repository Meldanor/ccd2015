package jchess.event;

/**
 * The base class for events. Identified by the event type.
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

    @Override
    public String toString() {
        return "Event{" +
            "eventType=" + eventType +
            '}';
    }
}
