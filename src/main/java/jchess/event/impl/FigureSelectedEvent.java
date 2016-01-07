package jchess.event.impl;

import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import jchess.event.Event;
import jchess.event.EventType;
import jchess.game.Figure;

/**
 * When a Figure is selected via the UI this event is thrown. It indicates the selected Figure.
 *
 * @since 14.12.2015.
 */
public class FigureSelectedEvent extends Event {

    private final Figure figure;

    /**
     * Create the event.
     *
     * @param figure The selected figure.
     */
    public FigureSelectedEvent(Figure figure) {
        super(EventType.FIGURE_SELECTED);

        this.figure = figure;
    }

    /**
     *
     * @return The selected figure.
     */
    public Figure getFigure(){return figure;}

    @Override
    public String toString() {
        return "FigureSelectedEvent{figure=" + figure + "} " + super.toString();
    }
}
