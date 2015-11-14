package jchess.game.movement;

import jchess.game.DefaultHexagonalGameboard;
import jchess.game.Figure;
import jchess.game.HexagonalGameboard;
import jchess.game.Position2D;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This test demonstrate the usage of {@link MovementPattern}. It uses a pawn, which can attack an enemy pawn or move
 * besides him.
 *
 * @since 14.11.2015
 */
public class MovementPatternTest {

    @Test
    public void testSimpleMove() throws Exception {
        // Default gameboard
        HexagonalGameboard gameboard = new DefaultHexagonalGameboard();
        // Move a black pawn in front of a white pawn
        gameboard.moveTo(Position2D.of(4, 0), Position2D.of(2, 0));
        Figure whitePawn = gameboard.getFigure(Position2D.of(1, 0)).get();

        MovementPattern<Position2D, HexagonalGameboard> pattern = new SimplePawnMovement();
        List<ChessAction> possibleActions = pattern.getPossibleActions(whitePawn, gameboard);
        // Should contain move and attack

        assertEquals(2, possibleActions.size());
    }

    public class SimplePawnMovement implements MovementPattern<Position2D, HexagonalGameboard> {

        @Override
        public List<ChessAction> getPossibleActions(Figure figure, HexagonalGameboard chessboard) {
            List<ChessAction> actions = new ArrayList<>();
            Position2D ownPosition = chessboard.getPositionOf(figure);
            int x = ownPosition.getX();
            int y = ownPosition.getY();

            // Capture the enemy
            Optional<Figure> possibleEnemy = chessboard.getFigure(Position2D.of(x + 1, y));
            assertTrue(possibleEnemy.isPresent());
            actions.add(captureEnemy(figure, possibleEnemy.get(), Position2D.of(x + 1, y)));

            // Or move besides him
            actions.add(moveTo(figure, Position2D.of(x + 1, y + 1)));

            return actions;
        }
    }
}