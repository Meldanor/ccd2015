package jchess.game;

import jchess.game.movement.MovementPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the builder class for {@link Figure}. It creates immutable figures objects.
 *
 * @since 01.12.2015
 */
public class FigureBuilder<P extends Position2D, G extends Gameboard<P>> {

    private final String name;
    private final FigureType figureType;
    private final PlayerType<G> owner;
    private List<MovementPattern<P, G>> movementPattern;

    /**
     * Creates the builder.
     *
     * @param name       The name of the figure. Cannot change after the constructor.
     * @param figureType The type of the figure. Cannot change after the constructor.
     * @param owner      The owner of the figure. Cannot change after the constructor.
     */
    public FigureBuilder(String name, FigureType figureType, PlayerType<G> owner) {
        this.name = name;
        this.figureType = figureType;
        this.owner = owner;
        this.movementPattern = new ArrayList<>();
    }

    /**
     * Add a movement pattern to the figure.
     *
     * @param pattern The pattern, which describes the movements of a figure.
     * @return This builder.
     */
    public FigureBuilder<P, G> movement(MovementPattern<P, G> pattern) {
        this.movementPattern.add(pattern);
        return this;
    }

    /**
     * Creates an immutable figure with the final attributes and the variable movement pattern.
     *
     * @return A fresh new figure to fight for our kingdom. Or just an instance of figure.
     */
    public Figure build() {
        return new Figure(name, figureType, owner, movementPattern);
    }

}
