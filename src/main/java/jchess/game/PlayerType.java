package jchess.game;

import java.util.Comparator;

/**
 * Defines the type of a player. Used for differentiate figures without additional knowledge about the player
 * @since 14.11.2015
 */
public interface PlayerType<T extends Gameboard> {

    int getOrder();

    static Comparator<HexagonalPlayerType> getComparator() {
        return (o1, o2) -> Integer.compare(o1.getOrder(), o2.getOrder());
    }
}
