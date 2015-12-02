package jchess.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @since 02.12.2015
 */
public class DefaultFiguresTest {

    @Test
    public void testPawn() throws Exception {
        Figure pawn = DefaultFigures.pawn(HexagonalPlayerType.BLACK);
        assertEquals(FigureType.PAWN, pawn.getType());
        assertEquals("Pawn", pawn.getName());
        assertEquals(1, pawn.getPattern().size());
    }

    @Test
    public void testKnight() throws Exception {
        Figure knight = DefaultFigures.knight(HexagonalPlayerType.BLACK);
        assertEquals(FigureType.KNIGHT, knight.getType());
        assertEquals("Knight", knight.getName());
        assertEquals(1, knight.getPattern().size());
    }

    @Test
    public void testBishop() throws Exception {
        Figure bishop = DefaultFigures.bishop(HexagonalPlayerType.BLACK);
        assertEquals(FigureType.BISHOP, bishop.getType());
        assertEquals("Bishop", bishop.getName());
        assertEquals(1, bishop.getPattern().size());
    }

    @Test
    public void testRook() throws Exception {
        Figure rook = DefaultFigures.rook(HexagonalPlayerType.BLACK);
        assertEquals(FigureType.ROOK, rook.getType());
        assertEquals("Rook", rook.getName());
        assertEquals(1, rook.getPattern().size());
    }

    @Test
    public void testQueen() throws Exception {

        Figure queen = DefaultFigures.queen(HexagonalPlayerType.BLACK);
        assertEquals(FigureType.QUEEN, queen.getType());
        assertEquals("Queen", queen.getName());
        assertEquals(2, queen.getPattern().size());
    }

    @Test
    public void testKing() throws Exception {

        Figure king = DefaultFigures.king(HexagonalPlayerType.BLACK);
        assertEquals(FigureType.KING, king.getType());
        assertEquals("King", king.getName());
        assertEquals(2, king.getPattern().size());
    }
}