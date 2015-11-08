package jchess.figures;

import jchess.game.Figure;
import jchess.game.FigureType;

/**
 * Representation of the queen figure.
 *
 * @since 07.11.2015.
 */
public class Queen extends Figure {

    /**
     * Construct a queen.
     */
    public Queen() {
        super("Queen", FigureType.QUEEN);
    }
}
