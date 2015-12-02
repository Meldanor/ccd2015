package jchess.game;

/**
 * A figure mock. Just for creating create objects.
 *
 * @since 01.12.2015
 */
public class FigureMock {

    private FigureMock() {

    }

    public static Figure create() {
        return new FigureBuilder<>("", null, () -> 1).build();
    }

}
