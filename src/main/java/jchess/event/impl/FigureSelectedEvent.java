package jchess.event.impl;

import jchess.event.Event;
import jchess.event.EventType;
import jchess.game.Figure;
import jchess.game.Position2D;
import jchess.game.movement.ChessAction;

import java.util.List;

/**
 * When a Figure is selected via the UI this event is thrown. It indicates the selected figure and it's possible moves.
 *
 * @since 14.12.2015.
 */
public class FigureSelectedEvent extends Event {

    private final Position2D figurePos;
    private final List<ChessAction> possibleActions;

    /**
     * Create the event.
     *
     * @param pos The position of the selected figure.
     * @param possibleActions The possible actions of the selected figure.
     */
    public FigureSelectedEvent(Position2D pos, List<ChessAction> possibleActions) {
        super(EventType.FIGURE_SELECTED);

        this.figurePos = pos;
        this.possibleActions = possibleActions;
    }

    /**
     *
     * @return The selected figure.
     */
    public Position2D getFigurePosition(){return figurePos;}

    /**
     *
     * @return The possible actions for the selected figure.
     */
    public List<ChessAction> getPossibleActions(){return possibleActions;}

    @Override
    public String toString() {
        return "FigureSelectedEvent{figurePos=" + figurePos + "; possibleActions= " + possibleActions + "} " + super.toString();
    }
}
