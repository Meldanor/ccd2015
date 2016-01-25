package jchess.gamelogic;

import jchess.game.DefaultFigures;
import jchess.game.Figure;
import jchess.game.HexagonalPlayerType;
import jchess.game.Position2D;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by stephan on 23.12.2015.
 */
public class GameHistoryTest {

    @Test
    public void testSaveAndLoad() {
        //Arrange

        Map<Position2D, Figure> figures = new HashMap<>();
        figures.put(Position2D.of(0, 0), DefaultFigures.king(HexagonalPlayerType.BLACK));
        figures.put(Position2D.of(5, 5), DefaultFigures.king(HexagonalPlayerType.GRAY));
        GameHistory before = new GameHistory();
        before.addGameState(GameState.Create(figures, 12, HexagonalPlayerType.BLACK));
        before.addGameState(GameState.Create(figures, 6, HexagonalPlayerType.GRAY));

        GameHistory after = new GameHistory();

        //Act and Assert
        String json = before.save();
        assertTrue(json.length() > 0);

        after.load(json);
        assertTrue(before.getTurnHistory().size() == after.getTurnHistory().size());
        for (int i = 0; i < before.getTurnHistory().size(); i++) {
            GameState stateBefore = before.getTurnHistory().get(i);
            GameState stateAfter = after.getTurnHistory().get(i);
            assertEquals(stateBefore.turnNumber, stateAfter.turnNumber);
            assertEquals(stateBefore.activePlayer, stateAfter.activePlayer);
            assertEquals(stateBefore.allFigures.size(), stateAfter.allFigures.size());
            for (int j = 0; j < stateBefore.allFigures.size(); j++) {
                GameState.FigureDescription figureBefore = stateBefore.allFigures.get(j);
                GameState.FigureDescription figureAfter = stateAfter.allFigures.get(j);

                assertEquals(figureBefore.Name, figureAfter.Name);
                assertEquals(figureBefore.Owner, figureAfter.Owner);
                assertEquals(figureBefore.Position, figureAfter.Position);
            }
        }
    }
}
