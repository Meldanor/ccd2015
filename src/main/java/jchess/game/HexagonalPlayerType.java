package jchess.game;

/**
 * Implementation of the player type for hexagonal based three chess.
 *
 * @since 14.11.2015
 */
public enum HexagonalPlayerType implements PlayerType<HexagonalGameboard> {

    WHITE(1),
    BLACK(2),
    GRAY(3);

    private final int order;

    HexagonalPlayerType(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
