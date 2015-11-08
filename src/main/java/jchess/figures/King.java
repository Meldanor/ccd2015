package jchess.figures;

import jchess.game.Figure;
import jchess.game.FigureType;

/**
 * Representation of the King figure.
 *
 * @since 07.11.2015.
 */
public class King extends Figure {

    /**
     * Construct a king.
     * */
    public King() {
        super("King", FigureType.KING);
    }
}
