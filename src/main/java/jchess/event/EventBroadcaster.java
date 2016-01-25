package jchess.event;

import jchess.Core;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * This singleton provides methods to register for events via callbacks. If a event is triggered, this class will
 * broadcast the event to any registered observer.
 * <p>
 * This is thread-safe.
 *
 * @since 20.11.2015
 */
public class EventBroadcaster {

    private static final EventBroadcaster INSTANCE = new EventBroadcaster();

    private Map<EventType, List<Consumer<Event>>> observers;

    private EventBroadcaster() {
        this.observers = new EnumMap<>(EventType.class);
        for (EventType eventType : EventType.values()) {
            this.observers.put(eventType, new CopyOnWriteArrayList<>());
        }
    }

    /**
     * Register for a certain event type to be informed when the event is triggered.
     *
     * @param type   The type of the event. Only events with this type will trigger your action.
     * @param action The action which will be executed when an event is triggered.
     */
    @SuppressWarnings("unchecked")
    public static void register(EventType type, Consumer<? extends Event> action) {
        Core.LOGGER.debug(() -> "Consumer registered for event type " + type);
        INSTANCE.observers.get(type).add((Consumer<Event>) action);
    }

    /**
     * Trigger the event and inform all observer, which has registered for it.
     *
     * @param event The event containing the information of the event.
     */
    public static void triggerEvent(Event event) {
        List<Consumer<Event>> consumers = INSTANCE.observers.get(event.getEventType());
        Core.LOGGER.debug(() -> "Trigger event " + event.getEventType() + " for "
            + consumers.size() + " observer. Event=" + event);
        for (Consumer<Event> action : consumers) {
            action.accept(event);
        }
    }


}
