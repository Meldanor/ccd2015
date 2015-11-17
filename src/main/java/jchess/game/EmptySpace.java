package jchess.game;

/**
 * A singleton for the {@link FigureType#FREE}. Used for internal representation of a free position.
 *
 * @since 06.11.2015
 */
public final class EmptySpace extends Figure {

    private static final EmptySpace INSTANCE = new EmptySpace();

    /**
     * @return A shared immutable instance to indicate, that this position is free.
     */
    public static EmptySpace get() {
        return INSTANCE;
    }

    private EmptySpace() {
        super("", FigureType.FREE, null);
    }
}
