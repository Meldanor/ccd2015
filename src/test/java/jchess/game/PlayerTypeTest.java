package jchess.game;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @since 14.11.2015
 */
public class PlayerTypeTest {

    @Test
    public void testOrder() throws Exception {
        List<HexagonalPlayerType> types = Arrays.asList(HexagonalPlayerType.values());
        Collections.shuffle(types);
        Collections.sort(types, PlayerType.getComparator());

        Iterator<HexagonalPlayerType> iterator = types.iterator();
        if (iterator.hasNext()) {
            HexagonalPlayerType first = iterator.next();
            while (iterator.hasNext()) {
                HexagonalPlayerType next = iterator.next();
                assertTrue(first.getOrder() <= next.getOrder());
                first = next;
            }
        } else {
            System.out.println("Warning: Enum PlayerType is empty!");
        }
    }
}