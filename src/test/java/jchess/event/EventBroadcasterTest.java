package jchess.event;

import jchess.event.impl.FigureCapturedEvent;
import jchess.event.impl.FigureMovedEvent;
import jchess.game.Figure;
import jchess.game.Position;
import org.junit.Before;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * @since 20.11.2015
 */
public class EventBroadcasterTest {

    // This is necessary, because we will static register the events and we need to reset a singleton.
    // Only for testing. Any use in the product code will result in eternal punishment.
    @Before
    public void before() throws Exception {
        // You want to test a singleton with a static final INSTANCE?
        // Use this black magic voodoo stuff. Pay with your own soul.

        // Construct a new instance. Of the singleton. Using the private constructor.
        Constructor<EventBroadcaster> constructor = EventBroadcaster.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        EventBroadcaster manager = constructor.newInstance();

        // Access the deep dark using the Unsafe
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        // Get the static final offset in your memory
        Field instanceField = EventBroadcaster.class.getDeclaredField("INSTANCE");
        long offset = unsafe.staticFieldOffset(instanceField);
        // Get the memory offset of your static class. What ever this is.
        Object object = unsafe.staticFieldBase(instanceField);
        // Simple: We update the static final variable of the singleton with our newly created instance.
        unsafe.putObject(object, offset, manager);
    }

    @Test
    public void testEventTrigger() throws Exception {

        // Data mocks
        Figure figureMock = mock(Figure.class);
        Position startPos = mock(Position.class);
        Position endPos = mock(Position.class);

        // Because variables for lambdas must be final, this triggered variable is wrapped.
        AtomicBoolean triggered = new AtomicBoolean(false);

        // Register for the event. This callback should be executed AFTER the newFigureMovedEvent().trigger()
        EventBroadcaster.register(EventType.FIGURE_MOVED, (FigureMovedEvent e) -> {

            triggered.set(true);
            assertEquals(EventType.FIGURE_MOVED, e.getEventType());
            assertSame(figureMock, e.getFigure());
            assertSame(startPos, e.getStart());
            assertSame(endPos, e.getEnd());
        });

        // Trigger the event and jump into the callback above
        new FigureMovedEvent(figureMock, startPos, endPos).trigger();
        assertTrue(triggered.get());

    }

    @Test
    public void testNoEventTrigger() throws Exception {

        // Data mocks
        Figure figureMock = mock(Figure.class);
        Position pos = mock(Position.class);

        // Register for another event, so this callback should NOT get invoked.
        EventBroadcaster.register(EventType.FIGURE_MOVED, (FigureMovedEvent e) -> fail());

        // Trigger the event
        new FigureCapturedEvent(figureMock, pos, figureMock, pos).trigger();
    }
}