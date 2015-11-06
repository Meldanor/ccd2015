package jchess.game;

/**
 * Describes a single {@link Figure}
 *
 * @since 03.11.2015
 */
public enum FigureType {

    /**
     * This figure is only a internal representation for a position without any figure on it.
     */
    FREE,

    PAWN,
    KNIGHT,
    BISHOP,
    ROOK,
    QUEEN,
    KING;
}
