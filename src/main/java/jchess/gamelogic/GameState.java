package jchess.gamelogic;

import jchess.game.Figure;
import jchess.game.HexagonalPlayerType;
import jchess.game.Position2D;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by stephan on 11.11.2015.
 * Contains data for one chess turn.
 */
public class GameState {

    public List<FigureDescription> allFigures;
    public int turnNumber;
    public HexagonalPlayerType activePlayer;

    /**
     * Factorymethod for easy creation of a gamestate
     *
     * @param figures Map of all Figures and their positions
     * @param turn    Turnnumber
     * @param player  active Players PlayerType
     * @return
     */
    public static GameState Create(Map<Position2D, Figure> figures, int turn, HexagonalPlayerType player) {
        GameState state = new GameState();
        Stream<Map.Entry<Position2D, Figure>> figureStream = figures.entrySet().stream();
        Function<Map.Entry<Position2D, Figure>, FigureDescription> mapper = entry -> new FigureDescription(entry.getKey(), entry.getValue().getName(), (HexagonalPlayerType) entry.getValue().getOwner());
        state.allFigures = figureStream.map(mapper).collect(Collectors.toList());
        state.turnNumber = turn;
        state.activePlayer = player;
        return state;
    }

    /**
     * Class for handling serialization of a Figure.
     * Name, Position and Owner of a Figure will be saved.
     */
    public static class FigureDescription {
        public Position2D Position;
        public String Name;
        public HexagonalPlayerType Owner;

        public FigureDescription(Position2D position, String name, HexagonalPlayerType player) {
            Position = position;
            Name = name;
            Owner = player;
        }
    }
}
