package jchess.game;

/**
 * A position on the chess field.
 *
 * @since 03.11.2015
 */
public interface Position {

    /**
     * @return A string representation of the position for chess (like 'A1')
     */
    String getChessNotation();
}
