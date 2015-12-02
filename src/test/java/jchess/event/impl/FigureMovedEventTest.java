package jchess.event.impl;

import jchess.event.Event;
import jchess.event.EventType;
import jchess.game.FigureMock;
import jchess.game.Figure;
import jchess.game.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * @since 18.11.2015
 */
public class FigureMovedEventTest {

    @Test
    public void testCreation() throws Exception {

        Figure figureMock = FigureMock.create();
        Position startPos = mock(Position.class);
        Position endPos = mock(Position.class);

        Event event = new FigureMovedEvent(figureMock, startPos, endPos);
        assertEquals(EventType.FIGURE_MOVED, event.getEventType());
    }
}