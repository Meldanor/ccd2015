package jchess.game;

import jchess.game.movement.BishopMovementPattern;
import jchess.game.movement.KnightMovement;
import jchess.game.movement.PawnMovement;
import jchess.game.movement.RookMovementPattern;

/**
 * A set of the default chess figures and their movement.
 *
 * @since 01.12.2015
 */
public final class DefaultFigures {

    private DefaultFigures() {

    }

    /**
     * Creates the pawn.
     *
     * @param owner The owner of the figure.
     * @param <P>   The position type
     * @param <G>   The gameboard type
     * @return A standard pawn.
     */
    @SuppressWarnings("unchecked")
    public static <P extends Position2D, G extends Gameboard<P>> Figure pawn(PlayerType<G> owner) {
        return new FigureBuilder<>("Pawn", FigureType.PAWN, owner).movement(new PawnMovement()).build();
    }

    /**
     * Creates the knight.
     *
     * @param owner The owner of the figure.
     * @param <P>   The position type
     * @param <G>   The gameboard type
     * @return A standard knight.
     */
    @SuppressWarnings("unchecked")
    public static <P extends Position2D, G extends Gameboard<P>> Figure knight(PlayerType<G> owner) {
        return new FigureBuilder<>("Knight", FigureType.KNIGHT, owner).movement(new KnightMovement()).build();
    }

    /**
     * Creates the bishop.
     *
     * @param owner The owner of the figure.
     * @param <P>   The position type
     * @param <G>   The gameboard type
     * @return A standard bishop.
     */
    @SuppressWarnings("unchecked")
    public static <P extends Position2D, G extends Gameboard<P>> Figure bishop(PlayerType<G> owner) {
        return new FigureBuilder<>("Bishop", FigureType.BISHOP, owner).movement(new BishopMovementPattern()).build();
    }

    /**
     * Creates the rook.
     *
     * @param owner The owner of the figure.
     * @param <P>   The position type
     * @param <G>   The gameboard type
     * @return A standard rook.
     */
    @SuppressWarnings("unchecked")
    public static <P extends Position2D, G extends Gameboard<P>> Figure rook(PlayerType<G> owner) {
        return new FigureBuilder<>("Rook", FigureType.ROOK, owner)
            .movement(new RookMovementPattern())
            .build();
    }

    /**
     * Creates the queen.
     *
     * @param owner The owner of the figure.
     * @param <P>   The position type
     * @param <G>   The gameboard type
     * @return A standard queen.
     */
    @SuppressWarnings("unchecked")
    public static <P extends Position2D, G extends Gameboard<P>> Figure queen(PlayerType<G> owner) {
        return new FigureBuilder<>("Queen", FigureType.QUEEN, owner)
            .movement(new RookMovementPattern())
            .movement(new BishopMovementPattern())
            .build();
    }

    /**
     * Creates the pawn.
     *
     * @param owner The owner of the figure.
     * @param <P>   The position type
     * @param <G>   The gameboard type
     * @return A standard king.
     */
    @SuppressWarnings("unchecked")
    public static <P extends Position2D, G extends Gameboard<P>> Figure king(PlayerType<G> owner) {
        return new FigureBuilder<>("King", FigureType.KING, owner)
            .movement(new RookMovementPattern(1))
            .movement(new BishopMovementPattern(1))
            .build();
    }
}
