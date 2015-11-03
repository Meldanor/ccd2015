package jchess.other;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SampleTest {

    @Test
    public void sample() {
        assertEquals(1, 1);
        assertNotEquals(2.0, 1.5, 0.1);
    }
}
