package jchess.game;

import java.util.Objects;

/**
 * A representation of a single figure in the game.
 *
 * @since 03.11.2015
 */
public abstract class Figure {

    // TODO: Add player reference

    private final String name;
    private final FigureType type;
    private final PlayerType owner;

    /**
     * Construct the figure.
     *
     * @param name  The name of the figure.
     * @param type  The type of the figure.
     * @param owner The owner (player) of the figure.
     */
    public Figure(String name, FigureType type, PlayerType owner) {
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    /**
     * @return The name of the figure.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The type of the figure.
     */
    public FigureType getType() {
        return type;
    }

    /**
     * @return The owner (player) of the figure.
     */
    public PlayerType getOwner() {
        return owner;
    }

    /**
     * Check if this figure has a different owner than the other figure. Useful to check, if this figure can attack
     * the other ones.
     *
     * @param other The other figure. Must not be null.
     * @return <code>True</code> if, and only if, this and the other figure have the same owner. <code>False</code>
     * otherwise.
     */
    public boolean isOppositesFigure(Figure other) {
        Objects.requireNonNull(other, () -> "The other figure is null!");
        return this.owner != other.owner;
    }
}
