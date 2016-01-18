package jchess.event.impl;

import jchess.event.Event;
import jchess.event.EventType;
import jchess.game.Figure;
import jchess.game.movement.ChessAction;

import java.util.List;

/**
 * When a Figure is selected via the UI this event is thrown. It indicates the selected figure and it's possible moves.
 *
 * @since 14.12.2015.
 */
public class FigureSelectedEvent extends Event {

    private final Figure figure;
    private final List<ChessAction> possibleActions;

    /**
     * Create the event.
     *
     * @param figure The selected figure.
     * @param possibleActions The possible actions of the selected figure.
     */
    public FigureSelectedEvent(Figure figure, List<ChessAction> possibleActions) {
        super(EventType.FIGURE_SELECTED);

        this.figure = figure;
        this.possibleActions = possibleActions;
    }

    /**
     *
     * @return The selected figure.
     */
    public Figure getFigure(){return figure;}

    /**
     *
     * @return The possible actions for the selected figure.
     */
    public List<ChessAction> getPossibleActions(){return possibleActions;}

    @Override
    public String toString() {
        return "FigureSelectedEvent{figure=" + figure + "} " + super.toString();
    }
}
