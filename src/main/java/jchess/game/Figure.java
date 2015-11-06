package jchess.game;

/**
 * A representation of a single figure in the game.
 *
 * @since 03.11.2015
 */
public abstract class Figure {

    // TODO: Add player reference

    private final String name;
    private final FigureType type;

    /**
     * Construct the figure.
     *
     * @param name The name of the figure.
     * @param type The type of the figure.
     */
    public Figure(String name, FigureType type) {
        this.name = name;
        this.type = type;
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
}
