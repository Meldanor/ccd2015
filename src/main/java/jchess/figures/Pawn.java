package jchess.figures;

import jchess.game.Figure;
import jchess.game.FigureType;

/**
 * Representation of the pawn figure.
 *
 * @since  07.11.2015.
 */
public class Pawn extends Figure{

    /**
     * Construct a pawn.
     */
    public Pawn() {
        super("Pawn", FigureType.PAWN);
    }
}
