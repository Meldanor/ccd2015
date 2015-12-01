package jchess.game;

import jchess.game.movement.MovementPattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A representation of a single figure in the game.
 *
 * @since 03.11.2015
 */
public final class Figure {

    private final String name;
    private final FigureType type;
    private final PlayerType owner;

    private final List<MovementPattern> pattern;

    /**
     * Construct the figure.
     *
     * @param name  The name of the figure.
     * @param type  The type of the figure.
     * @param owner The owner (player) of the figure.
     * @deprecated Use the {@link FigureBuilder} instead.
     */
    public Figure(String name, FigureType type, PlayerType owner) {
        this(name, type, owner, Collections.emptyList());
    }

    /**
     * Construct the figure.
     *
     * @param name            The name of the figure.
     * @param figureType      The type of the figure.
     * @param owner           The owner (player) of the figure.
     * @param movementPattern The possible movements of the figure.
     */
    <P extends Position2D, G extends Gameboard<P>>
    Figure(String name, FigureType figureType, PlayerType owner, List<MovementPattern<P, G>> movementPattern) {
        this.name = name;
        this.type = figureType;
        this.owner = owner;
        this.pattern = Collections.unmodifiableList(new ArrayList<>(movementPattern));
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
     * @return The possible pattern of this figure
     */
    public List<MovementPattern> getPattern() {
        return pattern;
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
